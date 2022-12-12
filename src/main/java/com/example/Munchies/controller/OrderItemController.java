package com.example.Munchies.controller;

import com.example.Munchies.model.dto.OrderItemCreationDTO;
import com.example.Munchies.model.dto.OrderItemDTO;
import com.example.Munchies.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderItemController {

    private final OrderService orderService;

    public OrderItemController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/order-item/add/{id}")
    public String addItemInOrder(@PathVariable Long id, Model model) {
        model.addAttribute("orderid", id);
        model.addAttribute("orderitem", new OrderItemCreationDTO());
        return "add-item";
    }

    @PostMapping("/orders/order-item/save/{id}")
    public String saveItemInOrder(@PathVariable("id") Long id, OrderItemCreationDTO orderItem) {
        orderService.createOrderItem(id, orderItem);
        // redirect to details with update, delete, confirm
        return "redirect:/";
    }

    @GetMapping("/orders/group-order-items/{id}")
    public String allItemsInOrder(@PathVariable("id") Long id, OrderItemDTO orderItem, Model model) {
        model.addAttribute("orderitems", orderService.findAllByGroupId(id, orderItem));
        return "group-order-details";
    }
}
