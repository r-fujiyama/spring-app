package app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode implements CodeValue<String, String> {

  UNKNOWN("Unknown", "Unknown"),
  BAD_REQUEST("400", "BadRequest"),
  UNAUTHORIZED("401", "Unauthorized"),
  INTERNAL_SERVER_ERROR("500", "InternalServerError");

  private final String code;
  private final String value;

  @Override
  public String getCode() {
    return this.code;
  }

  @JsonValue
  @Override
  public String getValue() {
    return this.value;
  }

  public static ErrorCode fromCode(String code) {
    return CodeValue.fromCode(Arrays.asList(values()), code, UNKNOWN);
  }

  @JsonCreator
  public static ErrorCode fromValue(String value) {
    return CodeValue.fromValue(Arrays.asList(values()), value, UNKNOWN);
  }

}
