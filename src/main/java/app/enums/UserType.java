package app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserType implements CodeValue<String, String> {

  UNKNOWN("0", "Unknown"),
  PRIVATE("1", "Private"),
  FREELANCE("2", "Freelance"),
  CORPORATE("3", "Corporate");

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

  public static UserType fromCode(String code) {
    return CodeValue.fromCode(Arrays.asList(values()), code, UNKNOWN);
  }

  @JsonCreator
  public static UserType fromValue(String value) {
    return CodeValue.fromValue(Arrays.asList(values()), value, UNKNOWN);
  }

}
