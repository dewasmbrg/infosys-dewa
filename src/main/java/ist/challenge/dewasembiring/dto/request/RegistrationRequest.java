package ist.challenge.dewasembiring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest implements Serializable {
    private static final long serialVersionUID = 6873144010250451838L;

    private String username;
    private String password;
}