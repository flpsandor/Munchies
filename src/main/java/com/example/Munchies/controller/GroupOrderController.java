package com.example.Munchies.controller;

import com.example.Munchies.model.dto.GroupOrderCreationDTO;
import com.example.Munchies.service.OrderService;
import com.example.Munchies.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GroupOrderController {

    private final OrderService orderService;
    private final RestaurantService restaurantService;

    public GroupOrderController(OrderService orderService, RestaurantService restaurantService) {
        this.orderService = orderService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/")
    public String home(Model model) {
        var groupOrders = orderService.findAll();
        model.addAttribute("grouporders", groupOrders);
        return "index";
    }

    @GetMapping("/orders/add")
    public String addNewGroupOrder(Model model) {
        model.addAttribute("grouporder", new GroupOrderCreationDTO());
        model.addAttribute("restaurants", restaurantService.findAll());
        return "new-group-order";
    }

    @PostMapping("/orders/save")
    public String saveGroupOrder(@ModelAttribute("grouporder") GroupOrderCreationDTO groupOrder) {
        orderService.createGroupOrder(groupOrder);
        return "redirect:/";
    }

    @GetMapping("/orders/delete/{id}")
    public String deleteGroupOrder(@PathVariable Long id) {
        orderService.deleteGroupOrder(id);
        return "redirect:/";
    }
}
