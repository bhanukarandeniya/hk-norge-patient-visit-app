package com.norge.patientvisit.controller.errors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.zalando.problem.AbstractThrowableProblem;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BadRequestAlertException.class, HolidayEntityCreationException.class})
    protected ResponseEntity<Object> handleCustomException(AbstractThrowableProblem ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, ex.getDetail() + " " + ex.getParameters(), new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }


}
