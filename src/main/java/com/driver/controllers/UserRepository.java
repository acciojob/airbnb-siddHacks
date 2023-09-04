package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;


public class UserRepository {

    HashMap<String,Hotel> hotelHashMap = new HashMap<>();
    HashMap<Integer,User> userHashMap = new HashMap<>();
    HashMap<String, List<Facility>> listFacilityHashMap = new HashMap<>();
    HashMap<String, Booking> bookingHashMap = new HashMap<>();
    HashMap<Integer,List<Booking>> bookingByPersonHashMap = new HashMap<>();


    private String hotelWithMaxFacility = "";
    private int maxFacilitiesCount = 0;
    public String addHotel(Hotel hotel) {
        if(hotel==null){
            return "FAILURE";
        }
        String key = hotel.getHotelName();
        if(key==null){
            return "FAILURE";
        }
        if(hotelHashMap.containsKey(key)){
            return "FAILURE";
        }
        hotelHashMap.put(key,hotel);

        int countOfFacilitiesInHotel=hotel.getFacilities().size();
        if(countOfFacilitiesInHotel>=maxFacilitiesCount){
            if(countOfFacilitiesInHotel==maxFacilitiesCount){
                if(hotel.getHotelName().compareTo(hotelWithMaxFacility)<0){
                    hotelWithMaxFacility = hotel.getHotelName();
                }
            }else{
                maxFacilitiesCount = countOfFacilitiesInHotel;
                hotelWithMaxFacility = hotel.getHotelName();
            }
        }
        return "SUCCESS";
    }

    public int addUser(User user) {
        userHashMap.put(user.getaadharCardNo(),user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        return hotelWithMaxFacility;
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
        if(!hotelHashMap.containsKey(hotelName)){
            return new Hotel();
        }

        Hotel currHotel = hotelHashMap.get(hotelName);

        for(Facility facility: newFacilities){
            if(!currHotel.getFacilities().contains(facility)){
                currHotel.getFacilities().add(facility);
            }
        }

        int countOfFacilities = currHotel.getFacilities().size();
        if(countOfFacilities>=maxFacilitiesCount){
            if(countOfFacilities==maxFacilitiesCount){
                if(currHotel.getHotelName().compareTo(hotelWithMaxFacility)<0){
                    hotelWithMaxFacility = currHotel.getHotelName();
                }
            }else{
                maxFacilitiesCount = countOfFacilities;
                hotelWithMaxFacility = currHotel.getHotelName();
            }
        }
        return currHotel;
    }
}
