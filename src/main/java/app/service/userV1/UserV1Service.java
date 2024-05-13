package app.service.userV1;

import app.service.userV1.parameter.InsertUserParam;
import app.service.userV1.parameter.SearchUsersParam;
import app.service.userV1.parameter.UpdateUserParam;
import app.service.userV1.result.UserInfo;
import java.util.List;

public interface UserV1Service {

  List<UserInfo> searchUsers(SearchUsersParam param);

  UserInfo insertUser(InsertUserParam param);

  UserInfo updateUser(UpdateUserParam param);

  void deleteUser(long id);

}
