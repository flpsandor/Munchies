package com.example.Munchies.controller;

import com.example.Munchies.model.dto.DeliveryInfoDTO;
import com.example.Munchies.model.dto.RestaurantDTO;
import com.example.Munchies.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        model.addAttribute("restaurant", restaurantService.createRestaurantDtoById(id));
        model.addAttribute("deliveryinfo", restaurantService.createDeliveryDtoFromRestaurantId(id));
        return "restaurant-details";
    }

    @GetMapping("/restaurants/add")
    public String addNewRestaurant(Model model) {
        RestaurantDTO restaurant = new RestaurantDTO();
        DeliveryInfoDTO deliveryInfo = new DeliveryInfoDTO();
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("deliveryinfo", deliveryInfo);
        return "add-restaurant";
    }

    @PostMapping("/restaurants/save")
    public String saveRestaurant(@ModelAttribute("deliveryinfo") DeliveryInfoDTO deliveryInfo, @ModelAttribute("restaurant") RestaurantDTO restaurant) {
        restaurantService.save(restaurant, deliveryInfo);
        return "redirect:/restaurants/";
    }

    @GetMapping("/restaurants/edit/{id}")
    public String editRestaurant(@PathVariable("id") Long id, Model model) {
        model.addAttribute("restaurant", restaurantService.createRestaurantDtoById(id));
        model.addAttribute("deliveryinfo", restaurantService.createDeliveryDtoFromRestaurantId(id));
        return "update-restaurant";
    }

    @PostMapping("/restaurants/update/{id}")
    public String updateRestaurant(@PathVariable("id") Long id, RestaurantDTO restaurant, DeliveryInfoDTO deliveryInfo) {
        restaurantService.update(id, restaurant, deliveryInfo);
        return "redirect:/restaurants/";
    }

    @GetMapping("/restaurants/delete/{id}")
    public String deleteRestaurant(@PathVariable Long id) {
        restaurantService.delete(id);
        return "redirect:/restaurants/";
    }
}
