package com.checkin.checkin.repository;

import com.checkin.checkin.model.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

    List<CheckIn> findByGuestId(Long guestId);
    @Query("from CheckIn where guest = :id")
    CheckIn findByIdGuest(@Param("id")Long id);
    @Query("from CheckIn where departureDate < :now")
    List<CheckIn> findByDepartureDate(@Param("now")LocalDateTime now);
    @Query("from CheckIn where departureDate > :now and entryDate < :now")
    List<CheckIn> findByEntryDate(@Param("now")LocalDateTime now);
}
