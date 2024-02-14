package app.controller.user.v2.request;


import app.constants.RegExp;
import app.constraint.Age;
import app.constraint.NotUnknown;
import app.constraint.Password;
import app.constraint.UserName;
import app.enums.UserType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

@Value
public class InsertUserRequest {

  @NotNull
  @UserName
  String userName;

  @NotNull
  @Password
  String password;

  @NotNull
  @NotUnknown
  UserType userType;

  @NotNull
  @Pattern(regexp = RegExp.ALL_HALF_WIDTH_ALPHABET)
  String firstName;

  @NotNull
  @Pattern(regexp = RegExp.ALL_HALF_WIDTH_ALPHABET)
  String lastName;

  @NotNull
  @Age
  Integer age;

}
