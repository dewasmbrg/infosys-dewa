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
public class UserUpdateRequest implements Serializable {
    private static final long serialVersionUID = 6873144010250451838L;

    @ApiModelProperty(example = "current_username")
    private String username;
    @ApiModelProperty(example = "current_password")
    private String password;
}