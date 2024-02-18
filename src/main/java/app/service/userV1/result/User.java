package app.service.userV1.result;

import app.enums.UserStatus;
import app.enums.UserType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {

  long id;
  String name;
  UserType type;
  UserStatus status;
  String firstName;
  String lastName;
  int age;

}
