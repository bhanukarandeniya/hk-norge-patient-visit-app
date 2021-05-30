package com.norge.patientvisit.controller;

import com.norge.patientvisit.controller.errors.BadRequestAlertException;
import com.norge.patientvisit.controller.errors.ErrorConstants;
import com.norge.patientvisit.controller.errors.HolidayEntityCreationException;
import com.norge.patientvisit.domain.Patient;
import com.norge.patientvisit.dto.DtoConverter;
import com.norge.patientvisit.dto.PatientDto;
import com.norge.patientvisit.dto.PatientPageDto;
import com.norge.patientvisit.repository.PatientRepository;
import com.norge.patientvisit.service.HolidayService;
import com.norge.patientvisit.service.PatientService;
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
 * REST controller for managing {@link Patient}.
 */
@RestController
@RequestMapping("/api")
public class PatientController {

    private final Logger log = LoggerFactory.getLogger(PatientController.class);

    private static final String ENTITY_NAME = "patient";

    @Value("${norge.clientApp.name}")
    private String applicationName;

    private final PatientService patientService;

    private final PatientRepository patientRepository;

    private final HolidayService holidayService;

    private final DtoConverter<Patient, PatientDto> dtoDtoConverter;

    public PatientController(PatientService patientService, PatientRepository patientRepository,
                             HolidayService holidayService, DtoConverter<Patient, PatientDto> dtoDtoConverter) {
        this.patientService = patientService;
        this.patientRepository = patientRepository;
        this.holidayService = holidayService;
        this.dtoDtoConverter = dtoDtoConverter;
    }

    /**
     * {@code POST  /patients} : Create a new patient.
     *
     * @param patient the patient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patient, or with status {@code 400 (Bad Request)} if the patient has already an ID.
     */
    @PostMapping("/patients")
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto patient) throws URISyntaxException,
            ClassNotFoundException {
        log.debug("REST request to save Patient : {}", patient);
        if (patient.getId() != null) {
            throw new BadRequestAlertException("A new patient cannot already have an ID", ENTITY_NAME, "id exists");
        } else if (!holidayService.validateCreateModifyDate()) {
            throw new HolidayEntityCreationException(ErrorConstants.HOLIDAY_ENTITY_CREATE_ERROR, ENTITY_NAME, "created or modified invalid");
        }
        Patient result;
        try {
            result = patientService.save(dtoDtoConverter.convertToEntity(patient, Patient.class));
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestAlertException(ErrorConstants.ENTITY_DUPLICATION_ERROR, ENTITY_NAME, "patientId exist");
        }
        return ResponseEntity
                .created(new URI("/api/patients/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(dtoDtoConverter.convertToDto(result, PatientDto.class));
    }

    /**
     * {@code PUT  /patients/:id} : Updates an existing patient.
     *
     * @param id      the id of the patient to save.
     * @param patient the patient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patient,
     * or with status {@code 400 (Bad Request)} if the patient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patient couldn't be updated.
     */
    @PutMapping("/patients/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable(value = "id") final Long id,
                                                    @Valid @RequestBody PatientDto patient) throws ClassNotFoundException {
        log.debug("REST request to update Patient : {}, {}", id, patient);
        if (patient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id null");
        } else if (!Objects.equals(id, patient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "id invalid");
        } else if (patientService.findOneWithActiveStatus(id).isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        } else if (!holidayService.validateCreateModifyDate()) {
            throw new HolidayEntityCreationException(ErrorConstants.HOLIDAY_ENTITY_CREATE_ERROR, ENTITY_NAME, "created or modified invalid");
        }
        Patient result = patientService.save(dtoDtoConverter.convertToEntity(patient, Patient.class));
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patient.getId().toString()))
                .body(dtoDtoConverter.convertToDto(result, PatientDto.class));
    }

    /**
     * {@code PATCH  /patients/:id} : Partial updates given fields of an existing patient, field will ignore if it is null
     *
     * @param id      the id of the patient to save.
     * @param patient the patient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patient,
     * or with status {@code 400 (Bad Request)} if the patient is not valid,
     * or with status {@code 404 (Not Found)} if the patient is not found,
     * or with status {@code 500 (Internal Server Error)} if the patient couldn't be updated.
     */
    @PatchMapping(value = "/patients/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PatientDto> partialUpdatePatient(
            @PathVariable(value = "id") final Long id, @NotNull @RequestBody PatientDto patient
    ) throws ClassNotFoundException {
        log.debug("REST request to partial update Patient partially : {}, {}", id, patient);
        if (patient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id null");
        } else if (!Objects.equals(id, patient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "id invalid");
        } else if (patientService.findOneWithActiveStatus(id).isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        } else if (!holidayService.validateCreateModifyDate()) {
            throw new HolidayEntityCreationException(ErrorConstants.HOLIDAY_ENTITY_CREATE_ERROR, ENTITY_NAME, "created or modified invalid");
        }
        Optional<Patient> result = patientService.partialUpdate(dtoDtoConverter.convertToEntity(patient, Patient.class));
        return ResponseUtil.wrapOrNotFound(
                Optional.of(dtoDtoConverter.convertToDto(result.get(), PatientDto.class)),
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patient.getId().toString())
        );
    }

    /**
     * {@code GET  /patients} : get all the patients.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patients in body.
     */
    @GetMapping("/patients")
    public ResponseEntity<PatientPageDto> getAllPatients(Pageable pageable) throws ClassNotFoundException {
        log.debug("REST request to get a page of Patients");
        Page<Patient> page = patientService.findPatientsByActive(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).
                body(new PatientPageDto().setList(dtoDtoConverter.convertToDtoList(page.getContent(), PatientDto.class)));
    }

    /**
     * {@code GET  /patients/:id} : get the "id" patient.
     *
     * @param id the id of the patient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable Long id) throws ClassNotFoundException {
        log.debug("REST request to get Patient : {}", id);
        Optional<Patient> patient = patientService.findOneWithActiveStatus(id);
        if (patient.isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        }
        return ResponseUtil.wrapOrNotFound(Optional.of(dtoDtoConverter.convertToDto(patient.get(), PatientDto.class)));
    }

    /**
     * {@code DELETE  /patients/:id} : delete the "id" patient.
     *
     * @param id the id of the patient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        log.debug("REST request to delete Patient : {}", id);
        Optional<Patient> patient = patientService.findOneWithActiveStatus(id);
        if (patient.isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        }
        patient.get().setActive(false);
        patientService.save(patient.get());
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
