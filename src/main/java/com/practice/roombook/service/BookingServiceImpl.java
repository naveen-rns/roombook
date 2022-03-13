package com.practice.roombook.service;

import com.practice.roombook.datastore.Datastore;
import com.practice.roombook.dto.BookingDto;
import com.practice.roombook.entity.Booking;
import com.practice.roombook.exception.BookingException;
import com.practice.roombook.util.Constants;
import com.practice.roombook.util.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private Datastore datastore;

    /**
     **1. Check if booking possible
     **2. If available then save booking
     **3. If not available possible then exception
     * @param bookingDto
     * @return
     * @throws BookingException
     */
    @Override
    public String saveBooking(BookingDto bookingDto) throws BookingException {
        Booking newBooking = null;
        newBooking = transformDTO(bookingDto);
        return datastore.addBooking(newBooking);
    }

    @Override
    public List<Integer> findAllAvailableRooms(String bookingDateStr) throws BookingException {
        Date bookingDate;
        try {
            bookingDate = new SimpleDateFormat(Constants.DATE_FORMAT).parse(bookingDateStr);
        } catch (ParseException e) {
            throw new BookingException(HttpStatus.BAD_REQUEST, "DATE_FORMAT_INCORRECT", "Expected date format of dd-MM-yyyy but received as " + bookingDateStr);
        }

        List<Integer> bookedRoomList = datastore.getBookingList().stream()
                .filter(booking -> booking.getBookingDate().equals(bookingDate))
                .map(booking -> booking.getRoomNumber())
                .collect(Collectors.toList());

        return getAllRoomNumberList().stream()
                .filter(roomNbr -> !bookedRoomList.contains(roomNbr))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findAllBookingsForGuest(String guestName) {
        return datastore.getBookingList().stream()
                .filter(booking -> booking.getGuestName().equals(guestName))
                .collect(Collectors.toList());
    }

    private Booking transformDTO(BookingDto dto) throws BookingException {
        Booking booking = null;
        try {
            booking = new Booking(dto.getRoomNumber(), dto.getGuestName(), ConverterUtils.getDateFromStr(dto.getBookingDateStr()));
        } catch (ParseException e) {
            throw new BookingException(HttpStatus.BAD_REQUEST, "DATE_FORMAT_INCORRECT", "Expected date format of dd-MM-yyyy but received as " + dto.getBookingDateStr());
        }
        return booking;
    }

    private List<Integer> getAllRoomNumberList(){
        List<Integer> allRoomList = new ArrayList<>();
        for(int i =1; i<=datastore.getTotalRooms(); i++){
            allRoomList.add(i);
        }
        return allRoomList;
    }
}
