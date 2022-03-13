package com.practice.roombook.dto;

import java.util.Date;

public class BookingDto {
    private String bookingRefID;
    private int roomNumber;
    private String guestName;
    private String bookingDateStr;

    public BookingDto(){

    }
    public BookingDto(int roomNumber, String guestName, String bookingDateStr) {
        this.roomNumber = roomNumber;
        this.guestName = guestName;
        this.bookingDateStr = bookingDateStr;
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

    public String getBookingDateStr() {
        return bookingDateStr;
    }

    public void setBookingDateStr(String bookingDateStr) {
        this.bookingDateStr = bookingDateStr;
    }

    @Override
    public String toString() {
        return "BookingDto{" +
                "bookingRefID='" + bookingRefID + '\'' +
                ", roomNumber=" + roomNumber +
                ", guestName='" + guestName + '\'' +
                ", bookingDateStr='" + bookingDateStr + '\'' +
                '}';
    }
}
