package app.controller.user.v2;

import app.constraints.UserID;
import app.controller.response.Response;
import app.controller.user.v2.request.GetUserRequest;
import app.controller.user.v2.request.InsertUserRequest;
import app.controller.user.v2.request.UpdateUserRequest;
import app.controller.user.v2.response.GetUserResponse;
import app.service.userV2.UserV2Service;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "v2/user")
@Validated
public class UserV2Controller {

  private final UserV2Service userService;

  @GetMapping(path = "{userID}")
  public GetUserResponse getUserV2(@Valid @UserID @PathVariable("userID") long userID,
      @Valid GetUserRequest request) {
    return userService.getUser(request);
  }

  @PostMapping(path = "{userID}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Response insertUser(@Valid @UserID @PathVariable("userID") long userID,
      @Valid @RequestBody InsertUserRequest request) {
    return userService.insertUser(userID, request);
  }

  @PutMapping(path = "{userID}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Response updateUser(@Valid @UserID @PathVariable("userID") long userID,
      @Valid @RequestBody UpdateUserRequest request) {
    return userService.updateUser(userID, request);
  }

  @DeleteMapping(path = "{userID}")
  public Response deleteUser(@Valid @UserID @PathVariable("userID") long userID) {
    return userService.deleteUser(userID);
  }

}
