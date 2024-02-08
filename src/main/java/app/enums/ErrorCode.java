package app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode implements CodeValueEnum<Integer, String> {

  UNKNOWN(Integer.MIN_VALUE, "Unknown"),
  BAD_REQUEST(400, "BadRequest"),
  UNAUTHORIZED(401, "Unauthorized"),
  FORBIDDEN(403, "Forbidden"),
  INTERNAL_SERVER_ERROR(500, "InternalServerError");

  private final int code;
  private final String value;

  @Override
  public Integer getCode() {
    return this.code;
  }

  @JsonValue
  @Override
  public String getValue() {
    return this.value;
  }

  public static ErrorCode fromCode(Integer code) {
    return CodeValueEnum.fromCode(ErrorCode.class, code);
  }

  @JsonCreator
  public static ErrorCode fromValue(String value) {
    return CodeValueEnum.fromValue(ErrorCode.class, value);
  }

}
