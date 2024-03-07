package app.controller.user.v1.response;

import app.controller.response.Response;
import app.controller.user.response.User;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class SearchUsersResponse extends Response {

  List<User> users;

}
