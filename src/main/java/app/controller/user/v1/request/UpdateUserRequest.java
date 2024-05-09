package app.controller.user.v1.request;


import app.annotation.constraint.Age;
import app.annotation.constraint.NotUnknown;
import app.annotation.constraint.Password;
import app.annotation.constraint.UserName;
import app.constants.RegExp;
import app.enums.UserType;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

@Value
public class UpdateUserRequest {

  @UserName
  String userName;

  @Password
  String password;

  @NotUnknown
  UserType userType;

  @Pattern(regexp = RegExp.HALF_WIDTH_ALPHABET)
  String firstName;

  @Pattern(regexp = RegExp.HALF_WIDTH_ALPHABET)
  String lastName;

  @Age
  Integer age;

}
