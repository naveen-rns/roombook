package com.practice.roombook.service;

import com.practice.roombook.datastore.Datastore;
import com.practice.roombook.dto.BookingDto;
import com.practice.roombook.entity.Booking;
import com.practice.roombook.exception.BookingException;
import com.practice.roombook.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = { "rooms.total.available=10" })
class BookingServiceImplTest {

    @TestConfiguration
    static class BookingServiceTestConfiguration {
        @Bean
        public BookingService bookingService() {
            return new BookingServiceImpl();
        }
    }

    @MockBean
    private Datastore datastore;

    @Autowired
    private BookingService bookingService;

    @Test
    void saveBooking() throws BookingException, ParseException {
        BookingDto bookingDto1 = new BookingDto(1, "VIP1", "20-03-2022");
        BookingDto bookingDto2 = new BookingDto(2, "VIP2", "20-03-2022");
        BookingDto bookingDto3 = new BookingDto(3, "VIP3", "20-03-2022");
        bookingService.saveBooking(bookingDto1);
        bookingService.saveBooking(bookingDto2);
        bookingService.saveBooking(bookingDto3);

        Mockito.verify(datastore, Mockito.times(3)).addBooking(Mockito.any(Booking.class));
        Mockito.verifyNoMoreInteractions(datastore);
    }

    @Test
    void findAllAvailableRooms() throws BookingException, ParseException {
        int totalRooms = 10;
        String bookingDateStr = "20-03-2022";
        Date bookingDate = new SimpleDateFormat(Constants.DATE_FORMAT).parse(bookingDateStr);
        List<Booking> bookedRoomList = getDummyBookingList();
        List<Booking> bookingsOnDate = bookedRoomList.stream()
                .filter(booking -> booking.getBookingDate().equals(bookingDate))
                .collect(Collectors.toList());
        Mockito.when(datastore.getTotalRooms()).thenReturn(totalRooms);
        Mockito.when(datastore.getBookingList()).thenReturn(bookedRoomList);
        List<Integer> allAvailableRoomsOnDate = bookingService.findAllAvailableRooms(bookingDateStr);

        // check the count
        assertTrue(allAvailableRoomsOnDate.size() == (totalRooms - bookingsOnDate.size()));

        // allAvailableRoomsOnDate list and bookingsOnDate should not have anything in common
        assertTrue(bookingsOnDate.stream().
                filter(booking -> allAvailableRoomsOnDate.contains(booking.getRoomNumber()))
                .collect(Collectors.toList())
                .isEmpty());
    }

    @Test
    void findAllBookingsForGuest() throws ParseException {
        String guestNameTest = "VIP1";
        int totalRooms = 10;
        List<Booking> bookedRoomList = getDummyBookingList();
        Mockito.when(datastore.getTotalRooms()).thenReturn(totalRooms);
        Mockito.when(datastore.getBookingList()).thenReturn(bookedRoomList);

        List<Booking> bookingList = bookingService.findAllBookingsForGuest(guestNameTest);

        List<Integer> guestNameBookingRooms = bookedRoomList.stream()
                .filter(booking -> booking.getGuestName().equals(guestNameTest))
                .map(booking -> booking.getRoomNumber())
                .collect(Collectors.toList());

        assertTrue(guestNameBookingRooms.size() == bookingList.size());
        assertTrue(bookingList.stream().allMatch(booking -> guestNameBookingRooms.contains(booking.getRoomNumber())));
    }

    private List<Booking> getDummyBookingList() throws ParseException {
        List<Booking> bookingList = new ArrayList<>();
        Booking booking1 = new Booking(1, "VIP1", new SimpleDateFormat(Constants.DATE_FORMAT).parse("20-03-2022"));
        Booking booking2 = new Booking(2, "VIP2", new SimpleDateFormat(Constants.DATE_FORMAT).parse("20-03-2022"));
        Booking booking3 = new Booking(3, "VIP1", new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-03-2022"));

        bookingList.add(booking1);
        bookingList.add(booking2);
        bookingList.add(booking3);
        return bookingList;
    }
}