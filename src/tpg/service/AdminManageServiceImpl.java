package tpg.service;

import java.util.List;
import java.util.Map;

import tpg.dao.AdminManageDao;
import tpg.dao.AdminManageDaoImpl;
import tpg.domain.KnowledgePoint;
import tpg.domain.Question;
import tpg.domain.QuestionType;
import tpg.domain.Subject;

public class AdminManageServiceImpl implements AdminManageService{
	
	AdminManageDao manageLibraryDao = new AdminManageDaoImpl();
	
	@Override
	public List<QuestionType> getAllQuestionTypes() {
		return manageLibraryDao.findAllQuestionsType();
	}

	@Override
	public List<Subject> getAllSubjects() {
		return manageLibraryDao.findAllSubjects();
	}

	@Override
	public List<KnowledgePoint> getOneSubjectKnowledgePoints(String subjectId) {
		return manageLibraryDao.findKnowledgePointsBySubjectId(subjectId);
	}

	@Override
	public boolean addSingleChoice(Map<String, String> paramMap) {
		return manageLibraryDao.insertSingleChoice(paramMap);
	}

	@Override
	public boolean addMultiChoice(Map<String, String> paramMap) {
		return manageLibraryDao.insertMultiChoice(paramMap);		
	}

	@Override
	public boolean addBlank(Map<String, String> paramMap) {
		return manageLibraryDao.insertBlank(paramMap);
		
	}

	@Override
	public boolean addJudgement(Map<String, String> paramMap) {
		return manageLibraryDao.insertJudgement(paramMap);		
	}

	@Override
	public boolean addShortAnswer(Map<String, String> paramMap) {
		return manageLibraryDao.insertShortAnswer(paramMap);		
	}

	@Override
	public boolean addComprehensive(Map<String, String> paramMap) {
		return manageLibraryDao.insertComprehensive(paramMap);
	}

	@Override
	public List<? extends Question> getAppointedTitle(Map<String, String> paramsMap) {
		return manageLibraryDao.findAppointedTitles(paramsMap);
	}

	@Override
	public boolean deleteTitleById(Map<String, String> paramsMap) {
		return manageLibraryDao.deleteTitleById(paramsMap);
	}

	@Override
	public boolean updateSingleChoice(Map<String, String> paramMap) {
		return manageLibraryDao.updateSingleChoice(paramMap);
		
	}

	@Override
	public boolean updateMultiChoice(Map<String, String> paramMap) {
		return manageLibraryDao.updateMultiChoice(paramMap);
		
	}

	@Override
	public boolean updateBlank(Map<String, String> paramMap) {
		return manageLibraryDao.updateBlank(paramMap);
	}

	@Override
	public boolean updateJudgement(Map<String, String> paramMap) {
		return manageLibraryDao.updateJudegement(paramMap);
		
	}

	@Override
	public boolean updateShortAnswer(Map<String, String> paramMap) {
		return manageLibraryDao.updateShortAnswer(paramMap);
		
	}

	@Override
	public boolean updateComprehensive(Map<String, String> paramMap) {
		return manageLibraryDao.updateComprehensive(paramMap);
		
	}

	@Override
	public boolean deleteKnowledgePoint(String id) {
		boolean deleteKnowledgePoint = manageLibraryDao.deleteKnowledgePointById(id);
		boolean deleteTitle = manageLibraryDao.deleteTitleByKnowledgePointId(id);
		return deleteKnowledgePoint && deleteTitle; 
	}

	@Override
	public Question getTitleById(Map<String, String> paramsMap) {
		return manageLibraryDao.findQuestionByQuestionTypeAndId(paramsMap);
		
	}

	@Override
	public boolean updateKnowledgePointById(Map<String, String> paramMap) {
		return manageLibraryDao.updateKnowledgePointById(paramMap);
	}

	@Override
	public boolean addKnowledgePoint(Map<String, String> paramMap) {
		return manageLibraryDao.insertKnowledgePoint(paramMap);
	}

	@Override
	public boolean addSubject(String subjectName) {
		
		return manageLibraryDao.addSubject(subjectName);
	}

	@Override
	public boolean deleteSubjectById(String id) {
		return manageLibraryDao.deleteSubjectById(id);
	}

}
