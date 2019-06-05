package tpg.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.sf.json.JSONArray;
import tpg.common.BaseServlet;
import tpg.common.FileUtils;
import tpg.common.MD5Utils;
import tpg.domain.KnowledgePoint;
import tpg.domain.MultiChoice;
import tpg.domain.Question;
import tpg.domain.QuestionType;
import tpg.domain.SingleChoice;
import tpg.domain.Subject;
import tpg.domain.User;
import tpg.service.AdminManageService;
import tpg.service.AdminManageServiceImpl;

@WebServlet("/admin_manage")
public class AdminManageServlet extends BaseServlet {

	private static final long serialVersionUID = 6878863794153814929L;
	
	public void getAllQuestionTypes(HttpServletRequest request, HttpServletResponse response) throws IOException{
		AdminManageService manageService = new AdminManageServiceImpl();
		List<QuestionType> questionTypeList = manageService.getAllQuestionTypes();
		JSONArray jsonArray = JSONArray.fromObject(questionTypeList);
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonArray.toString());
	}
	
	public void getAddQuestionForm(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String result = "";
		String questionTypeId = request.getParameter("questionTypeId");
		if(questionTypeId!=null && !questionTypeId.equals("-1")){
			String baseFilePath = request.getServletContext().getRealPath("/WEB-INF/pages/admin/SubPageInAddTitleJSP");
			switch(questionTypeId){
				case QuestionType.SINGLE_CHOICE:
					result = FileUtils.readFile(baseFilePath + "/add_single_choice.html");
					break;
				case QuestionType.MULTI_CHOICE:
					result = FileUtils.readFile(baseFilePath + "/add_multi_choice.html");
					break;
				case QuestionType.BLANK:
					result = FileUtils.readFile(baseFilePath + "/add_blank.html");
					break;
				case QuestionType.JUDGEMENT:
					result = FileUtils.readFile(baseFilePath + "/add_judgement.html");
					break;
				case QuestionType.SHORT_ANSWER:
					result = FileUtils.readFile(baseFilePath + "/add_short_answer.html");
					break;
				case QuestionType.COMPREHENSIVE:
					result = FileUtils.readFile(baseFilePath + "/add_comprehensive.html");
					break;
				default:result="";break;
			}
		}
		System.out.println(result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result);
	}

	public void getAllSubjects(HttpServletRequest request, HttpServletResponse response) throws IOException{
		AdminManageService manageService = new AdminManageServiceImpl();
		List<Subject> subjectList = manageService.getAllSubjects();
		JSONArray jsonArray = JSONArray.fromObject(subjectList);
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonArray.toString());
	}
	
	public void getKnowledgePoints(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String subjectId = request.getParameter("subjectId");
		AdminManageService manageLibraryService = new AdminManageServiceImpl();
		List<KnowledgePoint> knowledgePointList = manageLibraryService.getOneSubjectKnowledgePoints(subjectId);
		JSONArray jsonArray = JSONArray.fromObject(knowledgePointList);
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonArray.toString());
	}
	
	public void addTitle(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> paramMap = new HashMap<String,String>();//用于存放表单提交的所有参数
        String questionTypeId = request.getParameter("questionTypeId");
        boolean isSuccess = false;
        switch(questionTypeId){
	        case QuestionType.SINGLE_CHOICE: isSuccess = addSingleChoice(request, response); break;
			case QuestionType.MULTI_CHOICE: isSuccess = addMultiChoice(request, response);break;
			case QuestionType.BLANK: isSuccess = addBlank(request, response);break;
			case QuestionType.JUDGEMENT: isSuccess = addJudgement(request, response);break;
			default:break;
        }
        
        if(isSuccess){
        	response.getWriter().write("添加题目成功！");
        }else{
        	response.getWriter().write("添加题目失败！");
        }
	}
	
	private boolean addJudgement(HttpServletRequest request, HttpServletResponse response) {
		AdminManageService manageLibraryService = new AdminManageServiceImpl();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("kcbianhao", request.getParameter("subjectId"));
		paramMap.put("Knowledgepoint", request.getParameter("knowledgePointId"));
		paramMap.put("jtitle", request.getParameter("title"));
		paramMap.put("janswer", request.getParameter("answer"));
		paramMap.put("difficulty", request.getParameter("difficulty"));
		paramMap.put("jmark", request.getParameter("score"));
		paramMap.put("gonghao", ((User)request.getSession().getAttribute("user")).getUsername());
		return manageLibraryService.addJudgement(paramMap);
	}

	private boolean addBlank(HttpServletRequest request, HttpServletResponse response) {
		AdminManageService manageLibraryService = new AdminManageServiceImpl();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("kcbianhao", request.getParameter("subjectId"));
		paramMap.put("Knowledgepoint", request.getParameter("knowledgePointId"));
		paramMap.put("bfronttitle", request.getParameter("title"));
		paramMap.put("bbacktitle", "");
		paramMap.put("banswer", request.getParameter("answer"));
		paramMap.put("difficulty", request.getParameter("difficulty"));
		paramMap.put("bmark", request.getParameter("score"));
		paramMap.put("gonghao", ((User)request.getSession().getAttribute("user")).getUsername());
		return manageLibraryService.addBlank(paramMap);
	}

	private boolean addMultiChoice(HttpServletRequest request, HttpServletResponse response) {
		AdminManageService manageLibraryService = new AdminManageServiceImpl();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("kcbianhao", request.getParameter("subjectId"));
		paramMap.put("Knowledgepoint", request.getParameter("knowledgePointId"));
		paramMap.put("msubject", request.getParameter("title"));
		paramMap.put("moptionA", request.getParameter("optionA"));
		paramMap.put("moptionB", request.getParameter("optionB"));
		paramMap.put("moptionC", request.getParameter("optionC"));
		paramMap.put("moptionD", request.getParameter("optionD"));
		paramMap.put("manswer", request.getParameter("answer"));
		paramMap.put("difficulty", request.getParameter("difficulty"));
		paramMap.put("mmark", request.getParameter("score"));
		paramMap.put("gonghao", ((User)request.getSession().getAttribute("user")).getUsername());
		return manageLibraryService.addMultiChoice(paramMap);
	}

	private boolean addSingleChoice(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("addSingleChoice");
		AdminManageService manageLibraryService = new AdminManageServiceImpl();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("kcbianhao", request.getParameter("subjectId"));
		paramMap.put("Knowledgepoint", request.getParameter("knowledgePointId"));
		paramMap.put("ssubject", request.getParameter("title"));
		paramMap.put("soptionA", request.getParameter("optionA"));
		paramMap.put("soptionB", request.getParameter("optionB"));
		paramMap.put("soptionC", request.getParameter("optionC"));
		paramMap.put("soptionD", request.getParameter("optionD"));
		paramMap.put("sanswer", request.getParameter("answer"));
		paramMap.put("difficulty", request.getParameter("difficulty"));
		paramMap.put("smark", request.getParameter("score"));
		paramMap.put("gonghao", ((User)request.getSession().getAttribute("user")).getUsername());
		return manageLibraryService.addSingleChoice(paramMap);
	}

	public void getMatchTitles(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String,String> paramsMap = new HashMap<String, String>();
		
		Map<String, String[]> requestParamsMap = request.getParameterMap();
		Set<String> keys = requestParamsMap.keySet();
		for(String key : keys){
			switch(key){
				case "subjectId":
					String subjectId = request.getParameter(key);
					if(subjectId!=null && !subjectId.equals("-1")){
						paramsMap.put(key, new String(subjectId.getBytes("ISO-8859-1"), "UTF-8"));
						System.out.println("AdminManageLibraryServlet.getTitleData()--->" 
								+ key + "=" + new String(subjectId.getBytes("ISO-8859-1"), "UTF-8"));
					}
					break;
				case "knowledgePointId":
					String knowledgePointId = request.getParameter(key);
					if(knowledgePointId!=null && !knowledgePointId.equals("-1")){
						paramsMap.put(key, new String(knowledgePointId.getBytes("ISO-8859-1"), "UTF-8"));
						System.out.println("AdminManageLibraryServlet.getTitleData()--->" 
								+ key + "=" + new String(knowledgePointId.getBytes("ISO-8859-1"), "UTF-8"));
					}
					break;
				case "questionTypeId":
					String questionTypeId = request.getParameter(key);
					if(questionTypeId!=null && !questionTypeId.equals("-1")){
						paramsMap.put(key, new String(questionTypeId.getBytes("ISO-8859-1"), "UTF-8"));
						System.out.println("AdminManageLibraryServlet.getTitleData()--->" 
								+ key + "=" + new String(questionTypeId.getBytes("ISO-8859-1"), "UTF-8"));
					}
					break;
				case "keyword":
					String keyword = request.getParameter(key); 
					if(keyword!=null && !keyword.equals("")){
						paramsMap.put(key, new String(keyword.getBytes("ISO-8859-1"), "UTF-8"));
						System.out.println("AdminManageLibraryServlet.getTitleData()--->" 
								+ key + "=" + new String(keyword.getBytes("ISO-8859-1"), "UTF-8"));
					}
					break;
				default:break;
			}
		}
		
		AdminManageService manageLibraryService = new AdminManageServiceImpl();
		List<? extends Question> questionList = (List<? extends Question>) manageLibraryService.getAppointedTitle(paramsMap);
		JSONArray jsonArray = JSONArray.fromObject(questionList);
		
		if(questionList!=null){
			for(int i=0; i<questionList.size(); i++){
				System.out.println(questionList.get(i).toString());
			}
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonArray.toString());
	}
	
	public void deleteTitleById(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("id", request.getParameter("id"));
		paramsMap.put("questionTypeId", request.getParameter("questionTypeId"));
		
		AdminManageService manageLibraryService = new AdminManageServiceImpl();
		if(manageLibraryService.deleteTitleById(paramsMap)){
			response.getWriter().write("true");
		}else{
			response.getWriter().write("false");
		};
	}
	
	public String getTitleByQuestionTypeAndId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("id", request.getParameter("id"));
		paramsMap.put("questionTypeId", request.getParameter("questionTypeId"));
		
		//调用Service层返回所需的题目数据
		AdminManageService manageLibraryService = new AdminManageServiceImpl();
		Question title = manageLibraryService.getTitleById(paramsMap);
		System.out.println(title);
		response.setCharacterEncoding("UTF-8");
		if(title!=null){
			request.setAttribute("titleDetail", title);
			return "/WEB-INF/pages/admin/update_title.jsp";
		}
		return "/WEB-INF/pages/common/error.jsp";
	}
	
	public void getPictures(HttpServletRequest request, HttpServletResponse response){
		String path = request.getParameter("path");
		response.setContentType("image/jpeg");
		File file = new File(path);
		System.out.println(path);
		if(file.exists()){
			InputStream is;
			try {
				is = new FileInputStream(file);
				OutputStream os = response.getOutputStream();
				byte[] b = new byte[1024];
				while(is.read(b)!=-1){
					os.write(b);
				}
				is.close();
				os.flush();
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateTitle(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String questionTypeId = request.getParameter("questionTypeId");
		
		boolean isSuccess = false;
        if(questionTypeId!=null){//如果包含题型Id,就调用对应题型的Service层
        	switch(questionTypeId){
				case QuestionType.SINGLE_CHOICE : isSuccess = updateSingleChoice(request, response);break;
				case QuestionType.MULTI_CHOICE : isSuccess = updateMultiChoice(request, response);break;
				case QuestionType.BLANK : isSuccess = updateBlank(request, response);break;
				case QuestionType.JUDGEMENT : isSuccess = updateJudgement(request, response);break;
				default:break;
        	}
		}
        if(isSuccess){
        	response.getWriter().write("修改题目成功！");
        }else{
        	response.getWriter().write("修改题目失败！");
        }
		
	}

	private boolean updateBlank(HttpServletRequest request, HttpServletResponse response) {
		AdminManageService manageLibraryService = new AdminManageServiceImpl();
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("blankid", request.getParameter("id"));
		paramMap.put("kcbianhao", request.getParameter("subjectId"));
		paramMap.put("Knowledgepoint", request.getParameter("knowledgePointId"));
		paramMap.put("bfronttitle", request.getParameter("title"));
		paramMap.put("banswer", request.getParameter("answer"));
		paramMap.put("bmark", request.getParameter("score"));
		paramMap.put("difficulty", request.getParameter("difficulty"));
		
		return manageLibraryService.updateBlank(paramMap);
	}

	private boolean updateJudgement(HttpServletRequest request, HttpServletResponse response) {
		AdminManageService manageLibraryService = new AdminManageServiceImpl();
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("judgementid", request.getParameter("id"));
		paramMap.put("kcbianhao", request.getParameter("subjectId"));
		paramMap.put("Knowledgepoint", request.getParameter("knowledgePointId"));
		paramMap.put("jtitle", request.getParameter("title"));
		paramMap.put("janswer", request.getParameter("answer"));
		paramMap.put("jmark", request.getParameter("score"));
		paramMap.put("difficulty", request.getParameter("difficulty"));
		
		return manageLibraryService.updateJudgement(paramMap);
	}

	private boolean updateMultiChoice(HttpServletRequest request, HttpServletResponse response) {
		AdminManageService manageLibraryService = new AdminManageServiceImpl();
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("multiid", request.getParameter("id"));
		paramMap.put("kcbianhao", request.getParameter("subjectId"));
		paramMap.put("Knowledgepoint", request.getParameter("knowledgePointId"));
		paramMap.put("msubject", request.getParameter("title"));
		paramMap.put("moptionA", request.getParameter("optionA"));
		paramMap.put("moptionB", request.getParameter("optionB"));
		paramMap.put("moptionC", request.getParameter("optionC"));
		paramMap.put("moptionD", request.getParameter("optionD"));
		paramMap.put("manswer", request.getParameter("answer"));
		paramMap.put("mmark", request.getParameter("score"));
		paramMap.put("difficulty", request.getParameter("difficulty"));
		
		return manageLibraryService.updateMultiChoice(paramMap);
	}

	private boolean updateSingleChoice(HttpServletRequest request, HttpServletResponse response) {
		AdminManageService manageLibraryService = new AdminManageServiceImpl();
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("singleid", request.getParameter("id"));
		paramMap.put("kcbianhao", request.getParameter("subjectId"));
		paramMap.put("Knowledgepoint", request.getParameter("knowledgePointId"));
		paramMap.put("ssubject", request.getParameter("title"));
		paramMap.put("soptionA", request.getParameter("optionA"));
		paramMap.put("soptionB", request.getParameter("optionB"));
		paramMap.put("soptionC", request.getParameter("optionC"));
		paramMap.put("soptionD", request.getParameter("optionD"));
		paramMap.put("sanswer", request.getParameter("answer"));
		paramMap.put("smark", request.getParameter("score"));
		paramMap.put("difficulty", request.getParameter("difficulty"));
		
		return manageLibraryService.updateSingleChoice(paramMap);
	}

	public void updateKnowledgePointById(HttpServletRequest request, HttpServletResponse response) throws IOException{
		AdminManageService manageLibraryService = new AdminManageServiceImpl();
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("id", request.getParameter("id"));
		paramMap.put("name", new String(request.getParameter("knowledgePointName").getBytes("ISO-8859-1"), "UTF-8"));
		boolean isSuccess = manageLibraryService.updateKnowledgePointById(paramMap);
		if(isSuccess){
			response.getWriter().write("true");
		}
	}
	
	public void addKnowledgePoint(HttpServletRequest request, HttpServletResponse response) throws IOException{
		AdminManageService manageLibraryService = new AdminManageServiceImpl();
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("subjectId", request.getParameter("subjectId"));
		paramMap.put("name", new String(request.getParameter("knowledgePointName").getBytes("ISO-8859-1"), "UTF-8"));
		boolean isSuccess = manageLibraryService.addKnowledgePoint(paramMap);
		if(isSuccess){
			response.getWriter().write("true");
		}
	}
	
	
	/**用于添加题目和修改题目时，从fileItems中提取参数到paramMap
	 * @param fileItems
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> getParam(List<FileItem> fileItems) throws Exception {
		Map<String,String> paramMap = new HashMap<String, String>();
		
		String uploadPath = getServletContext().getRealPath("/upload");//获取要上传的路径
		StringBuffer imageTitleBuffer = new StringBuffer();//用于暂存imageTitle域多文件的路径
		StringBuffer imageAnswerBuffer = new StringBuffer();//用于暂存imageAnswer域多文件的路径
		for(FileItem fileItem : fileItems){
			if(fileItem.isFormField()){
				String fieldName = fileItem.getFieldName();
				String fieldValue = fileItem.getString("UTF-8");
				System.out.println("普通表单域-->" + fieldName + ":" + fieldValue);
				paramMap.put(fieldName, fieldValue);
			}else{
				if(fileItem.getName()!=null && !fileItem.getName().equals("")){
					String fieldName = fileItem.getFieldName();
        			String filename = new File(MD5Utils.generateMD5(fileItem.get().toString())+ fileItem.getName()).getName();
        			String filePath = uploadPath + File.separator + filename;
        			File storeFile = new File(filePath);
            		System.out.println("文件域-->" + fieldName + ":" + filePath);
            		fileItem.write(storeFile);
            		switch(fieldName){
		        		case "imageTitle":
		        			imageTitleBuffer.append(filePath + ";");
		        			paramMap.put("pictures", imageTitleBuffer.toString());break;
		        		case "imageA":paramMap.put("imageA", filePath);break;
		        		case "imageB":paramMap.put("imageB", filePath);break;
		        		case "imageC":paramMap.put("imageC", filePath);break;
		        		case "imageD":paramMap.put("imageD", filePath);break;
		        		case "imageAnswer":
		        			imageAnswerBuffer.append(filePath + ";");
		        			paramMap.put("imageAnswer", imageAnswerBuffer.toString());break;
		        		default:break;
            		}
        		}
			}
		}
		return paramMap;
	}
	
	public void deleteKnowledgePointById(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id = request.getParameter("id");
		boolean isSuccess = false;
		AdminManageService manageService = new AdminManageServiceImpl();
		if(id!=null){
			isSuccess = manageService.deleteKnowledgePoint(id);
		}
		if(isSuccess){
			response.getWriter().write("success");
		}
	}
	
	public void addSubject(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String subjectName = new String(request.getParameter("subjectName").getBytes("ISO-8859-1"), "UTF-8");
		boolean isSuccess = false;
		AdminManageService manageService = new AdminManageServiceImpl();
		isSuccess = manageService.addSubject(subjectName);
		if(isSuccess){
			response.getWriter().write("true");
		}
	}
	
	public void deleteSubjectById(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id = request.getParameter("id");
		boolean isSuccess = false;
		AdminManageService manageService = new AdminManageServiceImpl();
		isSuccess = manageService.deleteSubjectById(id);
		if(isSuccess){
			response.getWriter().write("true");
		}
	}
	
}
