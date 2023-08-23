package app.controller.user.v1.response;

import app.controller.response.Response;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class DeleteUserResponse extends Response {

  long userID;

}
