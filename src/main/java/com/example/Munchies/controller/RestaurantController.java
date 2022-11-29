package com.example.Munchies.controller;

import com.example.Munchies.model.entity.Restaurant;
import com.example.Munchies.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants")
    public String restaurants(Model model) {
        var restaurants = restaurantService.findAll();
        model.addAttribute("restaurants", restaurants);
        return "restaurants";
    }

    @GetMapping("/restaurants/restaurant-details/{id}")
    public String restaurantDetails(@PathVariable("id") Long id, Model model) {
        Restaurant restaurant = restaurantService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant with Id:" + id));
        model.addAttribute("restaurants", restaurant);
        return "restaurant-details";
    }

    @GetMapping("/restaurants/add")
    public String addNewRestaurant(Model model){
        Restaurant restaurant = new Restaurant();
        model.addAttribute("restaurant", restaurant);
        return "add-restaurant";
    }

    @PostMapping("/restaurants/save")
    public String saveRestaurant(@ModelAttribute("restaurant") Restaurant restaurant){
        restaurantService.save(restaurant);
        return "redirect:/restaurants/";
    }

    @GetMapping("/restaurants/edit/{id}")
    public String editRestaurant(@PathVariable("id") Long id, Model model) {
        Restaurant restaurant = restaurantService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant with Id:" + id));
        model.addAttribute("restaurant", restaurant);
        return "update-restaurant";
    }

    @PostMapping("/restaurants/update/{id}")
    public String updateRestaurant(@PathVariable("id") Long id, Restaurant restaurant, Model model){
        restaurantService.update(id, restaurant);
        return "redirect:/restaurants/";
    }

    @GetMapping("/restaurants/delete/{id}")
    public String deleteRestaurant(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant with Id:" + id));
        restaurantService.delete(restaurant);
        return "redirect:/restaurants/";
    }
}
