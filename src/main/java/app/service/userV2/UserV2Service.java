package app.service.userV2;

import app.service.userV2.parameter.InsertUserParam;
import app.service.userV2.parameter.UserSearchParam;
import app.service.userV2.parameter.UpdateUserParam;
import app.service.userV2.result.User;
import java.util.List;

public interface UserV2Service {

  List<User> userSearch(UserSearchParam param);

  void insertUser(InsertUserParam param);

  void updateUser(UpdateUserParam param);

  void deleteUser(long id);

}
