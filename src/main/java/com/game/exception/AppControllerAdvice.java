package com.game.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** Global exeption handler and response mapper
 * @author indiv
 *
 */
@ControllerAdvice
public class AppControllerAdvice {

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Reason> handleException(Exception exception) {
		if (exception instanceof ApplicationException) {
			ApplicationException exp = (ApplicationException) exception;
			Reason reason = Reason.from(exp);
			HttpStatus mappedStatus = HttpStatus.valueOf(Integer.parseInt(exp.getApplicationCode().code()));
			return ResponseEntity.status(mappedStatus).body(reason);
		} else {
			ApplicationException exp = new ApplicationException("Technical exception occured");
			Reason reason = Reason.toReason(exp);
			HttpStatus mappedStatus = HttpStatus.valueOf(Integer.parseInt(exp.getApplicationCode().code()));

			return ResponseEntity.status(mappedStatus).body(reason);
		}

	}

	public static class Reason {
		private String code;
		private String message;
		private List<Reason> nested = new ArrayList<>();

		public Reason(String code, String message) {
			super();
			this.code = code;
			this.message = message;
			this.nested = new ArrayList<>();
		}

		public Reason(ApplicationException e) {
			this(e.getApplicationCode().code(), e.getApplicationCode().message());
		}

		public static Reason toReason(ApplicationException e) {
			return new Reason(e);
		}

		public static Reason from(final ApplicationException e) {
			final Reason reason = toReason(e);
			e.getNestedExceptions().stream().forEach(n -> reason.getNested().add(toReason(n)));
			return reason;
		}

		public String getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

		public List<Reason> getNested() {
			return nested;
		}
	}
}