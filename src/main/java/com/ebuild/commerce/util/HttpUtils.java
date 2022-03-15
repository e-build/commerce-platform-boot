package com.ebuild.commerce.util;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class HttpUtils {

  public static void jsonFlush(HttpServletResponse response, int status, String body)
      throws IOException {
    response.setStatus(status);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter writer = response.getWriter();
    writer.print(body);
    writer.flush();
  }

}
