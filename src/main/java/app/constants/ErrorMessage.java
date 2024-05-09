package app.constants;

public class ErrorMessage {

  // 認証に失敗しました。
  public static final String AUTHENTICATION_FAILED = "app.error.message.authentication.failed";
  // User-Typeヘッダーが指定されていません。
  public static final String ACCESS_DENIED = "app.error.message.access.denied";
  // サポートしていないContent-Typeが指定されています。
  public static final String UNSUPPORTED_MEDIA_TYPE = "app.error.message.unsupported.media.type";
  // JSONのパースに失敗しました。
  public static final String PARSE_JSON_FAILED = "app.error.message.parse.json.failed";
  // {0}に値が入力されていません。
  public static final String MISSING_REQUEST_PARAMETER = "app.error.message.missing.request.parameter";
  // {0}に指定された値の型に誤りがあります。
  public static final String TYPE_MISMATCH = "app.error.message.type.mismatch";
  // リクエストメソッド'{0}'はサポートされていません。
  public static final String METHOD_NOT_SUPPORTED = "app.error.message.method.not.supported";
  // ユーザー:'{0}'は既に存在します。追加、更新することは出来ません。
  public static final String USER_ALREADY_EXISTS = "app.error.message.user.already.exists";
  // ユーザー:'{0}'が見つかりません。
  public static final String USER_NOT_FOUND = "app.error.message.user.not.found";

}
