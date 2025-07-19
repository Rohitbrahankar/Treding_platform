package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Domain.OrderType;
import com.treding_backend.tredingbackend.Modal.Coins;
import com.treding_backend.tredingbackend.Modal.Order;
import com.treding_backend.tredingbackend.Modal.OrderItem;
import com.treding_backend.tredingbackend.Modal.User;

import java.util.List;

public interface OrderService {


    Order createOrder(User user , OrderItem orderItem , OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrderOfUser(Long userId ,  OrderType  orderType, String assetSymbol);

    Order processOrder(Coins coins , double quantity, OrderType orderType , User user) throws Exception;






}
