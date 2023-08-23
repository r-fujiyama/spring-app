package app.controller.user.v1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.controller.ControllerTest;
import app.controller.response.Error;
import app.controller.response.Response;
import app.controller.user.v1.response.DeleteUserResponse;
import app.enums.ErrorCode;
import app.util.JSONUtils;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(controllers = UserController.class)
public class DeleteUserTest extends ControllerTest {

  @Test
  public void OK200() throws Exception {
    var json = mockMvc.perform(delete("/v1/user/{userID}", 1))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var actual = JSONUtils.convertToObject(json, DeleteUserResponse.class);
    var expected = new DeleteUserResponse(1L);
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @MethodSource("validationErrorProvider")
  public void validationErrorTest(String userID, Error error) throws Exception {
    var json = mockMvc.perform(delete("/v1/user/{userID}", userID))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var actual = JSONUtils.convertToObject(json, Response.class);
    var expected = new Response(error);
    assertThat(actual).isEqualTo(expected);
  }

  static Stream<Arguments> validationErrorProvider() {
    return Stream.of(
        // ユーザーID
        arguments("a", new Error(ErrorCode.BAD_REQUEST, "ユーザーIDに指定された値の型に誤りがあります。")),
        arguments("0", new Error(ErrorCode.BAD_REQUEST, "ユーザーIDは1~9223372036854775807以内の値を入力してください。")),
        arguments("9223372036854775808", new Error(ErrorCode.BAD_REQUEST, "ユーザーIDに指定された値の型に誤りがあります。"))
    );
  }

}
