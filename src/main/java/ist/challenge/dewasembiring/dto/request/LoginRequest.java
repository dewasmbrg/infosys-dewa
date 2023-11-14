package ist.challenge.dewasembiring.dto.request;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {
    private static final long serialVersionUID = 6873144010250451838L;

    @ApiModelProperty(example = "your_username")
    private String username;
    @ApiModelProperty(example = "your_password")
    private String password;
}
