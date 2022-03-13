package com.practice.roombook.service;

import com.practice.roombook.dto.BookingDto;
import com.practice.roombook.entity.Booking;
import com.practice.roombook.exception.BookingException;

import java.util.Date;
import java.util.List;

public interface BookingService {

    /**
     * Saves the Booking and returns the booking reference ID.
     * @param bookingDto
     * @return bookingRefID
     */
    String saveBooking(BookingDto bookingDto) throws BookingException;

    /**
     * Finds all available rooms based on the booking date.
     * @param bookingDateStr
     * @return
     */
    List<Integer> findAllAvailableRooms(String bookingDateStr) throws BookingException;

    /**
     * Find all the bookings for the guest.
     * @param guestName
     * @return
     */
    List<Booking> findAllBookingsForGuest(String guestName);
}
