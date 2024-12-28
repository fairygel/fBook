package me.fairygel.fbook.exception.handler;

import jakarta.persistence.EntityNotFoundException;
import me.fairygel.fbook.exception.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.zip.DataFormatException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleNotValidException(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                status,
                "invalid date format",
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
}
