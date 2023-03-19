package app.controller.user;

import app.constraints.UserID;
import app.controller.user.request.GetUserRequest;
import app.controller.user.response.GetUserResponse;
import app.controller.user.response.User;
import app.enums.UserStatus;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(path = "v2/user")
public class UserControllerV2 {

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
}
