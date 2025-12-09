package me.fairygel.fbook.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class PartialErrorResponse {
    private String code;
    private String message;
}
