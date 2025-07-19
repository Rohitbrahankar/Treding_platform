package com.treding_backend.tredingbackend.Request;

import com.treding_backend.tredingbackend.Domain.OrderType;
import lombok.Data;

@Data
public class CreateOrderRequest {
  private String orderId;
  private double quantity;
  private OrderType orderType;

}
