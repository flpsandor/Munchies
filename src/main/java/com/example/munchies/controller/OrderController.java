package com.example.munchies.controller;

import com.example.munchies.exception.GroupOrderNotExistException;
import com.example.munchies.exception.OrderNotValidException;
import com.example.munchies.exception.RestaurantNotExistException;
import com.example.munchies.model.dto.GroupOrderCreationDTO;
import com.example.munchies.model.dto.OrderItemCreationDTO;
import com.example.munchies.service.OrderService;
import com.example.munchies.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final RestaurantService restaurantService;

    public OrderController(OrderService orderService, RestaurantService restaurantService) {
        this.orderService = orderService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants/order/add")
    public String addNewGroupOrder(Model model) {
        model.addAttribute("grouporder", new GroupOrderCreationDTO());
        model.addAttribute("restaurants", restaurantService.findAll());
        return "new-group-order";
    }

    @GetMapping("/restaurants/{id}/order/add")
    public String addNewGroupOrderWithRestaurantId(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("grouporder", new GroupOrderCreationDTO());
        return "new-group-order-with-restaurant";
    }

    @PostMapping("/restaurants/{id}/order/save")
    public String saveGroupOrderWithRestaurant(@ModelAttribute("id") Long id, @ModelAttribute("grouporder") GroupOrderCreationDTO groupOrder, BindingResult bindingResult, Model model, HttpServletResponse response) throws RestaurantNotExistException {
        if (bindingResult.hasErrors()) {
            return "new-group-order";
        }
        model.addAttribute("grouporder", orderService.createGroupOrder(id, groupOrder));
        response.setStatus(HttpServletResponse.SC_CREATED);
        return "group-order-details";
    }

    @PostMapping("/restaurants/order/save")
    public String saveGroupOrder(@Valid @ModelAttribute("grouporder") GroupOrderCreationDTO groupOrder, BindingResult bindingResult, Model model, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "new-group-order";
        }
        model.addAttribute("grouporder", orderService.createGroupOrder(groupOrder));
        response.setStatus(HttpServletResponse.SC_CREATED);
        return "group-order-details";
    }

    @GetMapping("/restaurants/order/{id}/group-order-items")
    public String findItemsInOrder(@PathVariable("id") Long id, Model model) throws GroupOrderNotExistException {
        model.addAttribute("orderitems", orderService.findAllByGroupId(id));
        model.addAttribute("grouporder", orderService.findById(id));
        return "group-order-details";
    }

    @GetMapping("/restaurants/order/{id}/order-items-data")
    public String reloadItemsInOrder(@PathVariable("id") Long id, Model model) throws GroupOrderNotExistException {
        model.addAttribute("orderitems", orderService.findAllByGroupId(id));
        model.addAttribute("grouporder", orderService.findById(id));
        return "group-order-details::orderitems";
    }

    @GetMapping("/restaurants/order/{id}/order-item/add")
    public String addItemInOrder(@PathVariable Long id, Model model) {
        model.addAttribute("orderid", id);
        model.addAttribute("orderitem", new OrderItemCreationDTO());
        return "add-item";
    }

    @PostMapping("/restaurants/order/{id}/order-item/save")
    public String saveItemInOrder(@PathVariable("id") Long id, @ModelAttribute("orderitem") @Valid OrderItemCreationDTO orderItem, BindingResult bindingResult, HttpServletResponse response) throws GroupOrderNotExistException, OrderNotValidException {
        if (bindingResult.hasErrors()) {
            return "add-item";
        }
        orderService.createOrderItem(id, orderItem);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return "redirect:/restaurants/order/{id}/group-order-items/";
    }
}
