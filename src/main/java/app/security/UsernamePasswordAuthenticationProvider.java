package app.security;

import app.dao.UserDao;
import app.entity.join.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

  private final UserDao userDao;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    var userID = authentication.getName();
    var password = authentication.getCredentials().toString();

    var userInfo = userDao.findUserAndRoleByUserID(userID);
    if (!userExists(userInfo, password)) {
      return null;
    }

    return new UsernamePasswordAuthenticationToken(userID, password, userInfo.getRole().getGrantList());
  }

  private boolean userExists(UserInfo userInfo, String password) {
    if (userInfo == null) {
      return false;
    }
    return userInfo.getUser().getPassword().equals(password);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

}
