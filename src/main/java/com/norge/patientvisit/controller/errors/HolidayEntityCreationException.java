package com.norge.patientvisit.controller.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class HolidayEntityCreationException extends AbstractThrowableProblem {

    private final String entityName;

    private final String errorKey;

    public HolidayEntityCreationException(String defaultMessage, String entityName, String errorKey) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey);
    }

    public HolidayEntityCreationException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(type, defaultMessage, Status.BAD_REQUEST, defaultMessage, null, null, getAlertParameters(entityName, errorKey));
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }

    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", "error." + errorKey);
        parameters.put("params", entityName);
        return parameters;
    }

}
