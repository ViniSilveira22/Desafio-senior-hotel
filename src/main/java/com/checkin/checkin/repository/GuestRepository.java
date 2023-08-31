package com.checkin.checkin.repository;

import com.checkin.checkin.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    @Query("from Guest where name like %:name%")
    List<Guest> findByName(@Param("name")String name);
    @Query("from Guest where document = :document")
    Guest findByDocument(@Param("document")String document);
    @Query("from Guest where phone = :phone")
    Guest findByPhone(@Param("phone")String phone);

}
