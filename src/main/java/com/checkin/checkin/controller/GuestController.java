package com.checkin.checkin.controller;

import com.checkin.checkin.model.Guest;
import com.checkin.checkin.model.dto.GuestDTO;
import com.checkin.checkin.service.GuestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {
    @Autowired
    private GuestService guestService;

    @PostMapping
    public ResponseEntity<Guest> create(@Valid @RequestBody GuestDTO guestDTO) throws Exception {
        Guest guest = guestService.create(guestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(guest.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<GuestDTO> delete (@PathVariable Long id) throws Exception {
        guestService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GuestDTO> update(@PathVariable Long id, @Valid @RequestBody GuestDTO guestDTO) throws Exception {
        Guest guest = guestService.update(id, guestDTO);
        return ResponseEntity.ok().body(new GuestDTO(guest));
    }

    @GetMapping
    public ResponseEntity<List<GuestDTO>> getAll() {
        List<GuestDTO> guests = guestService.getAll();
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> getById(@PathVariable Long id) {
        GuestDTO guestDTO = guestService.getById(id);
        return new ResponseEntity<>(guestDTO, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Guest>> getByName(@PathVariable String name) {
        List<Guest> guestsFound = guestService.findByName(name);
        return new ResponseEntity<>(guestsFound, HttpStatus.OK);
    }

    @GetMapping("/document/{document}")
    public ResponseEntity<Guest> getByDocument(@PathVariable String document) {
        Guest guestsFound = guestService.findByDocument(document);
        return new ResponseEntity<>(guestsFound, HttpStatus.OK);
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<Guest> getByPhone(@PathVariable String phone) {
        Guest guestsFound = guestService.findByPhone(phone);
        return new ResponseEntity<>(guestsFound, HttpStatus.OK);
    }

    @GetMapping("/out")
    public ResponseEntity<List<GuestDTO>> getOut() {
        List<GuestDTO> outGuests = guestService.findByDepartureDate();
        return ResponseEntity.ok(outGuests);
    }

    @GetMapping("/in")
    public ResponseEntity<List<GuestDTO>> getIn() {
        List<GuestDTO> inGuests = guestService.findByEntryDate();
        return ResponseEntity.ok(inGuests);
    }

 }
