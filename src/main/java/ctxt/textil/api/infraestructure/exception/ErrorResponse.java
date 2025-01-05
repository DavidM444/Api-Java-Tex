package ctxt.textil.api.infraestructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final String code;
    private final String message;
    private final String detail;
    private final LocalDateTime timestamp;

    public ErrorResponse(String code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
        this.timestamp = LocalDateTime.now();
    }
}
