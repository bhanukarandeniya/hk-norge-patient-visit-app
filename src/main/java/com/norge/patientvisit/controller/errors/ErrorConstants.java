package com.norge.patientvisit.controller.errors;

import java.net.URI;

public class ErrorConstants {

    public static final URI DEFAULT_TYPE = URI.create("/problem-with-message");
    public static final URI ERR_HOLIDAY = URI.create("");
    public static final URI ERR_DUPLICATION = URI.create("");

    private ErrorConstants() {
    }
}
