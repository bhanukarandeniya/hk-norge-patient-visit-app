package com.norge.patientvisit.repository;

import com.norge.patientvisit.domain.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the Patient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Page<Patient> findPatientsByActive(Pageable pageable, boolean active);

    Optional<Patient> findPatientByIdAndActive(Long id, boolean active);

}
