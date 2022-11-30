package com.example.Munchies.service;

import com.example.Munchies.model.entity.Restaurant;
import com.example.Munchies.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> findById(Long id) {
        return restaurantRepository.findById(id);
    }


    public void save(Restaurant restaurant) {
        restaurant.setRestaurantCreated(LocalDateTime.now());
        restaurant.setRestaurantShortName(restaurant.getRestaurantName().replaceAll("\\s+", "_").toLowerCase());
        restaurantRepository.save(restaurant);
    }

    public void delete(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }

    public void update(Long id, Restaurant restaurant) {
        Optional<Restaurant> restaurantDb = restaurantRepository.findById(id);
        if (restaurantDb.isPresent()) {
            var updatedRestaurant = restaurantDb.get();
            updatedRestaurant.setRestaurantName(restaurant.getRestaurantName());
            updatedRestaurant.setRestaurantShortName(restaurant.getRestaurantName().replaceAll("\\s+", "_").toLowerCase());
            updatedRestaurant.setRestaurantUpdated(LocalDateTime.now());
            updatedRestaurant.setRestaurantCreated(restaurantDb.get().getRestaurantCreated());
            updatedRestaurant.setRestaurantAddress(restaurant.getRestaurantAddress());
            updatedRestaurant.setRestaurantMenuUrl(restaurant.getRestaurantMenuUrl());
            updatedRestaurant.setRestaurantPhoneNumber(restaurant.getRestaurantPhoneNumber());
            restaurantRepository.save(updatedRestaurant);
        }
    }
}
