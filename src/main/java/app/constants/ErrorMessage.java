package app.constants;

public class ErrorMessage {

  // 認証に失敗しました。
  public static final String AUTHENTICATION_FAILED = "app.error.message.authentication.failed";
  // サポートしていないContent-Typeが指定されています。
  public static final String UNSUPPORTED_MEDIA_TYPE = "app.error.message.unsupported.media.type";
  // JSONのパースに失敗しました。
  public static final String PARSE_JSON_FAILED = "app.error.message.parse.json.failed";
  // {0}に値が入力されていません。
  public static final String MISSING_REQUEST_PARAMETER = "app.error.message.missing.request.parameter";
  // {0}に指定された値の型に誤りがあります。
  public static final String TYPE_MISMATCH = "app.error.message.type.mismatch";

}
