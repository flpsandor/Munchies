package com.example.Munchies.controller;

import com.example.Munchies.model.entity.DeliveryInfo;
import com.example.Munchies.model.entity.Restaurant;
import com.example.Munchies.service.DeliveryInfoService;
import com.example.Munchies.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final DeliveryInfoService deliveryInfoService;

    public RestaurantController(RestaurantService restaurantService, DeliveryInfoService deliveryInfoService) {
        this.restaurantService = restaurantService;
        this.deliveryInfoService = deliveryInfoService;
    }

    @GetMapping("/restaurants")
    public String restaurants(Model model) {
        var restaurants = restaurantService.findAll();
        model.addAttribute("restaurants", restaurants);
        return "restaurants";
    }

    @GetMapping("/restaurants/restaurant-details/{id}")
    public String restaurantDetails(@PathVariable("id") Long id, Model model) {
        var restaurant = restaurantService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid restaurant with Id:" + id));
        var deliveryInfo = deliveryInfoService.findByRestaurant(restaurant).orElseThrow(() -> new IllegalArgumentException("Invalid deliveryInfo with restaurant with Id:" + id));
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("deliveryinfo", deliveryInfo);
        return "restaurant-details";
    }

    @GetMapping("/restaurants/add")
    public String addNewRestaurant(Model model) {
        Restaurant restaurant = new Restaurant();
        DeliveryInfo deliveryInfo = new DeliveryInfo();
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("deliveryinfo", deliveryInfo);
        return "add-restaurant";
    }

    @PostMapping("/restaurants/save")
    public String saveRestaurant(@ModelAttribute("deliveryinfo") DeliveryInfo deliveryInfo, @ModelAttribute("restaurant") Restaurant restaurant) {
        restaurantService.save(restaurant);
        deliveryInfoService.save(restaurant, deliveryInfo);
        return "redirect:/restaurants/";
    }

    @GetMapping("/restaurants/edit/{id}")
    public String editRestaurant(@PathVariable("id") Long id, Model model) {
        Restaurant restaurant = restaurantService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid restaurant with Id:" + id));
        DeliveryInfo deliveryInfo = deliveryInfoService.findByRestaurant(restaurant).orElseThrow(() -> new IllegalArgumentException("Invalid deliveryinfo for restaurant with Id:" + id));
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("deliveryinfo", deliveryInfo);
        return "update-restaurant";
    }

    @PostMapping("/restaurants/update/{id}")
    public String updateRestaurant(@PathVariable("id") Long id, Restaurant restaurant, DeliveryInfo deliveryInfo) {
        restaurantService.update(id, restaurant);
        deliveryInfoService.update(restaurantService.findById(id).get(), deliveryInfo);
        return "redirect:/restaurants/";
    }

    @GetMapping("/restaurants/delete/{id}")
    public String deleteRestaurant(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid restaurant with Id:" + id));
        deliveryInfoService.delete(restaurant);
        restaurantService.delete(restaurant);
        return "redirect:/restaurants/";
    }
}
