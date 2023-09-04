package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    HashMap<String,Hotel> hotelHashMap = new HashMap<>();
    HashMap<Integer,User> userHashMap = new HashMap<>();
    HashMap<String, List<Facility>> listFacilityHashMap = new HashMap<>();
    HashMap<String, Booking> bookingHashMap = new HashMap<>();
    HashMap<Integer,List<Booking>> bookingByPersonHashMap = new HashMap<>();


    public Hotel addHotel(Hotel hotel) {
        if(hotelHashMap.containsKey(hotel)){
            return null;
        }else{
            hotelHashMap.put(hotel.getHotelName(),hotel);
            return hotel;
        }
    }

    public int addUser(User user) {
        userHashMap.put(user.getaadharCardNo(),user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        String hotelWithMostFacility = null;
        int numFacility = 0;
        for(Map.Entry<String , List<Facility>> entry : listFacilityHashMap.entrySet()){
            String key = entry.getKey();
            List<Facility> facilities = entry.getValue();

            if(numFacility <=  facilities.size()){
                if(numFacility == facilities.size()){
                    int len1 = hotelWithMostFacility.length();
                    int len2 = key.length();
                    if(len1 > len2){
                        hotelWithMostFacility = key;
                    }
                    break;
                }
                numFacility = facilities.size();

                hotelWithMostFacility = key;
            }
        }
        return hotelWithMostFacility;
    }

    public int bookAroom(Booking booking) {
        UUID randomUUID = UUID.randomUUID();
        String bookingId = randomUUID.toString();
        booking.setBookingId(bookingId);

        String hotelName = booking.getHotelName();
        Hotel hotel = hotelHashMap.get(hotelName);
       int pricePerNight = hotel.getPricePerNight();
       int availableRooms = hotel.getAvailableRooms();
       int totalAmount = 0;
       if(booking.getNoOfRooms() > availableRooms){
           return -1;
       }else{
           totalAmount = pricePerNight * availableRooms;
           booking.setAmountToBePaid(totalAmount);
           bookingHashMap.put(bookingId,booking);
           return totalAmount;
       }
    }

    public int getBookingsByPerson(Integer aadharCard) {
       List<Booking> listBooking = bookingByPersonHashMap.get(aadharCard);
       return listBooking.size();
    }

    public Hotel updateFacility(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelHashMap.get(hotelName);
        List<Facility> facilities = hotel.getFacilities();

        if (!facilities.equals(newFacilities)) {
            hotel.setFacilities(newFacilities);
            hotelHashMap.getOrDefault(hotelName,hotel);
        }
        return hotel;
    }
}
