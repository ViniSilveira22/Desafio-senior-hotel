package com.checkin.checkin.controller;

import com.checkin.checkin.model.CheckIn;
import com.checkin.checkin.model.dto.CheckInDTO;
import com.checkin.checkin.service.CheckInService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/checkins")
public class CheckInController {
    @Autowired
    private CheckInService checkInService;

    @PostMapping
    public ResponseEntity<CheckIn> create(@Valid @RequestBody CheckInDTO checkInDTO) {
        CheckIn newCheckIn = checkInService.create(checkInDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newCheckIn.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CheckInDTO> delete (@PathVariable Long id) throws Exception {
        checkInService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CheckInDTO> update(@PathVariable Long id, @Valid @RequestBody CheckInDTO checkInDTO) throws Exception {
        CheckIn checkIn = checkInService.update(id, checkInDTO);
        return ResponseEntity.ok().body(new CheckInDTO(checkIn));
    }

    @GetMapping
    public ResponseEntity<List<CheckInDTO>> getAll() {
        List<CheckInDTO> checkIns = checkInService.getAll();
        return ResponseEntity.ok().body(checkIns);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckInDTO> getById(@PathVariable Long id) {
        CheckInDTO checkInDTO = checkInService.getById(id);
        return ResponseEntity.ok().body(checkInDTO);
    }

}
