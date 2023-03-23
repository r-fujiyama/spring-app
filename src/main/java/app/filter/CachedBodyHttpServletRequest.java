package app.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.util.StreamUtils;

public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

  private final byte[] cachedBody;

  public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
    super(request);
    this.cachedBody = StreamUtils.copyToByteArray(request.getInputStream());
  }

  @Override
  public ServletInputStream getInputStream() {
    return new ByteArrayServletInputStream(this.cachedBody);
  }

  @Override
  public BufferedReader getReader() {
    var byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
    return new BufferedReader(new InputStreamReader(byteArrayInputStream));
  }

  private static class ByteArrayServletInputStream extends ServletInputStream {

    private final ByteArrayInputStream byteArrayInputStream;

    public ByteArrayServletInputStream(byte[] cachedBody) {
      this.byteArrayInputStream = new ByteArrayInputStream(cachedBody);
    }

    @Override
    public boolean isFinished() {
      return byteArrayInputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {
      throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public int read() {
      return byteArrayInputStream.read();
    }
  }

}
