package com.checkin.checkin.service;

import com.checkin.checkin.exceptions.GuestNotFoundException;
import com.checkin.checkin.model.CheckIn;
import com.checkin.checkin.model.Guest;
import com.checkin.checkin.model.dto.GuestDTO;
import com.checkin.checkin.repository.GuestRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class GuestService {
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private CheckInService checkInService;
    public Guest create(GuestDTO guestDTO) throws Exception {
        return guestRepository.save(newGuest(guestDTO));
    }
    private Guest newGuest(GuestDTO guestDTO) throws Exception {

        Guest guest = new Guest();

        if (guestDTO.getId() != null) {
            Optional<Guest> existingCheckIn = guestRepository.findById(guestDTO.getId());
            System.out.println(existingCheckIn);

            if (existingCheckIn != null) {
                guest.setId(existingCheckIn.get().getId());
            }
            else {
                throw new Exception("Id não existente: " + guestDTO.getId());
            }
        }
        guest.setName(guestDTO.getName());
        guest.setDocument(guestDTO.getDocument());
        guest.setPhone(guestDTO.getPhone());

        return guest;
    }
    public Guest update(Long id, @Valid GuestDTO guestDTO) throws Exception {
        if (!guestRepository.existsById(id)) {
            throw new GuestNotFoundException(id);
        }
        guestDTO.setId(id);
        var oldGuest = guestRepository.findById(id).get();
        oldGuest = newGuest(guestDTO);
        return guestRepository.save(oldGuest);
    }
    public void delete(Long id) throws Exception {
        try {
            Guest guest = guestRepository.findById(id).get();
            boolean allowDelete = true;
            if (guest.getCheckIns().size() > 0) {
                for (CheckIn checkIn : guest.getCheckIns()) {
                    if(checkIn.getDepartureDate().isAfter(LocalDateTime.now())){
                        allowDelete = false;
                        break;
                    }
                }
            }
            if(allowDelete){
                guestRepository.deleteById(id);
            }

        } catch (NoSuchElementException ex) {
            throw new GuestNotFoundException(id);
        }

    }
    public GuestDTO getById(Long id) {
        if (!guestRepository.existsById(id)) {
            throw new GuestNotFoundException(id);
        }
        var guest = guestRepository.findById(id);
        var checkIns= checkInService.findByGuestId(id);
        var guestDTO = calculateTotalAmount(checkIns, guest.get());
        return guestDTO;
    }
    public List<GuestDTO> getAll() {
        var guestList = guestRepository.findAll();
        var guestDTOList = new ArrayList<GuestDTO>();

        for (Guest guest : guestList) {
            List<CheckIn> checkIns = checkInService.findByGuestId(guest.getId());
            GuestDTO guestDTO = calculateTotalAmount(checkIns, guest);
            guestDTOList.add(guestDTO);
        }
        return guestDTOList;
    }
    public List<Guest> findByName(String name) {
        return guestRepository.findByName(name);
    }
    public Guest findByDocument(String document) {
        return guestRepository.findByDocument(document);
    }
    public Guest findByPhone(String phone) {
        return guestRepository.findByPhone(phone);
    }
    public List<GuestDTO> findByDepartureDate() {
        List<CheckIn> outCheckins = checkInService.findByDepartureDate();
        List<GuestDTO> outGuests = new ArrayList<>();

        for (CheckIn checkin : outCheckins) {
            GuestDTO guestDTO = calculateTotalAmount(outCheckins, checkin.getGuest());
            outGuests.add(guestDTO);
        }

        return outGuests;
    }
    public List<GuestDTO> findByEntryDate() {
        List<CheckIn> inCheckins = checkInService.findByEntryDate();
        List<GuestDTO> inGuests = new ArrayList<>();

        for (CheckIn checkin : inCheckins) {
            GuestDTO guestDTO = calculateTotalAmount(inCheckins, checkin.getGuest());
            inGuests.add(guestDTO);
        }

        return inGuests;
    }
    public GuestDTO calculateTotalAmount(List<CheckIn> checkIns, Guest guest) {
        var total = 0;
        LocalDateTime lastCheckIn = LocalDateTime.MIN;
        var lastCheckInValue = 0.0;
        for (CheckIn checkIn:checkIns) {
            total += checkIn.getTotalValue();

            if(checkIn.getEntryDate().isAfter(lastCheckIn) && checkIn.getEntryDate().isBefore(LocalDateTime.now())) {
                lastCheckIn = checkIn.getEntryDate();
                lastCheckInValue = checkIn.getTotalValue();
            }
        }
        GuestDTO guestDTO = new GuestDTO(guest);
        guestDTO.setLastCheckInAmount(lastCheckInValue);
        guestDTO.setTotalCheckInAmount(total);
        return guestDTO;
    }
}
