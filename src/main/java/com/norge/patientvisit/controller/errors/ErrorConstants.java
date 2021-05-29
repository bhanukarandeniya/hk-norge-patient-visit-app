package com.norge.patientvisit.controller.errors;

import java.net.URI;

public class ErrorConstants {

    private ErrorConstants() {
    }

    public static final URI DEFAULT_TYPE = URI.create("/problem-with-message");
    public static final URI ERR_HOLIDAY = URI.create("");
    public static final URI ERR_DUPLICATION = URI.create("");
    public static final String HOLIDAY_ENTITY_CREATE_ERROR = "Entity cannot be created on a holiday";
    public static final String HOLIDAY_ENTITY_MODIFY_ERROR = "Entity cannot be modified on a holiday";
    public static final String ENTITY_DUPLICATION_ERROR = "Entity with the given id already exist";

}
