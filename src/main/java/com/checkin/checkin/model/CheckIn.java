package com.checkin.checkin.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class CheckIn implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "idguest")
    private Guest guest;
    private LocalDateTime entryDate;
    private LocalDateTime departureDate;
    private boolean addVehicle;
    private double totalValue;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Guest getGuest() {
        return guest;
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
