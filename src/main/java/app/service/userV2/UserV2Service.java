package app.service.userV2;

import app.controller.user.v2.request.SearchUserRequest;
import app.controller.user.v2.request.InsertUserRequest;
import app.controller.user.v2.request.UpdateUserRequest;
import app.controller.user.v2.response.SearchUserResponse;

public interface UserV2Service {

  SearchUserResponse getUser(SearchUserRequest request);

  void insertUser(InsertUserRequest request);

  void updateUser(long id, UpdateUserRequest request);

  void deleteUser(long id);

}
