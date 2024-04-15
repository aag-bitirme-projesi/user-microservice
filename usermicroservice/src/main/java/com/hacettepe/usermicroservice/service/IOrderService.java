package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.dto.PaymentInfoDTO;
import com.hacettepe.usermicroservice.exception.UnableToPayException;
import com.hacettepe.usermicroservice.model.Model;
import com.hacettepe.usermicroservice.model.ShoppingCart;

import java.security.Principal;
import java.util.List;

public interface IOrderService {
    void addToShoppingCart(long modelId);
    List<Model> getShoppingCart();
    double getTotalAmount(List<Model> modelsList);
    void payForOrder() throws UnableToPayException; //boolean addNewPayment, PaymentInfoDTO paymentInfo, boolean savePayment) throws UnableToPayException;
    List<?> getPastOrders();
}
