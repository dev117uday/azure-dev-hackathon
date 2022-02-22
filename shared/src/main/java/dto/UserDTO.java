package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    Long userId;
    String userName;
    String userEmail;

    public static UserDTO userToUserDTO(User user) {
        return new UserDTO(user.getUserId(), user.getUserName(), user.getUserEmail());
    }

    public static User userDtoToUser(UserDTO userDTO) {
        return new User(userDTO.getUserName(), userDTO.getUserEmail());
    }
}
