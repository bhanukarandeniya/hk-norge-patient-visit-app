package com.norge.patientvisit.controller;

import com.norge.patientvisit.controller.errors.BadRequestAlertException;
import com.norge.patientvisit.controller.errors.ErrorConstants;
import com.norge.patientvisit.controller.errors.HolidayEntityCreationException;
import com.norge.patientvisit.domain.Physician;
import com.norge.patientvisit.dto.DtoConverter;
import com.norge.patientvisit.dto.PhysicianDto;
import com.norge.patientvisit.dto.PhysicianPageDto;
import com.norge.patientvisit.repository.PhysicianRepository;
import com.norge.patientvisit.service.HolidayService;
import com.norge.patientvisit.service.PhysicianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link Physician}.
 */
@RestController
@RequestMapping("/api")
public class PhysicianController {

    private final Logger log = LoggerFactory.getLogger(PhysicianController.class);

    private static final String ENTITY_NAME = "physician";

    @Value("${norge.clientApp.name}")
    private String applicationName;

    private final PhysicianService physicianService;

    private final PhysicianRepository physicianRepository;

    private final DtoConverter<Physician, PhysicianDto> dtoDtoConverter;

    private final HolidayService holidayService;

    public PhysicianController(PhysicianService physicianService, PhysicianRepository physicianRepository,
                               DtoConverter<Physician, PhysicianDto> dtoDtoConverter, HolidayService holidayService) {
        this.physicianService = physicianService;
        this.physicianRepository = physicianRepository;
        this.dtoDtoConverter = dtoDtoConverter;
        this.holidayService = holidayService;
    }

    /**
     * {@code POST  /physicians} : Create a new physician.
     *
     * @param physician the physician to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new physician, or with status {@code 400 (Bad Request)} if the physician has already an ID.
     */
    @PostMapping("/physicians")
    public ResponseEntity<PhysicianDto> createPhysician(@Valid @RequestBody PhysicianDto physician)
            throws URISyntaxException, ClassNotFoundException {
        log.debug("REST request to save Physician : {}", physician);
        if (physician.getId() != null) {
            throw new BadRequestAlertException("A new physician cannot already have an ID", ENTITY_NAME, "id exists");
        } else if (!holidayService.validateCreateModifyDate()) {
            throw new HolidayEntityCreationException(ErrorConstants.HOLIDAY_ENTITY_CREATE_ERROR, ENTITY_NAME, "created or modified invalid");
        }
        Physician result;
        try {
            result = physicianService.save(dtoDtoConverter.convertToEntity(physician, Physician.class));
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestAlertException(ErrorConstants.ENTITY_DUPLICATION_ERROR, ENTITY_NAME, "physicianId exist");
        }

        return ResponseEntity
                .created(new URI("/api/physicians/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(dtoDtoConverter.convertToDto(result, PhysicianDto.class));
    }

    /**
     * {@code PUT  /physicians/:id} : Updates an existing physician.
     *
     * @param id        the id of the physician to save.
     * @param physician the physician to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated physician,
     * or with status {@code 400 (Bad Request)} if the physician is not valid,
     * or with status {@code 500 (Internal Server Error)} if the physician couldn't be updated.
     */
    @PutMapping("/physicians/{id}")
    public ResponseEntity<PhysicianDto> updatePhysician(@PathVariable(value = "id") final Long id,
                                                        @Valid @RequestBody PhysicianDto physician
    ) throws ClassNotFoundException {
        log.debug("REST request to update Physician : {}, {}", id, physician);
        if (physician.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id null");
        } else if (!Objects.equals(id, physician.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "id invalid");
        } else if (physicianService.findOneWithActiveStatus(id).isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        } else if (!holidayService.validateCreateModifyDate()) {
            throw new HolidayEntityCreationException(ErrorConstants.HOLIDAY_ENTITY_CREATE_ERROR, ENTITY_NAME, "created or modified invalid");
        }
        Physician result = physicianService.save(dtoDtoConverter.convertToEntity(physician, Physician.class));
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, physician.getId().toString()))
                .body(dtoDtoConverter.convertToDto(result, PhysicianDto.class));
    }

    /**
     * {@code PATCH  /physicians/:id} : Partial updates given fields of an existing physician, field will ignore if it is null
     *
     * @param id        the id of the physician to save.
     * @param physician the physician to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated physician,
     * or with status {@code 400 (Bad Request)} if the physician is not valid,
     * or with status {@code 404 (Not Found)} if the physician is not found,
     * or with status {@code 500 (Internal Server Error)} if the physician couldn't be updated.
     */
    @PatchMapping(value = "/physicians/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PhysicianDto> partialUpdatePhysician(@PathVariable(value = "id") final Long id,
                                                               @NotNull @RequestBody Physician physician)
            throws ClassNotFoundException {
        log.debug("REST request to partial update Physician partially : {}, {}", id, physician);
        if (physician.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id null");
        } else if (!Objects.equals(id, physician.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "id invalid");
        } else if (physicianService.findOneWithActiveStatus(id).isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        }
        Optional<Physician> result = physicianService.partialUpdate(physician);
        return ResponseUtil.wrapOrNotFound(
                Optional.of(dtoDtoConverter.convertToDto(result.get(), Physician.class)),
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, physician.getId().toString())
        );
    }

    /**
     * {@code GET  /physicians} : get all the physicians.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of physicians in body.
     */
    @GetMapping("/physicians")
    public ResponseEntity<PhysicianPageDto> getAllPhysicians(Pageable pageable) throws ClassNotFoundException {
        log.debug("REST request to get a page of Physicians");
        Page<Physician> page = physicianService.findPhysiciansByActive(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).
                body(new PhysicianPageDto().setList(dtoDtoConverter.convertToDtoList(page.getContent(), PhysicianPageDto.class)));
    }

    /**
     * {@code GET  /physicians/:id} : get the "id" physician.
     *
     * @param id the id of the physician to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the physician, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/physicians/{id}")
    public ResponseEntity<PhysicianDto> getPhysician(@PathVariable Long id) throws ClassNotFoundException {
        log.debug("REST request to get Physician : {}", id);
        Optional<Physician> physician = physicianService.findOneWithActiveStatus(id);
        if (physician.isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        }
        return ResponseUtil.wrapOrNotFound(Optional.of(dtoDtoConverter.convertToDto(physician.get(), PhysicianDto.class)));
    }

    /**
     * {@code DELETE  /physicians/:id} : delete the "id" physician.
     *
     * @param id the id of the physician to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/physicians/{id}")
    public ResponseEntity<Void> deletePhysician(@PathVariable Long id) {
        log.debug("REST request to delete Physician : {}", id);
        Optional<Physician> physician = physicianService.findOneWithActiveStatus(id);
        if (physician.isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        }
        physician.get().setActive(false);
        physicianService.save(physician.get());
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
