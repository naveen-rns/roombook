package com.practice.roombook.entity;

import org.springframework.context.annotation.Bean;

import java.util.Date;

/**
 * Entity object representing table in the database
 */
public class Booking {
    private String bookingRefID;
    private int roomNumber;
    private String guestName;
    private Date bookingDate;

    public Booking(int roomNumber, String guestName, Date bookingDate) {
        this.roomNumber = roomNumber;
        this.guestName = guestName;
        this.bookingDate = bookingDate;
    }

    public String getBookingRefID() {
        return bookingRefID;
    }

    public void setBookingRefID(String bookingRefID) {
        this.bookingRefID = bookingRefID;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingRefID='" + bookingRefID + '\'' +
                ", roomNumber=" + roomNumber +
                ", guestName='" + guestName + '\'' +
                ", bookingDate=" + bookingDate +
                '}';
    }
}
