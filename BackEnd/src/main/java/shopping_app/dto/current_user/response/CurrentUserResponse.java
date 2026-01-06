package shopping_app.dto.current_user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserResponse {

    private Long id;
    private String username;
    private String fullName;
    private List<String> roles;
}
