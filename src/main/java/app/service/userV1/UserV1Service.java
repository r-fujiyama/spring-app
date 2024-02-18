package app.service.userV1;

import app.service.userV1.parameter.InsertUserParam;
import app.service.userV1.parameter.SearchUserParam;
import app.service.userV1.parameter.UpdateUserParam;
import app.service.userV1.result.User;
import java.util.List;

public interface UserV1Service {

  List<User> searchUser(SearchUserParam param);

  User insertUser(InsertUserParam param);

  User updateUser(UpdateUserParam param);

  User deleteUser(long id);

}
