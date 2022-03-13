## Room Booking Application Manager
This project implements a simple hotel room booking manager.
The number of rooms are configurable in the yml file.

The application exposes these services
1. Store a booking 
   - This is a POST service call that accepts guest name, room number and a date
   - On success it returns a booking reference ID
   - If the room is already booked then throws a BookingException
   - Storing the booking is thread safe. So no two threads can store same booking at same time. 
   
2. Find available rooms on a given date
   - This service returns all the room numbers that are available for the given date

3. Find all bookings for a given guest name
    - This service returns all the bookings with the booking reference Ids for the given guest name

