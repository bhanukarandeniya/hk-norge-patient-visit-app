package com.norge.patientvisit.service;

import com.norge.patientvisit.domain.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Patient}.
 */
public interface PatientService {
    /**
     * Save a patient.
     *
     * @param patient the entity to save.
     * @return the persisted entity.
     */
    Patient save(Patient patient);

    /**
     * Partially updates a patient.
     *
     * @param patient the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Patient> partialUpdate(Patient patient);

    /**
     * Get all the patients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Patient> findPatientsByActive(Pageable pageable);

    /**
     * Get the "id" patient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Patient> findOne(Long id);

    /**
     * Delete the "id" patient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<Patient> findOneWithActiveStatus(Long id);

}
