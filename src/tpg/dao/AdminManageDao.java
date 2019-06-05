package tpg.dao;

import java.util.List;
import java.util.Map;

import tpg.domain.KnowledgePoint;
import tpg.domain.Question;
import tpg.domain.QuestionType;
import tpg.domain.Subject;

public interface AdminManageDao {

	List<QuestionType> findAllQuestionsType();

	List<Subject> findAllSubjects();

	List<KnowledgePoint> findKnowledgePointsBySubjectId(String subjectId);

	boolean insertSingleChoice(Map<String, String> paramMap);

	boolean insertMultiChoice(Map<String, String> paramMap);

	boolean insertComprehensive(Map<String, String> paramMap);

	boolean insertShortAnswer(Map<String, String> paramMap);

	boolean insertJudgement(Map<String, String> paramMap);

	boolean insertBlank(Map<String, String> paramMap);

	List<? extends Question> findAppointedTitles(Map<String, String> paramsMap);

	boolean deleteTitleById(Map<String, String> paramsMap);

	boolean updateSingleChoice(Map<String, String> paramMap);

	boolean updateMultiChoice(Map<String, String> paramMap);

	boolean updateBlank(Map<String, String> paramMap);

	boolean updateJudegement(Map<String, String> paramMap);

	boolean updateShortAnswer(Map<String, String> paramMap);

	boolean updateComprehensive(Map<String, String> paramMap);

	boolean deleteTitleByKnowledgePointId(String id);

	boolean deleteKnowledgePointById(String id);

	Question findQuestionByQuestionTypeAndId(Map<String, String> paramsMap);

	boolean updateKnowledgePointById(Map<String, String> paramMap);

	boolean insertKnowledgePoint(Map<String, String> paramMap);

	boolean addSubject(String subjectName);

	boolean deleteSubjectById(String id);

}
