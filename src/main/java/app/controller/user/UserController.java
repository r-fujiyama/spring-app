package app.controller.user;

import app.constants.RegExp;
import app.constraints.Age;
import app.constraints.NotUnknown;
import app.constraints.Pattern;
import app.constraints.UserID;
import app.controller.user.request.InsertUserRequest;
import app.controller.user.request.UpdateUserRequest;
import app.controller.user.response.DeleteUserResponse;
import app.controller.user.response.GetUserResponse;
import app.controller.user.response.InsertUserResponse;
import app.controller.user.response.UpdateUserResponse;
import app.controller.user.response.User;
import app.enums.UserStatus;
import app.enums.UserType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(path = "v1/user")
public class UserController {

  @GetMapping(path = "{userID}")
  public GetUserResponse getUser(
      @Valid @UserID @PathVariable("userID") long userID,
      @Valid @NotUnknown @RequestParam UserType userType,
      @Valid @NotBlank @Pattern(regexp = RegExp.ALL_HALF_WIDTH_ALPHABET) @RequestParam String firstName,
      @Valid @NotBlank @Pattern(regexp = RegExp.ALL_HALF_WIDTH_ALPHABET) @RequestParam String lastName,
      @Valid @Age @RequestParam Integer age) {
    return new GetUserResponse(
        User.builder()
            .id(userID)
            .type(userType)
            .status(UserStatus.REGISTERED)
            .firstName(firstName)
            .lastName(lastName)
            .age(age)
            .build()
    );
  }

  @PostMapping(path = "{userID}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public InsertUserResponse insertUser(@Valid @UserID @PathVariable("userID") long userID,
      @Valid @RequestBody InsertUserRequest request) {
    return new InsertUserResponse(
        User.builder()
            .id(userID)
            .type(request.getUserType())
            .status(UserStatus.REGISTERED)
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .age(request.getAge())
            .build()
    );
  }

  @PutMapping(path = "{userID}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public UpdateUserResponse updateUser(@Valid @UserID @PathVariable("userID") long userID,
      @Valid @RequestBody UpdateUserRequest request) {
    return new UpdateUserResponse(
        User.builder()
            .id(userID)
            .type(request.getUserType())
            .status(UserStatus.REGISTERED)
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .age(request.getAge())
            .build()
    );
  }

  @DeleteMapping(path = "{userID}")
  public DeleteUserResponse deleteUser(@Valid @UserID @PathVariable("userID") long userID) {
    return new DeleteUserResponse(userID);
  }

}
