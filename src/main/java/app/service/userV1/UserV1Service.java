package app.service.userV1;

import app.controller.user.v1.request.InsertUserRequest;
import app.controller.user.v1.request.UpdateUserRequest;
import app.controller.user.v1.response.DeleteUserResponse;
import app.controller.user.v1.response.InsertUserResponse;
import app.controller.user.v1.response.SearchUserResponse;
import app.controller.user.v1.response.UpdateUserResponse;
import app.entity.User;
import app.service.userV1.dto.SearchUserParam;
import java.util.List;

public interface UserV1Service {

  SearchUserResponse searchUser(SearchUserParam param);

  InsertUserResponse insertUser(InsertUserRequest request);

  UpdateUserResponse updateUser(long id, UpdateUserRequest request);

  DeleteUserResponse deleteUser(long id);

}
