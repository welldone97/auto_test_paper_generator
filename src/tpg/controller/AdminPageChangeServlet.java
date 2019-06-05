package tpg.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tpg.common.BaseServlet;

@WebServlet("/admin_page_change")
public class AdminPageChangeServlet extends BaseServlet {
	
	public String home(HttpServletRequest request, HttpServletResponse response){
		return "/WEB-INF/pages/admin/home.jsp";
	}
	
	public String addTitle(HttpServletRequest request, HttpServletResponse response){
		return "/WEB-INF/pages/admin/add_title.jsp";
	}
	
	public String manageTitle(HttpServletRequest request, HttpServletResponse response){
		return "/WEB-INF/pages/admin/manage_title.jsp";
	}
	
	public String manageSubject(HttpServletRequest request, HttpServletResponse response){
		return "/WEB-INF/pages/admin/manage_subject.jsp";
	}
	
	public String manageKnowledgePoint(HttpServletRequest request, HttpServletResponse response){
		return "/WEB-INF/pages/admin/manage_knowledge_point.jsp";
	}
	
	
}











