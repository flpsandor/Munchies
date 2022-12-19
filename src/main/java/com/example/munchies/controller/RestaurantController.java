package com.example.munchies.controller;

import com.example.munchies.exception.RestaurantNotExistException;
import com.example.munchies.model.dto.RestaurantCreationDTO;
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
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @GetMapping("/restaurants/add")
    public String addNewRestaurant(Model model) {
        model.addAttribute("restaurant", new RestaurantCreationDTO());
        return "add-restaurant";
    }

    @PostMapping("/restaurants/save")
    public String saveRestaurant( @ModelAttribute("restaurant") @Valid RestaurantCreationDTO restaurant, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "add-restaurant";
        }
        restaurantService.createRestaurant(restaurant);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return "redirect:/restaurants/";
    }

    @GetMapping("/restaurants")
    public String restaurants(Model model) {
        var restaurants = restaurantService.findAll();
        model.addAttribute("restaurants", restaurants);
        return "restaurants";
    }

    @GetMapping("/restaurants/restaurant-details/{id}")
    public String restaurantDetails(@PathVariable("id") Long id, Model model) throws RestaurantNotExistException {
        model.addAttribute("restaurant", restaurantService.restaurantDetails(id));
        return "restaurant-details";
    }

    @GetMapping("/restaurants/edit/{id}")
    public String editRestaurant(@PathVariable("id") Long id, Model model) throws RestaurantNotExistException {
        model.addAttribute("id", id);
        model.addAttribute("restaurant", restaurantService.restaurantUpdateCreation(id));
        return "update-restaurant";
    }

    @PostMapping("/restaurants/update/{id}")
    public String updateRestaurant(@PathVariable("id") Long id, @Valid @ModelAttribute("restaurant") RestaurantCreationDTO restaurant, BindingResult bindingResult, HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "update-restaurant";
        }
        restaurantService.updateRestaurant(id, restaurant);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return "redirect:/restaurants/restaurant-details/{id}";
    }

    @GetMapping("/restaurants/delete/{id}")
    public String deleteRestaurant(@PathVariable Long id, HttpServletResponse response) {
        restaurantService.deleteRestaurant(id);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        return "redirect:/restaurants/";
    }
}
