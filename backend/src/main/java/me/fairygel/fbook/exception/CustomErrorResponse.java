package me.fairygel.fbook.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;


@Getter
@AllArgsConstructor
public class CustomErrorResponse {
    private final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    private final HttpStatus status;
    private final String problem;
    private final String description;

}
