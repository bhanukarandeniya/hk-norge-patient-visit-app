package com.norge.patientvisit.repository;

import com.norge.patientvisit.domain.Physician;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the Physician entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhysicianRepository extends JpaRepository<Physician, Long> {

    Optional<Physician> findPhysicianByIdAndActive(Long id, boolean active);

    Page<Physician> findPhysicianByActive(Pageable pageable, boolean active);

}
