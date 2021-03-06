package com.norge.patientvisit.service.impl;

import com.norge.patientvisit.domain.Physician;
import com.norge.patientvisit.repository.PhysicianRepository;
import com.norge.patientvisit.service.PhysicianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Physician}.
 */
@Service
@Transactional
public class PhysicianServiceImpl implements PhysicianService {

    private final Logger log = LoggerFactory.getLogger(PhysicianServiceImpl.class);

    private final PhysicianRepository physicianRepository;

    public PhysicianServiceImpl(PhysicianRepository physicianRepository) {
        this.physicianRepository = physicianRepository;
    }

    @Override
    public Physician save(Physician physician) {
        log.debug("Request to save Physician : {}", physician);
        return physicianRepository.save(physician);
    }

    @Override
    public Optional<Physician> partialUpdate(Physician physician) {
        log.debug("Request to partially update Physician : {}", physician);

        return physicianRepository
                .findById(physician.getId())
                .map(
                        existingPhysician -> {
                            if (physician.getPhysicianId() != null) {
                                existingPhysician.setPhysicianId(physician.getPhysicianId());
                            }
                            if (physician.getName() != null) {
                                existingPhysician.setName(physician.getName());
                            }
                            if (physician.getCreated() != null) {
                                existingPhysician.setCreated(physician.getCreated());
                            }
                            if (physician.getModified() != null) {
                                existingPhysician.setModified(physician.getModified());
                            }
                            if (physician.getCreatedBy() != null) {
                                existingPhysician.setCreatedBy(physician.getCreatedBy());
                            }
                            if (physician.getModifiedBy() != null) {
                                existingPhysician.setModifiedBy(physician.getModifiedBy());
                            }

                            return existingPhysician;
                        }
                )
                .map(physicianRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Physician> findAll(Pageable pageable) {
        log.debug("Request to get all Physicians");
        return physicianRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Physician> findOne(Long id) {
        log.debug("Request to get Physician : {}", id);
        return physicianRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Physician : {}", id);
        physicianRepository.deleteById(id);
    }

    @Override
    public Optional<Physician> findOneWithActiveStatus(Long id) {
        return physicianRepository.findPhysicianByIdAndActive(id, true);
    }

    @Override
    public Page<Physician> findPhysiciansByActive(Pageable pageable) {
        return physicianRepository.findPhysicianByActive(pageable, true);
    }
}
