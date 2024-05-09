package app.service.userV1.parameter;

import app.enums.UserType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateUserParam {

  long id;
  String name;
  String password;
  UserType userType;
  String firstName;
  String lastName;
  Integer age;

}
