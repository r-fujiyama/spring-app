package app.controller.user.v1;

import app.constants.RegExp;
import app.constraint.Age;
import app.constraint.NotUnknown;
import app.constraint.Pattern;
import app.constraint.UserID;
import app.controller.user.v1.request.InsertUserRequest;
import app.controller.user.v1.request.UpdateUserRequest;
import app.controller.user.v1.response.DeleteUserResponse;
import app.controller.user.v1.response.GetUserResponse;
import app.controller.user.v1.response.InsertUserResponse;
import app.controller.user.v1.response.UpdateUserResponse;
import app.enums.UserType;
import app.service.userV1.UserV1Service;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@RestController
@RequestMapping(path = "v1/user")
@Validated
public class UserV1Controller {

  private final UserV1Service userService;

  @GetMapping(path = "{userID}")
  public GetUserResponse getUser(
      @Valid @UserID @PathVariable("userID") long userID,
      @Valid @NotUnknown @RequestParam UserType userType,
      @Valid @NotBlank @Pattern(regexp = RegExp.ALL_HALF_WIDTH_ALPHABET) @RequestParam String firstName,
      @Valid @NotBlank @Pattern(regexp = RegExp.ALL_HALF_WIDTH_ALPHABET) @RequestParam String lastName,
      @Valid @Age @RequestParam Integer age) {
    return userService.getUser(userID, userType, firstName, lastName, age);
  }

  @PostMapping(path = "{userID}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public InsertUserResponse insertUser(@Valid @UserID @PathVariable("userID") long userID,
      @Valid @RequestBody InsertUserRequest request) {
    return userService.insertUser(userID, request);
  }

  @PutMapping(path = "{userID}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public UpdateUserResponse updateUser(@Valid @UserID @PathVariable("userID") long userID,
      @Valid @RequestBody UpdateUserRequest request) {
    return userService.updateUser(userID, request);
  }

  @DeleteMapping(path = "{userID}")
  public DeleteUserResponse deleteUser(@Valid @UserID @PathVariable("userID") long userID) {
    return userService.deleteUser(userID);
  }

}
