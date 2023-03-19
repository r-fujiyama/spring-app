package app.controller.user.v2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.controller.ControllerTest;
import app.controller.response.Error;
import app.controller.response.Response;
import app.controller.user.UserControllerV2;
import app.controller.user.response.GetUserResponse;
import app.controller.user.response.User;
import app.enums.ErrorCode;
import app.enums.UserStatus;
import app.enums.UserType;
import app.util.JSONUtils;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(controllers = UserControllerV2.class)
public class GetUserTest extends ControllerTest {

  @Test
  public void OK200() throws Exception {
    var json = mockMvc.perform(get("/v2/user/{userID}", 1)
            .param("userType", UserType.PRIVATE.getValue())
            .param("firstName", "taro")
            .param("lastName", "nihon")
            .param("age", "20"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var actual = JSONUtils.convertToObject(json, GetUserResponse.class);
    var expected = new GetUserResponse(
        User.builder()
            .id(1L)
            .type(UserType.PRIVATE)
            .status(UserStatus.REGISTERED)
            .firstName("taro")
            .lastName("nihon")
            .age(20)
            .build()
    );
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @MethodSource("validationErrorProvider")
  public void validationErrorTest(String userID, String userType, String firstName, String lastName,
      String age, Error error)
      throws Exception {
    var json = mockMvc.perform(get("/v2/user/{userID}", userID)
            .param("userType", userType)
            .param("firstName", firstName)
            .param("lastName", lastName)
            .param("age", age))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var actual = JSONUtils.convertToObject(json, Response.class);
    var expected = new Response(error);
    assertThat(actual).isEqualTo(expected);
  }

  static Stream<Arguments> validationErrorProvider() {
    return Stream.of(
        // ユーザーID
        arguments("a", UserType.PRIVATE.getValue(), "taro", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "ユーザーIDに指定された値の型に誤りがあります。")),
        arguments("0", UserType.PRIVATE.getValue(), "taro", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "ユーザーIDは1~9223372036854775807以内の値を入力してください。")),
        arguments("9223372036854775808", UserType.PRIVATE.getValue(), "taro", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "ユーザーIDに指定された値の型に誤りがあります。")),
        // ユーザタイプ
        arguments("1", null, "taro", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "ユーザータイプに値が入力されていません。")),
        arguments("1", "", "taro", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "ユーザータイプに値が入力されていません。")),
        arguments("1", "　", "taro", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "ユーザータイプに指定された値は許可されていません。")),
        arguments("1", UserType.UNKNOWN.getValue(), "taro", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "ユーザータイプに指定された値は許可されていません。")),
        arguments("1", "aaa", "taro", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "ユーザータイプに指定された値は許可されていません。")),
        // 名前
        arguments("1", UserType.PRIVATE.getValue(), null, "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "名前にNULL、空文字、空白は許可されていません。")),
        arguments("1", UserType.PRIVATE.getValue(), "", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "名前にNULL、空文字、空白は許可されていません。")),
        arguments("1", UserType.PRIVATE.getValue(), " ", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "名前にNULL、空文字、空白は許可されていません。")),
        arguments("1", UserType.PRIVATE.getValue(), "aaa!aaa", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "名前は^[a-zA-Z]+$の形式で入力してください。")),
        // 苗字
        arguments("1", UserType.PRIVATE.getValue(), "taro", null, "20",
            new Error(ErrorCode.BAD_REQUEST, "苗字にNULL、空文字、空白は許可されていません。")),
        arguments("1", UserType.PRIVATE.getValue(), "taro", "", "20",
            new Error(ErrorCode.BAD_REQUEST, "苗字にNULL、空文字、空白は許可されていません。")),
        arguments("1", UserType.PRIVATE.getValue(), "taro", " ", "20",
            new Error(ErrorCode.BAD_REQUEST, "苗字にNULL、空文字、空白は許可されていません。")),
        arguments("1", UserType.PRIVATE.getValue(), "taro", "aaa!aaa", "20",
            new Error(ErrorCode.BAD_REQUEST, "苗字は^[a-zA-Z]+$の形式で入力してください。")),
        // 年齢
        arguments("1", UserType.PRIVATE.getValue(), "taro", "nihon", null,
            new Error(ErrorCode.BAD_REQUEST, "年齢に値が入力されていません。")),
        arguments("1", UserType.PRIVATE.getValue(), "taro", "nihon", "",
            new Error(ErrorCode.BAD_REQUEST, "年齢に値が入力されていません。")),
        arguments("1", UserType.PRIVATE.getValue(), "taro", "nihon", "aaa",
            new Error(ErrorCode.BAD_REQUEST, "年齢に指定された値の型に誤りがあります。")),
        arguments("1", UserType.PRIVATE.getValue(), "taro", "nihon", "-1",
            new Error(ErrorCode.BAD_REQUEST, "年齢は0~999以内の値を入力してください。")),
        arguments("1", UserType.PRIVATE.getValue(), "taro", "nihon", "1000",
            new Error(ErrorCode.BAD_REQUEST, "年齢は0~999以内の値を入力してください。"))
    );
  }

}
