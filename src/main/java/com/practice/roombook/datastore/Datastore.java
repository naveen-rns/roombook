package com.practice.roombook.datastore;

import com.practice.roombook.entity.Booking;
import com.practice.roombook.exception.BookingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Datastore {

    private static int bookingRefCounter = 1000;

    @Value("${rooms.total.available}")
    private Integer totalRooms;

    private List<Booking> bookingList = new ArrayList<>();

    public List<Booking> getBookingList() {
        return Collections.unmodifiableList(bookingList);
    }

    public synchronized String addBooking(Booking booking) throws BookingException {
        validateBookingData(booking);
        boolean roomAvailable = bookingList.stream()
                .filter(booking1 -> (booking1.getRoomNumber() == booking.getRoomNumber() && booking1.getBookingDate().equals(booking.getBookingDate())))
                        .collect(Collectors.toList()).isEmpty();
        if(roomAvailable){
            booking.setBookingRefID("ID-"+bookingRefCounter++);
            bookingList.add(booking);
            return booking.getBookingRefID();
        }
        throw new BookingException(HttpStatus.CONFLICT,
                "BOOKING_TAKEN",
                "Room " + booking.getRoomNumber() +"  is already booked for this date " + new SimpleDateFormat("dd-MM-YYYY").format(booking.getBookingDate()));
    }

    private void validateBookingData(Booking booking) throws BookingException {
        if(booking.getBookingDate() == null || new Date().after(booking.getBookingDate())){
            throw new BookingException(HttpStatus.BAD_REQUEST,"BOOKING_DATE_NOT_ALLOWED", "Booking Date cannot be null or in the past");
        }
        else if (booking.getRoomNumber() <1 || booking.getRoomNumber() > getTotalRooms()){
            throw new BookingException(HttpStatus.BAD_REQUEST,"BOOKING_ROOM_NUMBER_INCORRECT_DATA", "Room Number is incorrect");
        }
        else if (booking.getGuestName() == null || booking.getGuestName().trim().isEmpty()){
            throw new BookingException(HttpStatus.BAD_REQUEST,"BOOKING_GUEST_NAME_REQUIRED", "Guest Name is required for the booking. It cannot be empty");
        }
    }

    public Integer getTotalRooms() {
        return totalRooms;
    }
}