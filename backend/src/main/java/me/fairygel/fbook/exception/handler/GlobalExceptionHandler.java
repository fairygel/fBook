package me.fairygel.fbook.exception.handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import me.fairygel.fbook.exception.PartialErrorResponse;
import me.fairygel.fbook.exception.ValidationErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleNotValidException(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        BindingResult bindingResult = e.getBindingResult();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        Set<ValidationErrorResponse.Detail> details = fieldErrors
                .stream()
                .map(err -> new ValidationErrorResponse.Detail(
		            err.getField(),
		            err.getDefaultMessage()
                ))
                .collect(Collectors.toSet());

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                "NOT_VALID",
                "Validation error occurred.",
                details
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<PartialErrorResponse> handleValidationException(ValidationException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        PartialErrorResponse errorResponse = new PartialErrorResponse(
                "VALIDATION_ERROR",
                e.getMessage()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<PartialErrorResponse> handleEntityNotFound(EntityNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        PartialErrorResponse errorResponse = new PartialErrorResponse(
                "NOT_FOUND",
                e.getMessage()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<PartialErrorResponse> handleEntityNotFound(NoResourceFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        PartialErrorResponse errorResponse = new PartialErrorResponse(
                "INVALID_PATH",
                "There is no '" + e.getResourcePath() + "' path."
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<PartialErrorResponse> handleHttpMessageNotReadableException(HttpMediaTypeNotSupportedException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        PartialErrorResponse errorResponse = new PartialErrorResponse(
                "INVALID_REQUEST",
                e.getMessage()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<PartialErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        PartialErrorResponse errorResponse = new PartialErrorResponse(
                "NOT_READABLE",
                e.getMessage()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(value = MissingServletRequestPartException.class)
    public ResponseEntity<PartialErrorResponse> handleHttpMessageNotReadableException(MissingServletRequestPartException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        PartialErrorResponse errorResponse = new PartialErrorResponse(
                "MISSING_BODY",
                e.getMessage()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<PartialErrorResponse> handleHttpMessageNotReadableException(DataIntegrityViolationException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        PartialErrorResponse errorResponse = new PartialErrorResponse(
                "REFERENCE_EXCEPTION",
                "Cannot delete or update because of reference constraint."
        );

        return ResponseEntity.status(status).body(errorResponse);
    }
}
