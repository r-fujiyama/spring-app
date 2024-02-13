package app.controller.user.v2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.controller.ControllerTest;
import app.controller.response.Error;
import app.controller.response.Response;
import app.enums.ErrorCode;
import app.service.userV2.UserV2Service;
import app.util.JSONUtils;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(UserV2Controller.class)
public class DeleteUserTest extends ControllerTest {

  @MockBean
  UserV2Service userService;

  @Test
  public void OK200() throws Exception {
    var actual = mockMvc.perform(delete("/v2/user/{id}", 1))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var expected = JSONUtils.toJSON(new Response());
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @MethodSource("validationErrorProvider")
  public void validationErrorTest(String id, Error error) throws Exception {
    var res = mockMvc.perform(delete("/v2/user/{id}", id))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var actual = JSONUtils.toObject(res, Response.class);
    var expected = new Response(error);
    assertThat(actual.getStatus()).isEqualTo(expected.getStatus());
    assertThat(actual.getErrors()).containsExactlyInAnyOrder(expected.getErrors().toArray(new Error[0]));
  }

  static Stream<Arguments> validationErrorProvider() {
    return Stream.of(
        // ユーザーID
        arguments("a", new Error(ErrorCode.BAD_REQUEST, "IDに指定された値の型に誤りがあります。")),
        arguments("0",
            new Error(ErrorCode.BAD_REQUEST, "IDは1~9223372036854775807以内の値を入力してください。")),
        arguments("9223372036854775808",
            new Error(ErrorCode.BAD_REQUEST, "IDに指定された値の型に誤りがあります。"))
    );
  }

}
