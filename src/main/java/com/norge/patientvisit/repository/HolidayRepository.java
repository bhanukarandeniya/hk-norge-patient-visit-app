package com.norge.patientvisit.repository;

import com.norge.patientvisit.domain.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the Holiday entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    Page<Holiday> findHolidaysByActive(Pageable pageable, boolean active);

    List<Holiday> findHolidaysByHolidayDate(Date date);

    Optional<Holiday> findHolidayByIdAndActive(Long id, boolean active);

}
