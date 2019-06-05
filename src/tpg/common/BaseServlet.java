package tpg.common;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BaseServlet
 */
@WebServlet(name = "DispatcherServlet", urlPatterns = { "/DispatcherServlet" })
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		String methodName = request.getParameter("method");
		
		if(methodName == null || methodName.isEmpty()) {
			methodName = "execute";
		}
		Class<? extends BaseServlet> c = this.getClass();
		try {
			Method m = c.getMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			System.out.println("开始执行" + m.getName());
			String result = (String) m.invoke(this, request, response);
			System.out.println("完成执行"+ m.getName());
			if(result != null && !result.isEmpty()) {
				request.getRequestDispatcher(result).forward(request, response);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}



}
