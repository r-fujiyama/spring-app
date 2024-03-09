package app.service.userV2;

import app.service.userV2.parameter.InsertUserParam;
import app.service.userV2.parameter.SearchUsersParam;
import app.service.userV2.parameter.UpdateUserParam;
import app.service.userV2.result.UserInfo;
import java.util.List;

public interface UserV2Service {

  List<UserInfo> searchUsers(SearchUsersParam param);

  void insertUser(InsertUserParam param);

  void updateUser(UpdateUserParam param);

  void deleteUser(long id);

}
