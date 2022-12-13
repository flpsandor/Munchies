package com.example.Munchies.controller;

import com.example.Munchies.model.dto.GroupOrderCreationDTO;
import com.example.Munchies.service.OrderService;
import com.example.Munchies.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class GroupOrderController {

    private final OrderService orderService;
    private final RestaurantService restaurantService;

    public GroupOrderController(OrderService orderService, RestaurantService restaurantService) {
        this.orderService = orderService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/orders/add")
    public String addNewGroupOrder(Model model) {
        model.addAttribute("grouporder", new GroupOrderCreationDTO());
        model.addAttribute("restaurants", restaurantService.findAll());
        return "new-group-order";
    }

    @GetMapping("/orders/add/restaurant/{id}")
    public String addNewGroupOrderWithRestaurantId(@PathVariable("id") Long id, Model model){
        model.addAttribute("id", id);
        model.addAttribute("grouporder", new GroupOrderCreationDTO());
        return "new-group-order-with-restaurant";
    }

    @PostMapping("/orders/save/restaurant/{id}")
    public String saveGroupOrderWithRestaurant(@ModelAttribute("id") Long id, @ModelAttribute("grouporder") GroupOrderCreationDTO groupOrder, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "new-group-order";
        }
        model.addAttribute("grouporder", orderService.createGroupOrder(id, groupOrder));
        return "group-order-details";
    }

    @PostMapping("/orders/save")
    public String saveGroupOrder(@Valid @ModelAttribute("grouporder") GroupOrderCreationDTO groupOrder, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "new-group-order";
        }
        model.addAttribute("grouporder", orderService.createGroupOrder(groupOrder));
        return "group-order-details";
    }

    @GetMapping("/orders/group-order-items/{id}")
    public String findItemsInOrder(@PathVariable("id") Long id, Model model) {
        model.addAttribute("orderitems", orderService.findAllByGroupId(id));
        model.addAttribute("grouporder", orderService.findById(id));
        return "group-order-details";
    }
}
