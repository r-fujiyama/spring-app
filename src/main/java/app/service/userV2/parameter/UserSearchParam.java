package app.service.userV2.parameter;

import app.enums.UserStatus;
import app.enums.UserType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserSearchParam {

  Long id;
  String userName;
  UserType userType;
  UserStatus userStatus;
  String firstName;
  String lastName;
  Integer age;

}
