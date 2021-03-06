package com.norge.patientvisit.service.impl;

import com.norge.patientvisit.domain.Patient;
import com.norge.patientvisit.repository.PatientRepository;
import com.norge.patientvisit.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Patient}.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient save(Patient patient) {
        log.debug("Request to save Patient : {}", patient);
        return patientRepository.save(patient);
    }

    @Override
    public Optional<Patient> partialUpdate(Patient patient) {
        log.debug("Request to partially update Patient : {}", patient);

        return patientRepository
                .findById(patient.getId())
                .map(
                        existingPatient -> {
                            if (patient.getPatientId() != null) {
                                existingPatient.setPatientId(patient.getPatientId());
                            }
                            if (patient.getName() != null) {
                                existingPatient.setName(patient.getName());
                            }
                            if (patient.getDob() != null) {
                                existingPatient.setDob(patient.getDob());
                            }
                            if (patient.getGender() != null) {
                                existingPatient.setGender(patient.getGender());
                            }
                            if (patient.getCreated() != null) {
                                existingPatient.setCreated(patient.getCreated());
                            }
                            if (patient.getModified() != null) {
                                existingPatient.setModified(patient.getModified());
                            }
                            if (patient.getCreatedBy() != null) {
                                existingPatient.setCreatedBy(patient.getCreatedBy());
                            }

                            return existingPatient;
                        }
                )
                .map(patientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Patient> findPatientsByActive(Pageable pageable) {
        log.debug("Request to get all Patients");
        return patientRepository.findPatientsByActive(pageable, true);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Patient> findOne(Long id) {
        log.debug("Request to get Patient : {}", id);
        return patientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Patient : {}", id);
        patientRepository.deleteById(id);
    }

    @Override
    public Optional<Patient> findOneWithActiveStatus(Long id) {
        return patientRepository.findPatientByIdAndActive(id, true);
    }

}
