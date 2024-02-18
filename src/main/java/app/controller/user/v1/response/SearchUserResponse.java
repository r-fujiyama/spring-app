package app.controller.user.v1.response;

import app.controller.response.Response;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class SearchUserResponse extends Response {

  List<User> users;

}
