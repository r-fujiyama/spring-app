package app.security.basic;

import app.dao.UserDao;
import app.entity.join.UserAndRole;
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
    var userName = authentication.getName();
    var password = authentication.getCredentials().toString();

    var userAndRole = userDao.findUserAndRoleByUserName(userName);
    if (!userExists(userAndRole, password)) {
      return null;
    }

    return new UsernamePasswordAuthenticationToken(userName, password, userAndRole.getRole().getGrantList());
  }

  private boolean userExists(UserAndRole userAndRole, String password) {
    if (userAndRole == null) {
      return false;
    }
    return userAndRole.getUser().getPassword().equals(password);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

}
