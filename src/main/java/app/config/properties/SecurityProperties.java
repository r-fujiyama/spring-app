package app.config.properties;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

  private String username;
  private String password;
  private String token;
  private String[] allowedOrigins;
  private String[] allowedMethods;
  private String[] allowedHeaders;
  private List<String> maskRequestHeaderNames;

}
