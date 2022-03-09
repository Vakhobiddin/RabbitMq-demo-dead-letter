package com.example.rabbit.publisher;

import com.example.rabbit.config.MessagingConfig;
import com.example.rabbit.dto.Order;
import com.example.rabbit.dto.OrderStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderPublisher {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping("/{restaurantName}")
    public String bookOrder(@RequestBody Order order, @PathVariable String restaurantName){
        order.setOrderId(UUID.randomUUID().toString());
        OrderStatus orderStatus = new OrderStatus(order,"PROCCESS",
                "order placed successfully: " + restaurantName);
        rabbitTemplate.convertAndSend(MessagingConfig.EXCHANGE,MessagingConfig.BINDING, orderStatus);
        return "Success info !!";
    }
}
