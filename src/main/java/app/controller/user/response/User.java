package app.controller.user.response;

import app.enums.UserStatus;
import app.enums.UserType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {

  Long id;
  UserType type;
  UserStatus status;
  String firstName;
  String lastName;
  Integer age;

}
