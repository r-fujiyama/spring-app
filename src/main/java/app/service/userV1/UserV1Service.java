package app.service.userV1;

import app.controller.user.v1.request.InsertUserRequest;
import app.controller.user.v1.request.UpdateUserRequest;
import app.controller.user.v1.response.DeleteUserResponse;
import app.controller.user.v1.response.InsertUserResponse;
import app.controller.user.v1.response.UpdateUserResponse;
import app.service.userV1.parameter.SearchUserParam;
import app.service.userV1.result.User;
import java.util.List;

public interface UserV1Service {

  List<User> searchUser(SearchUserParam param);

  InsertUserResponse insertUser(InsertUserRequest request);

  UpdateUserResponse updateUser(long id, UpdateUserRequest request);

  DeleteUserResponse deleteUser(long id);

}
