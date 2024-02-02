package app.entity.join;

import app.entity.Role;
import app.entity.User;
import lombok.Data;

@Data
public class UserInfo {

  private User user;
  private Role role;

}
