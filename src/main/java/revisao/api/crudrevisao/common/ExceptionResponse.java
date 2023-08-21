package revisao.api.crudrevisao.common;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, LocalDateTime timestamp, int status, String path) {
}
