package tpg.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tpg.common.BaseServlet;
import tpg.common.VerifyCodeUtils;
import tpg.domain.User;
import tpg.service.UserService;
import tpg.service.UserServiceImpl;


/**
 * Servlet implementation class CommonServlet
 */
@WebServlet("/common")
public class CommonServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//调用工具类生成验证码
		String verifyCode = VerifyCodeUtils.generatorVerifyCode(4);
		//将验证码转成小写存入session
		request.getSession().setAttribute("verifyCode", verifyCode.toLowerCase());
		//设置响应头
		response.setHeader("Content-Type", "image/jpeg");
		response.setDateHeader("Expries", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		//输出图片到响应的输出流
		VerifyCodeUtils.outputImage(100, 30, response.getOutputStream(), verifyCode);
	}
	
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password");
		String verify = request.getParameter("verify").trim();
		String role = request.getParameter("role");
		System.out.println("\nusername=" + username + 
							"\npassword=" + password + 
							"\nverify=" + verify + 
							"\nrole=" + role);		
		String trueVerify = (String) request.getSession().getAttribute("verifyCode");
		response.setCharacterEncoding("UTF-8");
		if(!trueVerify.equalsIgnoreCase(verify)){
			response.getWriter().write("0");
			return;
		}
		
		User user = new User(username, password, role);
		UserService userService = new UserServiceImpl();
		boolean loginResult = userService.login(user);
		if(loginResult){
			request.getSession().setAttribute("user", user);
			response.getWriter().write("1");
		}else{
			response.getWriter().write("-1");
		}
		return;
	}
	
	public String loginSuccess(HttpServletRequest request, HttpServletResponse response){
		User loginUser = (User)request.getSession().getAttribute("user");
		String result = null;
		if(loginUser.getType().equals("admin")){
			result = "/WEB-INF/pages/admin/console.jsp";
		}else if(loginUser.getType().equals("user")){
			result = "/WEB-INF/pages/client/make_paper.jsp";
		}
		return result;
	}
	
}
