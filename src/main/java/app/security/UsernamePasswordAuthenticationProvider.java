package app.security;

import app.constants.RoleName;
import app.dao.RoleDao;
import app.dao.UserDao;
import app.entity.Role;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

  private final UserDao userDao;
  private final RoleDao roleDao;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String userID = authentication.getName();
    String password = authentication.getCredentials().toString();

    if (!userExists(userID, password)) {
      return null;
    }

    var role = roleDao.findByUserID(userID);
    if (role == null) {
      return null;
    }

    return new UsernamePasswordAuthenticationToken(userID, password, toRoleList(role));
  }

  private boolean userExists(String userID, String password) {
    var user = userDao.findByUserID(userID);
    if (user == null) {
      return false;
    }
    return user.getPassword().equals(password);
  }

  private List<SimpleGrantedAuthority> toRoleList(Role role) {
    var roleList = new ArrayList<SimpleGrantedAuthority>();
    if (role.isAllowCreate()) {
      roleList.add(new SimpleGrantedAuthority(RoleName.CREATE));
    }
    if (role.isAllowRead()) {
      roleList.add(new SimpleGrantedAuthority(RoleName.READ));
    }
    if (role.isAllowUpdate()) {
      roleList.add(new SimpleGrantedAuthority(RoleName.UPDATE));
    }
    if (role.isAllowDelete()) {
      roleList.add(new SimpleGrantedAuthority(RoleName.DELETE));
    }
    return roleList;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

}
