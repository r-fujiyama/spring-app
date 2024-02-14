package app.security.basic;

import app.constants.ErrorMessage;
import app.controller.response.Error;
import app.controller.response.Response;
import app.dao.UserDao;
import app.entity.join.UserInfo;
import app.enums.ErrorCode;
import app.util.JSONUtils;
import app.util.MessageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

  private final UserDao userDao;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    var userName = authentication.getName();
    var password = authentication.getCredentials().toString();

    var userInfo = userDao.findUserAndRoleByUserName(userName);
    if (!userExists(userInfo, password)) {
      return null;
    }

    return new UsernamePasswordAuthenticationToken(userName, password, userInfo.getRole().getGrantList());
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

  @Slf4j
  @AllArgsConstructor
  @Component
  public static class ErrorAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final MessageUtils messageUtils;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException)
        throws IOException {
      log.info(authException.getMessage(), authException);
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      var res = new Response(
          new Error(ErrorCode.UNAUTHORIZED, messageUtils.getMessage(ErrorMessage.AUTHENTICATION_FAILED)));
      JSONUtils.writeValue(response.getWriter(), res);
    }

  }
}
