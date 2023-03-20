package app.config;

import app.config.properties.SecurityProperties;
import app.converter.StringToCodeValueEnumConverterFactory;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AllArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final SecurityProperties securityProperties;

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverterFactory(new StringToCodeValueEnumConverterFactory());
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins(securityProperties.getAllowedOrigins())
        .allowedMethods(securityProperties.getAllowedMethods())
        .allowedHeaders(securityProperties.getAllowedHeaders());
  }

}
