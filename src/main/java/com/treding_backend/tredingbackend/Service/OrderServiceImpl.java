package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Domain.OrderStatus;
import com.treding_backend.tredingbackend.Domain.OrderType;
import com.treding_backend.tredingbackend.Modal.*;
import com.treding_backend.tredingbackend.Repository.OrderItemRepository;
import com.treding_backend.tredingbackend.Repository.OrderRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Service


public class OrderServiceImpl implements   OrderService {
    @Autowired
    private  OrderRepository orderRepository;
    @Autowired
    private  WalletService walletService;

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private  AssetService assetService;




    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {

        double price = orderItem.getCoins().getCurrentPrice()*orderItem.getQuantity();

        Order order  = new Order();
        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimeStamp(LocalDateTime.now());

        order.setOrderStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) throws Exception {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("order Not found "));
    }


    @Override
    public List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);
    }



    private  OrderItem createOrderItem(Coins coins , double quantity , double buyPrice , double sellPrice ) {
        OrderItem  orderItem = new OrderItem();
        orderItem.setCoins(coins);
        orderItem.setQuantity(quantity);
        orderItem.setBuyprice(buyPrice);
        orderItem.setSellprice(sellPrice);
        return orderItemRepository.save(orderItem);
    }


    //transactional work as a rollback
    @Transactional
    public  Order buyAsset(Coins coin , double quantity , User user) throws Exception {
        if(quantity<=0)
        {
            throw  new Exception("Quantitty should be  greater than zero ");
        }

        double buyPrice= coin.getCurrentPrice();
        OrderItem orderItem = createOrderItem(coin , quantity,buyPrice , 0 );
        Order order = createOrder(user ,orderItem,OrderType.BUY);

        orderItem.setOrder(order);
        walletService.payOrderPayment(order , user);

        order.setOrderStatus(OrderStatus.SUCCESS);
        order .setOrderType(OrderType.BUY);
        Order  savedOrder = orderRepository.save(order);


        // create asset
          Asset oldAsset = assetService.findAssetByUserIdAndCoinsId(
                  order.getUser().getId(),
                  order.getOrderItem().getCoins().getId());
          if(oldAsset==null)
          {
              assetService.createAsset(user,orderItem.getCoins() , orderItem.getQuantity());

          }else{
               assetService.updateAsset(oldAsset.getId() , quantity);
          }

        return savedOrder;
    }


    @Transactional
    public  Order sellAsset(Coins coin , double quantity , User user) throws Exception {
        if(quantity<=0)
        {
            throw  new Exception("Quantitty should be  greater than zero ");
        }
       double sellPrice= coin.getCurrentPrice();
        Asset assetToSell = assetService.findAssetByUserIdAndCoinsId(
                user.getId(),
                coin.getId()
        );

        double buyPrice = assetToSell.getBuyPrice();
        if(assetToSell != null) {
            OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);



//            double sellPrice = coin.getCurrentPrice();

            Order order = createOrder(user, orderItem, OrderType.SELL);

            orderItem.setOrder(order);


            if (assetToSell.getQuantity() >= quantity) {


                order.setOrderStatus(OrderStatus.SUCCESS);
                order.setOrderType(OrderType.SELL);

                Order savedOrder = orderRepository.save(order);

                walletService.payOrderPayment(order, user);
                Asset updaAsset= assetService.updateAsset(assetToSell.getId() , -quantity);

                if(updaAsset.getQuantity()*coin.getCurrentPrice()<=1)
                {
                     assetService.deleteAsset(updaAsset.getId());
                }
                return savedOrder;


            }
            throw new Exception("Insufficient Quantity to sell ");
        }
        throw new Exception("Asset not found ");
    }

    @Override
    @Transactional
    public Order processOrder(Coins coins, double quantity, OrderType orderType , User user) throws Exception {
        if(orderType.equals(OrderType.BUY))
        {
          return  buyAsset(coins, quantity ,user);
        }else  if(orderType.equals(OrderType.SELL))
        {
            return sellAsset(coins , quantity,user);
        }

       throw  new Exception("Invalid order type ");
    }
}
