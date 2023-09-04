package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


public class UserService {

    UserRepository userRepository = new UserRepository();


    public String addHotel(Hotel hotel) {

        return userRepository.addHotel(hotel);
    }

    public int addUser(User user) {
        return userRepository.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        String hotelName = userRepository.getHotelWithMostFacilities();
        return hotelName;
    }

    public int bookAroom(Booking booking) {
        return userRepository.bookAroom(booking);
    }

    public int getBookingsByPerson(Integer aadharCard) {
       return userRepository.getBookingsByPerson(aadharCard);
    }


    public Hotel updateFacility(List<Facility> newFacilities, String hotelName) {
        return userRepository.updateFacility(newFacilities,hotelName);
    }
}
