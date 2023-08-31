package com.checkin.checkin.service;

import com.checkin.checkin.exceptions.CheckInNotFoundException;
import com.checkin.checkin.model.CheckIn;
import com.checkin.checkin.model.dto.CheckInDTO;
import com.checkin.checkin.repository.CheckInRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CheckInService {
    @Autowired
    private CheckInRepository checkInRepository;
    public CheckIn create(CheckInDTO checkInDTO) {
        calculateDailyValue(checkInDTO);
        return checkInRepository.save(newCheckIn(checkInDTO));
    }
    private CheckIn newCheckIn(CheckInDTO checkInDTO){
        CheckIn checkIn = new CheckIn();

        if (checkInDTO.getId() != null) {
            Optional<CheckIn> existingCheckIn = checkInRepository.findById(checkInDTO.getId());

            if (existingCheckIn != null) {
                checkIn.setId(existingCheckIn.get().getId());
            }
        }
        checkIn.setGuest(checkInDTO.getGuest());
        checkIn.setEntryDate(checkInDTO.getEntryDate());
        checkIn.setDepartureDate(checkInDTO.getDepartureDate());
        checkIn.setAddVehicle(checkInDTO.isAddVehicle());
        checkIn.setTotalValue(checkInDTO.getTotalValue());

        return checkIn;
    }
    public CheckIn update(Long id, @Valid CheckInDTO checkInDTO) throws Exception {
        if (!checkInRepository.existsById(id)) {
            throw new CheckInNotFoundException(id);
        }
        checkInDTO.setId(id);
        calculateDailyValue(checkInDTO);
        var oldCheckIn = checkInRepository.findById(id).get();
        oldCheckIn = newCheckIn(checkInDTO);
        return checkInRepository.save(oldCheckIn);
    }
    public void delete(Long id) {
        if (checkInRepository.existsById(id)) {
            checkInRepository.deleteById(id);
        } else {
            throw new CheckInNotFoundException(id);
        }
    }
    public CheckInDTO getById(Long id) {
        Optional<CheckIn> checkInOptional = checkInRepository.findById(id);
        if (checkInOptional.isEmpty()) {
            throw new CheckInNotFoundException(id);
        }
        return new CheckInDTO(checkInOptional.get());
    }
    public List<CheckInDTO> getAll() {
        var checkInList = checkInRepository.findAll();
        var checkInDTOList = new ArrayList<CheckInDTO>();

        for (CheckIn checkIn : checkInList) {
            checkInDTOList.add(new CheckInDTO(checkIn));
        }
        return checkInDTOList;
    }
    public List<CheckIn> findByGuestId(Long id) {
        return checkInRepository.findByGuestId(id);
    }
    public List<CheckIn> findByDepartureDate() {
        return checkInRepository.findByDepartureDate(LocalDateTime.now());
    }
    public List<CheckIn> findByEntryDate() {
        return checkInRepository.findByEntryDate(LocalDateTime.now());
    }
    private void calculateDailyValue(CheckInDTO checkInDTO) {
        double baseDailyValue = 0;

        for (LocalDateTime today = checkInDTO.getEntryDate(); today.isBefore(checkInDTO.getDepartureDate()); today = today.plusDays(1)) {
            baseDailyValue += getAccommodationValue(today);
            if (checkInDTO.isAddVehicle()) {
                baseDailyValue += getParkingValue(today);
            }
        }

        LocalDateTime today;
        if (checkInDTO.getEntryDate().toLocalTime().isAfter(LocalTime.of(16, 30))) {
            today = checkInDTO.getDepartureDate().plusDays(1);
            baseDailyValue += getAccommodationValue(today);
            if (checkInDTO.isAddVehicle()) {
                baseDailyValue += getParkingValue(today);
            }
        }

        checkInDTO.setTotalValue(baseDailyValue);
    }
    public double getAccommodationValue(LocalDateTime today) {
        return (today.getDayOfWeek() == DayOfWeek.SATURDAY || today.getDayOfWeek() == DayOfWeek.SUNDAY) ? 150.00 : 120.00;
    }
    public double getParkingValue(LocalDateTime today) {
        return (today.getDayOfWeek() == DayOfWeek.SATURDAY || today.getDayOfWeek() == DayOfWeek.SUNDAY) ? 20.00 : 15.00;
    }
}
