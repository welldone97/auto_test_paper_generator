package tpg.service;

import java.util.List;
import java.util.Map;

import tpg.domain.KnowledgePoint;
import tpg.domain.Question;
import tpg.domain.QuestionType;
import tpg.domain.Subject;

public interface AdminManageService {

	List<QuestionType> getAllQuestionTypes();

	List<Subject> getAllSubjects();

	List<KnowledgePoint> getOneSubjectKnowledgePoints(String subjectId);

	boolean addSingleChoice(Map<String, String> paramMap);

	boolean addMultiChoice(Map<String, String> paramMap);

	boolean addBlank(Map<String, String> paramMap);

	boolean addJudgement(Map<String, String> paramMap);

	boolean addShortAnswer(Map<String, String> paramMap);

	boolean addComprehensive(Map<String, String> paramMap);

	List<? extends Question> getAppointedTitle(Map<String, String> paramsMap);

	boolean deleteTitleById(Map<String, String> paramsMap);

	boolean updateSingleChoice(Map<String, String> paramMap);

	boolean updateMultiChoice(Map<String, String> paramMap);

	boolean updateBlank(Map<String, String> paramMap);

	boolean updateJudgement(Map<String, String> paramMap);

	boolean updateShortAnswer(Map<String, String> paramMap);

	boolean updateComprehensive(Map<String, String> paramMap);

	boolean deleteKnowledgePoint(String id);

	Question getTitleById(Map<String, String> paramsMap);

	boolean updateKnowledgePointById(Map<String, String> paramMap);

	boolean addKnowledgePoint(Map<String, String> paramMap);

	boolean addSubject(String subjectName);

	boolean deleteSubjectById(String id);

}
