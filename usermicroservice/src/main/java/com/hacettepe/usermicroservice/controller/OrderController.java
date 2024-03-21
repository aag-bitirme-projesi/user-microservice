package com.hacettepe.usermicroservice.controller;

import com.hacettepe.usermicroservice.dto.PayRequestDto;
import com.hacettepe.usermicroservice.exception.UnableToPayException;
import com.hacettepe.usermicroservice.model.Model;
import com.hacettepe.usermicroservice.model.ShoppingCart;
import com.hacettepe.usermicroservice.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@Secured(SecurityUtils.ROLE_USER)
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    public IOrderService orderService;

    @PutMapping("/add-to-cart")
    public ResponseEntity<ShoppingCart> addToShoppingCart(@RequestBody Long modelId) {
        return ResponseEntity.ok(orderService.addToShoppingCart(modelId));
    }

    @GetMapping("/get-cart")
    public ResponseEntity<List<Model>> getShoppingCart() {
        return ResponseEntity.ok(orderService.getShoppingCart());
    }

    @PostMapping("/pay")
    public ResponseEntity<?> payForOrder(@RequestBody PayRequestDto payRequestDto) throws UnableToPayException {
        try {
            orderService.payForOrder(payRequestDto.isAddNewPayment(), payRequestDto.getPaymentInfoDTO(), payRequestDto.isSavePayment());
            return ResponseEntity.ok("paid.");
        } catch (UnableToPayException ex) {
            return ResponseEntity.status(400)
                                 .body(ex.getMessage());
        }
    }

    @GetMapping("/past-orders")
    public ResponseEntity<List<?>> getPastOrders() {
        return ResponseEntity.ok(orderService.getPastOrders());
    }
}
