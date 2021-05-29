package com.norge.patientvisit.service.impl;

import com.norge.patientvisit.domain.Holiday;
import com.norge.patientvisit.repository.HolidayRepository;
import com.norge.patientvisit.service.HolidayService;
import com.norge.patientvisit.util.DateTimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Holiday}.
 */
@Service
@Transactional
public class HolidayServiceImpl implements HolidayService {

    private final Logger log = LoggerFactory.getLogger(HolidayServiceImpl.class);

    private final HolidayRepository holidayRepository;

    public HolidayServiceImpl(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    @Override
    public Holiday save(Holiday holiday) {
        log.debug("Request to save Holiday : {}", holiday);
        holiday.setHolidayDate(DateTimeUtility.setTimeToZero(holiday.getHolidayDate()));
        return holidayRepository.save(holiday);
    }

    @Override
    public Optional<Holiday> partialUpdate(Holiday holiday) {
        log.debug("Request to partially update Holiday : {}", holiday);

        return holidayRepository
                .findById(holiday.getId())
                .map(
                        existingHoliday -> {
                            if (holiday.getHolidayDate() != null) {
                                existingHoliday.setHolidayDate(DateTimeUtility.setTimeToZero(holiday.getHolidayDate()));
                            }
                            if (holiday.getCreated() != null) {
                                existingHoliday.setCreated(holiday.getCreated());
                            }
                            if (holiday.getModified() != null) {
                                existingHoliday.setModified(holiday.getModified());
                            }
                            if (holiday.getCreatedBy() != null) {
                                existingHoliday.setCreatedBy(holiday.getCreatedBy());
                            }
                            if (holiday.getModifiedBy() != null) {
                                existingHoliday.setModifiedBy(holiday.getModifiedBy());
                            }
                            if (holiday.getActive() != null) {
                                existingHoliday.setActive(holiday.getActive());
                            }

                            return existingHoliday;
                        }
                )
                .map(holidayRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Holiday> findAll(Pageable pageable) {
        log.debug("Request to get all Holidays");
        return holidayRepository.findHolidaysByActive(pageable,true);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Holiday> findOne(Long id) {
        log.debug("Request to get Holiday : {}", id);
        return holidayRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Holiday : {}", id);
        holidayRepository.deleteById(id);
    }

    @Override
    public boolean validateCreateModifyDate() {
        return holidayRepository.findHolidaysByHolidayDate(new Date()).isEmpty();
    }

}
