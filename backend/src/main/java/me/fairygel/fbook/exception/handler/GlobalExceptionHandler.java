package me.fairygel.fbook.exception.handler;

import jakarta.persistence.EntityNotFoundException;
import me.fairygel.fbook.exception.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleNotValidException(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        BindingResult bindingResult = e.getBindingResult();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String errorMessages = fieldErrors.stream()
                .map(fieldError -> "with field '" + fieldError.getField() + "' - " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                status,
                "not valid data",
                errorMessages
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(value = IllegalAccessException.class)
    public ResponseEntity<CustomErrorResponse> handleIllegalAccessException(IllegalAccessException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                status,
                "not valid data",
                e.getMessage()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(value = DataFormatException.class)
    public ResponseEntity<CustomErrorResponse> handleIllegalStateException(DataFormatException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                status,
                "invalid date format",
                e.getMessage()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleEntityNotFound(EntityNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                status,
                "no such entity",
                e.getMessage()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                status,
                "invalid request body",
                e.getMessage()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }
}
