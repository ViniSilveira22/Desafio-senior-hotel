package com.checkin.checkin.model.dto;

import com.checkin.checkin.model.CheckIn;
import com.checkin.checkin.model.Guest;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CheckInDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Guest guest;
    private LocalDateTime entryDate;
    private LocalDateTime departureDate;
    private boolean addVehicle;
    private double totalValue;

    public CheckInDTO() {
    }
    public CheckInDTO(CheckIn checkIn) {
        this.id = checkIn.getId();
        this.guest = checkIn.getGuest();
        this.entryDate = checkIn.getEntryDate();
        this.departureDate = checkIn.getDepartureDate();
        this.addVehicle = checkIn.isAddVehicle();
        this.totalValue = checkIn.getTotalValue();
    }

    public Guest getGuest() {
        return guest;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setGuest(Guest guest) {
        this.guest = guest;
    }
    public LocalDateTime getEntryDate() {
        return entryDate;
    }
    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }
    public LocalDateTime getDepartureDate() {
        return departureDate;
    }
    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }
    public boolean isAddVehicle() {
        return addVehicle;
    }
    public void setAddVehicle(boolean addVehicle) {
        this.addVehicle = addVehicle;
    }
    public double getTotalValue() {
        return totalValue;
    }
    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

}
