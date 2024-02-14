package app.service.userV1.dto;

import app.enums.UserStatus;
import app.enums.UserType;
import lombok.Builder;

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
