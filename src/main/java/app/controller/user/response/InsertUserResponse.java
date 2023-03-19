package app.controller.user.response;

import app.controller.response.Response;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class InsertUserResponse extends Response {

  User user;

}
