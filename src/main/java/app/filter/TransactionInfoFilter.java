package app.filter;

import app.TransactionInfo;
import app.constants.DIOrder;
import app.constants.ErrorMessage;
import app.controller.response.Error;
import app.controller.response.Response;
import app.enums.ErrorCode;
import app.enums.UserType;
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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Order(DIOrder.TRANSACTION_INFO_FILTER)
@AllArgsConstructor
@Component
public class TransactionInfoFilter extends OncePerRequestFilter {

  private final MessageUtils messageUtils;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    var userType = UserType.fromValue(request.getHeader("User-Type"));
    if (userType == null || userType == UserType.UNKNOWN) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      var res = new Response(
          new Error(ErrorCode.UNAUTHORIZED, messageUtils.getMessage(ErrorMessage.DOSE_NOT_EXIST_HEADER_USER_TYPE)));
      JSONUtils.writeValue(response.getWriter(), res);
      return;
    }
    TransactionInfo.init(userType);
    filterChain.doFilter(request, response);
  }

}
