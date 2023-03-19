package app.controller;

import app.config.properties.SecurityProperties;
import app.security.UsernamePasswordAuthenticationProvider;
import app.util.MessageUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(username = "username")
@Import({SecurityProperties.class, UsernamePasswordAuthenticationProvider.class, MessageUtils.class})
public abstract class ControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;
  protected MockMvc mockMvc;

  @BeforeAll
  public void beforeAll() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

}
