package com.norge.patientvisit.controller;

import com.norge.patientvisit.controller.errors.BadRequestAlertException;
import com.norge.patientvisit.controller.errors.ErrorConstants;
import com.norge.patientvisit.controller.errors.HolidayEntityCreationException;
import com.norge.patientvisit.domain.Holiday;
import com.norge.patientvisit.dto.DtoConverter;
import com.norge.patientvisit.dto.HolidayDto;
import com.norge.patientvisit.dto.HolidayPageDto;
import com.norge.patientvisit.repository.HolidayRepository;
import com.norge.patientvisit.service.HolidayService;
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
 * REST controller for managing {@link Holiday}.
 */
@RestController
@RequestMapping("/api")
public class HolidayController {

    private final Logger log = LoggerFactory.getLogger(HolidayController.class);

    private static final String ENTITY_NAME = "holiday";

    @Value("${norge.clientApp.name}")
    private String applicationName;

    private final HolidayService holidayService;

    private final HolidayRepository holidayRepository;

    private final DtoConverter<Holiday, HolidayDto> dtoDtoConverter;

    public HolidayController(HolidayService holidayService, HolidayRepository holidayRepository,
                             DtoConverter<Holiday, HolidayDto> dtoDtoConverter) {
        this.holidayService = holidayService;
        this.holidayRepository = holidayRepository;
        this.dtoDtoConverter = dtoDtoConverter;
    }

    /**
     * {@code POST  /holidays} : Create a new holiday.
     *
     * @param holiday the holiday to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new holiday, or with status {@code 400 (Bad Request)} if the holiday has already an ID.
     */
    @PostMapping("/holidays")
    public ResponseEntity<HolidayDto> createHoliday(@Valid @RequestBody HolidayDto holiday)
            throws URISyntaxException, ClassNotFoundException {
        log.debug("REST request to save Holiday : {}", holiday);
        if (holiday.getId() != null) {
            throw new BadRequestAlertException("A new holiday cannot already have an ID", ENTITY_NAME, "id exists");
        } else if (!holidayService.validateCreateModifyDate()) {
            throw new HolidayEntityCreationException(ErrorConstants.HOLIDAY_ENTITY_CREATE_ERROR, ENTITY_NAME, "created or modified invalid");
        }
        Holiday result;
        try {
            result = holidayService.save(dtoDtoConverter.convertToEntity(holiday, Holiday.class));
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestAlertException(ErrorConstants.ENTITY_DUPLICATION_ERROR, ENTITY_NAME, "holidayDate exist");
        }
        return ResponseEntity
                .created(new URI("/api/holidays/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(dtoDtoConverter.convertToDto(result, HolidayDto.class));
    }

    /**
     * {@code PUT  /holidays/:id} : Updates an existing holiday.
     *
     * @param id      the id of the holiday to save.
     * @param holiday the holiday to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holiday,
     * or with status {@code 400 (Bad Request)} if the holiday is not valid,
     * or with status {@code 500 (Internal Server Error)} if the holiday couldn't be updated.
     */
    @PutMapping("/holidays/{id}")
    public ResponseEntity<HolidayDto> updateHoliday(@PathVariable(value = "id") final Long id,
                                                    @Valid @RequestBody HolidayDto holiday)
            throws ClassNotFoundException {
        log.debug("REST request to update Holiday : {}, {}", id, holiday);
        if (holiday.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id null");
        } else if (!Objects.equals(id, holiday.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "id invalid");
        } else if (holidayService.findOneWithActiveStatus(id).isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        } else if (!holidayService.validateCreateModifyDate()) {
            throw new HolidayEntityCreationException(ErrorConstants.HOLIDAY_ENTITY_CREATE_ERROR, ENTITY_NAME,
                    "created or modified invalid");
        }
        Holiday result;
        try {
            result = holidayService.save(dtoDtoConverter.convertToEntity(holiday, Holiday.class));
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestAlertException(ErrorConstants.ENTITY_DUPLICATION_ERROR, ENTITY_NAME, "holidayDate already exist in DB");
        }
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, holiday.getId().toString()))
                .body(dtoDtoConverter.convertToDto(result, HolidayDto.class));
    }

    /**
     * {@code PATCH  /holidays/:id} : Partial updates given fields of an existing holiday, field will ignore if it is null
     *
     * @param id      the id of the holiday to save.
     * @param holiday the holiday to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holiday,
     * or with status {@code 400 (Bad Request)} if the holiday is not valid,
     * or with status {@code 404 (Not Found)} if the holiday is not found,
     * or with status {@code 500 (Internal Server Error)} if the holiday couldn't be updated.
     */
    @PatchMapping(value = "/holidays/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<HolidayDto> partialUpdateHoliday(@PathVariable(value = "id") final Long id,
                                                           @NotNull @RequestBody HolidayDto holiday)
            throws ClassNotFoundException {
        log.debug("REST request to partial update Holiday partially : {}, {}", id, holiday);
        if (holiday.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id null");
        } else if (!Objects.equals(id, holiday.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "id invalid");
        } else if (holidayService.findOneWithActiveStatus(id).isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        } else if (!holidayService.validateCreateModifyDate()) {
            throw new HolidayEntityCreationException(ErrorConstants.HOLIDAY_ENTITY_CREATE_ERROR, ENTITY_NAME,
                    "created or modified invalid");
        }
        Optional<Holiday> result;
        try {
            result = holidayService.partialUpdate(dtoDtoConverter.convertToEntity(holiday, Holiday.class));
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestAlertException(ErrorConstants.ENTITY_DUPLICATION_ERROR, ENTITY_NAME, "holidayDate already exist in DB");
        }
        return ResponseUtil.wrapOrNotFound(
                Optional.of(dtoDtoConverter.convertToDto(result.get(), HolidayDto.class)),
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, holiday.getId().toString())
        );
    }

    /**
     * {@code GET  /holidays} : get all the holidays.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of holidays in body.
     */
    @GetMapping("/holidays")
    public ResponseEntity<HolidayPageDto> getAllHolidays(Pageable pageable) throws ClassNotFoundException {
        log.debug("REST request to get a page Holidays");
        Page<Holiday> page = holidayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).
                body(new HolidayPageDto().setList(dtoDtoConverter.convertToDtoList(page.getContent(), HolidayDto.class)));
    }

    /**
     * {@code GET  /holidays/:id} : get the "id" holiday.
     *
     * @param id the id of the holiday to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the holiday, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/holidays/{id}")
    public ResponseEntity<HolidayDto> getHoliday(@PathVariable Long id) throws ClassNotFoundException {
        log.debug("REST request to get Holiday : {}", id);
        Optional<Holiday> holiday = holidayService.findOneWithActiveStatus(id);
        if (holiday.isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        }
        return ResponseUtil.wrapOrNotFound(Optional.of(dtoDtoConverter.convertToDto(holiday.get(), HolidayDto.class)));
    }

    /**
     * {@code DELETE  /holidays/:id} : delete the "id" holiday.
     *
     * @param id the id of the holiday to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/holidays/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
        log.debug("REST request to delete Holiday : {}", id);
        if (!holidayService.validateCreateModifyDate()) {
            throw new HolidayEntityCreationException(ErrorConstants.HOLIDAY_ENTITY_CREATE_ERROR, ENTITY_NAME,
                    "created or modified invalid");
        }
        Optional<Holiday> holiday = holidayService.findOneWithActiveStatus(id);
        if (holiday.isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        }
        holiday.get().setActive(false);
        holidayService.save(holiday.get());
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
