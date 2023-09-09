package app.service.userV1;

import app.controller.user.v1.request.InsertUserRequest;
import app.controller.user.v1.request.UpdateUserRequest;
import app.controller.user.v1.response.DeleteUserResponse;
import app.controller.user.v1.response.GetUserResponse;
import app.controller.user.v1.response.InsertUserResponse;
import app.controller.user.v1.response.UpdateUserResponse;
import app.enums.UserType;

public interface UserV1Service {

  GetUserResponse getUser(long userID, UserType userType, String firstName, String lastName, Integer age);

  InsertUserResponse insertUser(long userID, InsertUserRequest request);

  UpdateUserResponse updateUser(long userID, UpdateUserRequest request);

  DeleteUserResponse deleteUser(long userID);

}
