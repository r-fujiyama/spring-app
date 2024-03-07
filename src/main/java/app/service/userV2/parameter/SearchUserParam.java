package app.service.userV2.parameter;

import app.enums.UserStatus;
import app.enums.UserType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SearchUserParam {

  Long id;
  String userName;
  UserType userType;
  UserStatus userStatus;
  String firstName;
  String lastName;
  Integer age;

}
