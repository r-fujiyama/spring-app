package app.service.userV2.parameter;

import app.enums.UserType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateUserParam {

  long id;
  UserType userType;
  String firstName;
  String lastName;
  Integer age;

}
