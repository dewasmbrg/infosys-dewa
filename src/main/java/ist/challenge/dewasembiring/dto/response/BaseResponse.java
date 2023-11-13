package ist.challenge.dewasembiring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BaseResponse {
    private LocalDateTime timestamp;
    private int status;
    private boolean error;
    private String message;
    private String path;
}
