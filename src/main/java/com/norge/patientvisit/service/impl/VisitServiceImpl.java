package com.norge.patientvisit.service.impl;

import com.norge.patientvisit.domain.Visit;
import com.norge.patientvisit.repository.VisitRepository;
import com.norge.patientvisit.service.VisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Visit}.
 */
@Service
@Transactional
public class VisitServiceImpl implements VisitService {

    private final Logger log = LoggerFactory.getLogger(VisitServiceImpl.class);

    private final VisitRepository visitRepository;

    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Visit save(Visit visit) {
        log.debug("Request to save Visit : {}", visit);
        return visitRepository.save(visit);
    }

    @Override
    public Optional<Visit> partialUpdate(Visit visit) {
        log.debug("Request to partially update Visit : {}", visit);

        return visitRepository
                .findById(visit.getId())
                .map(
                        existingVisit -> {
                            if (visit.getVisitDate() != null) {
                                existingVisit.setVisitDate(visit.getVisitDate());
                            }
                            if (visit.getReason() != null) {
                                existingVisit.setReason(visit.getReason());
                            }
                            if (visit.getCreated() != null) {
                                existingVisit.setCreated(visit.getCreated());
                            }
                            if (visit.getModified() != null) {
                                existingVisit.setModified(visit.getModified());
                            }
                            if (visit.getCreatedBy() != null) {
                                existingVisit.setCreatedBy(visit.getCreatedBy());
                            }
                            if (visit.getModifiedBy() != null) {
                                existingVisit.setModifiedBy(visit.getModifiedBy());
                            }

                            return existingVisit;
                        }
                )
                .map(visitRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Visit> findAll(Pageable pageable) {
        log.debug("Request to get all Visits");
        return visitRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Visit> findOne(Long id) {
        log.debug("Request to get Visit : {}", id);
        return visitRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Visit : {}", id);
        visitRepository.deleteById(id);
    }
}
