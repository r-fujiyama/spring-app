package app.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import app.controller.response.Error;
import app.controller.response.Response;
import app.enums.ErrorCode;
import app.exception.InternalServerErrorException;
import java.io.StringWriter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class JSONUtilsTest {

  @Nested
  public class ToJSON {

    @Test
    public void toJSONTest() {
      var obj = new Response(new Error(ErrorCode.BAD_REQUEST, "test"));
      var actual = JSONUtils.toJSON(obj);
      var expected = "{\"status\":\"Failure\",\"errors\":[{\"code\":\"BadRequest\",\"message\":\"test\"}]}";
      assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void throwException() {
      assertThatThrownBy(() -> JSONUtils.toJSON(new EmptyClass()))
          .isInstanceOf(InternalServerErrorException.class)
          .hasMessage(
              "ErrorCode:InternalServerError, Message:No serializer found for class app.util.JSONUtilsTest$EmptyClass and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)");
    }

  }

  @Nested
  public class ToObject {

    @Test
    public void toObject() {
      var json = "{\"status\":\"Failure\",\"errors\":[{\"code\":\"BadRequest\",\"message\":\"test\"}]}";
      var actual = JSONUtils.toObject(json, Response.class);
      var expected = new Response(new Error(ErrorCode.BAD_REQUEST, "test"));
      assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void throwException() {
      assertThatThrownBy(() -> JSONUtils.toObject("{error}", Response.class))
          .isInstanceOf(InternalServerErrorException.class)
          .hasMessage(
              "ErrorCode:InternalServerError, Message:Unexpected character ('e' (code 101)): was expecting double-quote to start field name\n at [Source: (String)\"{error}\"; line: 1, column: 3]");
    }
  }

  @Nested
  public class WriteValue {

    @Test
    public void writeValue() {
      var writer = new StringWriter();
      var writeObject = new Response(new Error(ErrorCode.BAD_REQUEST, "test"));
      JSONUtils.writeValue(writer, writeObject);
      var actual = writer.toString();
      var expected = JSONUtils.toJSON(writeObject);
      assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void throwException() {
      var writer = new StringWriter();
      assertThatThrownBy(() -> JSONUtils.writeValue(writer, new EmptyClass()))
          .isInstanceOf(InternalServerErrorException.class)
          .hasMessage(
              "ErrorCode:InternalServerError, Message:No serializer found for class app.util.JSONUtilsTest$EmptyClass and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)");
    }
  }

  public static class EmptyClass {

  }

}
