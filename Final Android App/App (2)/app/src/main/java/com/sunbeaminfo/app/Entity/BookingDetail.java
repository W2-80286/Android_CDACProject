package com.sunbeaminfo.app.Entity;

import com.google.gson.annotations.SerializedName;

public class BookingDetail {
    @SerializedName("id")
    private int id;

    @SerializedName("event_name")
    private String eventName;

    @SerializedName("venue_name")
    private String venueName;

    @SerializedName("attendees")
    private int attendees;

    @SerializedName("total_price")
    private double totalPrice;

    @SerializedName("booking_date")
    private String bookingDate;

    @SerializedName("booking_timestamp")
    private String bookingTimestamp;

    @SerializedName("payment_status")
    private String paymentStatus;

    public BookingDetail(String s, String s1, int i, double v, String date, String s2, String unpaid) {
    }

    public BookingDetail() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public int getAttendees() {
        return attendees;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTimestamp() {
        return bookingTimestamp;
    }

    public void setBookingTimestamp(String bookingTimestamp) {
        this.bookingTimestamp = bookingTimestamp;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Getter and setter methods
}