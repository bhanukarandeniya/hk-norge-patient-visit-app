package com.norge.patientvisit.service;

import com.norge.patientvisit.domain.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Holiday}.
 */
public interface HolidayService {
    /**
     * Save a holiday.
     *
     * @param holiday the entity to save.
     * @return the persisted entity.
     */
    Holiday save(Holiday holiday);

    /**
     * Partially updates a holiday.
     *
     * @param holiday the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Holiday> partialUpdate(Holiday holiday);

    /**
     * Get all the holidays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Holiday> findAll(Pageable pageable);

    /**
     * Get the "id" holiday.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Holiday> findOne(Long id);

    /**
     * Delete the "id" holiday.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Check today is a Holiday persisted on DB
     * @return boolean.
     */
    boolean validateCreateModifyDate();

    /**
     * Get the "id" holiday with active status true.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Holiday> findOneWithActiveStatus(Long id);

}
