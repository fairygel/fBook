package me.fairygel.fbook.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@AllArgsConstructor
public class ValidationErrorResponse {
	private String code;
	private String message;
	private Set<Detail> detail;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Detail {
		private String field;
		private String value;
	}
}
