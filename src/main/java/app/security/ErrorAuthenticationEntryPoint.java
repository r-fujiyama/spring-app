package app.security;

import app.constants.ErrorMessage;
import app.controller.response.Error;
import app.controller.response.Response;
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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class ErrorAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final MessageUtils messageUtils;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
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
