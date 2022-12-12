package com.example.Munchies.controller;

import com.example.Munchies.model.dto.OrderItemCreationDTO;
import com.example.Munchies.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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
    public String saveItemInOrder(@PathVariable("id") Long id, @ModelAttribute("orderitem") @Valid OrderItemCreationDTO orderItem, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "add-item";
        }
        orderService.createOrderItem(id, orderItem);
        return "redirect:/orders/group-order-items/{id}";
    }
}
