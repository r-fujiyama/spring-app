package app.controller.user.v2;

import app.constraints.UserID;
import app.controller.response.Response;
import app.controller.user.response.User;
import app.controller.user.v2.request.GetUserRequest;
import app.controller.user.v2.request.InsertUserRequest;
import app.controller.user.v2.response.GetUserResponse;
import app.enums.UserStatus;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(path = "v2/user")
public class UserV2Controller {

  @GetMapping(path = "{userID}")
  public GetUserResponse getUserV2(@Valid @UserID @PathVariable("userID") long userID,
      @Valid GetUserRequest request) {
    return new GetUserResponse(
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

  @PostMapping(path = "{userID}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Response insertUser(@Valid @UserID @PathVariable("userID") long userID,
      @Valid @RequestBody InsertUserRequest request) {
    return new Response();
  }

}
