package app.controller.response;

import app.enums.ErrorCode;
import lombok.Value;

@Value
public class Error {

  ErrorCode code;
  String message;

}
