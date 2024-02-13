package app.service.userV2;

import app.controller.response.Response;
import app.controller.user.v2.request.GetUserRequest;
import app.controller.user.v2.request.InsertUserRequest;
import app.controller.user.v2.request.UpdateUserRequest;
import app.controller.user.v2.response.GetUserResponse;

public interface UserV2Service {

  GetUserResponse getUser(GetUserRequest request);

  Response insertUser(InsertUserRequest request);

  Response updateUser(long id, UpdateUserRequest request);

  Response deleteUser(long id);

}
