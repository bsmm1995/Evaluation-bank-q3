package com.bp.cbe.configuration;

import com.bp.cbe.domain.dto.error.ErrorResponse;
import com.bp.cbe.exceptions.LoanException;
import com.bp.cbe.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionConfig {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(NotFoundException e, HttpServletRequest req) {
        log.error(e.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse(e.getLocalizedMessage(), req.getRequestURI(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> invalidFormatException(InvalidFormatException e, HttpServletRequest req) {
        log.error(e.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse("User type does not exist", req.getRequestURI(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoanException.class)
    public ResponseEntity<ErrorResponse> loanException(LoanException e, HttpServletRequest req) {
        log.error(e.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse(e.getLocalizedMessage(), req.getRequestURI(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }
}
