package app.controller.user.v1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.controller.ControllerTest;
import app.controller.response.Error;
import app.controller.response.Response;
import app.enums.ErrorCode;
import app.service.userV1.UserV1Service;
import app.util.JSONUtils;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(UserV1Controller.class)
public class DeleteUserTest extends ControllerTest {

  @MockBean
  UserV1Service userService;

  @Test
  public void OK200() throws Exception {
    mockMvc.perform(delete("/v1/user/{id}", 1))
        .andExpect(status().isOk());
  }

  @ParameterizedTest
  @MethodSource("validationErrorProvider")
  public void validationErrorTest(String id, Error error) throws Exception {
    var res = mockMvc.perform(delete("/v1/user/{id}", id))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var actual = JSONUtils.toObject(res, Response.class);
    var expected = new Response(error);
    assertThat(actual.getStatus()).isEqualTo(expected.getStatus());
    assertThat(actual.getErrors()).containsExactlyInAnyOrder(expected.getErrors().toArray(new Error[0]));
  }

  static Stream<Arguments> validationErrorProvider() {
    return Stream.of(
        // ID
        arguments("a", new Error(ErrorCode.BAD_REQUEST, "IDに指定された値の型に誤りがあります。")),
        arguments("0",
            new Error(ErrorCode.BAD_REQUEST, "IDは1~9223372036854775807以内の値を入力してください。")),
        arguments("9223372036854775808",
            new Error(ErrorCode.BAD_REQUEST, "IDに指定された値の型に誤りがあります。"))
    );
  }

}
