package com.practice.roombook.controller;

import com.practice.roombook.dto.BookingDto;
import com.practice.roombook.entity.Booking;
import com.practice.roombook.exception.BookingException;
import com.practice.roombook.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping(value = "/rooms/book", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> bookARoom(@RequestHeader MultiValueMap<String, String> headers,
                                                         @RequestBody BookingDto bookingDto) throws BookingException {

        String bookingRefId = bookingService.saveBooking(bookingDto);
        return ResponseEntity.ok("Your room is booked. Booking Ref ID = "+ bookingRefId);

    }

        @GetMapping(value = "/rooms/available/{dateStr}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findAllAvailableRooms(@RequestHeader MultiValueMap<String, String> headers,
                                                        @PathVariable String dateStr) throws BookingException {

        List<Integer> availableRoomNumbers = bookingService.findAllAvailableRooms(dateStr);
        return ResponseEntity.ok("Available Rooms= " + availableRoomNumbers + " for Date =" + dateStr);
    }

    @GetMapping(value = "/rooms/search/{guestName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findBookingsByGuestName(@RequestHeader MultiValueMap<String, String> headers,
                                                        @PathVariable String guestName) throws BookingException {

        List<Booking> bookingsByGuest = bookingService.findAllBookingsForGuest(guestName);
        return ResponseEntity.ok("Bookings by Guest = " + bookingsByGuest);
    }

}
