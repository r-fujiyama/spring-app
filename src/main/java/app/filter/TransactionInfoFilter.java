package app.filter;

import app.TransactionInfo;
import app.constants.DIOrder;
import app.constants.ErrorMessage;
import app.controller.response.Error;
import app.controller.response.Response;
import app.dao.UserDao;
import app.enums.ErrorCode;
import app.util.JSONUtils;
import app.util.MessageUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Order(DIOrder.TRANSACTION_INFO_FILTER)
@AllArgsConstructor
@Component
public class TransactionInfoFilter extends OncePerRequestFilter {

  private final UserDao UserDao;
  private final MessageUtils messageUtils;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
      var name = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      var user = UserDao.findByName(name);
      if (user != null) {
        TransactionInfo.init(user.getName(), user.getType());
      } else {
        setResponse(response, name);
        return;
      }
    }
    filterChain.doFilter(request, response);
  }

  public void setResponse(HttpServletResponse response, String name) throws IOException {
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    var res = new Response(new Error(ErrorCode.NOT_FOUND, messageUtils.getMessage(ErrorMessage.USER_NOT_FOUND, name)));
    JSONUtils.writeValue(response.getWriter(), res);
  }

}
