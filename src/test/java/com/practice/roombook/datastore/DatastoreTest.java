package com.practice.roombook.datastore;

import com.practice.roombook.entity.Booking;
import com.practice.roombook.exception.BookingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = { "rooms.total.available=10" })
class DatastoreTest {

    @TestConfiguration
    static class DatastoreTestConfiguration {
        @Bean
        public Datastore datastore() {
            return new Datastore();
        }
    }

    @Autowired
    private Datastore datastore;

    @Test
    void addBooking_bookingdate_in_future() throws BookingException, ParseException {
        Booking booking = new Booking(1, "VIP Guest 1", new SimpleDateFormat("dd-MM-yyyy").parse("20-03-2022"));
        datastore.addBooking(booking);
        Assertions.assertFalse(datastore.getBookingList().isEmpty());
    }


    @Test
    void addBooking_bookingdate_in_past() throws BookingException, ParseException {
        Booking booking = new Booking(1, "VIP Guest 1", new SimpleDateFormat("dd-MM-yyyy").parse("10-03-2022"));
        BookingException exception = assertThrows(BookingException.class, () -> datastore.addBooking(booking));
    }

    @Test
    void addBooking_roomnumber_greater_than_10() throws BookingException, ParseException {
        Booking booking = new Booking(11, "VIP Guest 1", new SimpleDateFormat("dd-MM-yyyy").parse("20-03-2022"));
        BookingException exception = assertThrows(BookingException.class, () -> datastore.addBooking(booking));
    }
}