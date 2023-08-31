package com.checkin.checkin.model.dto;
import com.checkin.checkin.model.Guest;
import java.io.Serializable;

public class GuestDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String document;
    private String phone;
    private double lastCheckInAmount;
    private double totalCheckInAmount;

    public GuestDTO() {
    }
    public GuestDTO(Guest guest) {
        this.id = guest.getId();
        this.name = guest.getName();
        this.document = guest.getDocument();
        this.phone = guest.getPhone();
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDocument() {
        return document;
    }
    public void setDocument(String document) {
        this.document = document;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public double getLastCheckInAmount() {
        return lastCheckInAmount;
    }
    public void setLastCheckInAmount(double lastCheckInAmount) {
        this.lastCheckInAmount = lastCheckInAmount;
    }
    public double getTotalCheckInAmount() {
        return totalCheckInAmount;
    }
    public void setTotalCheckInAmount(double totalCheckInAmount) {
        this.totalCheckInAmount = totalCheckInAmount;
    }
}
