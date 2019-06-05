package tpg.dao;

import java.util.List;
import java.util.Map;

import ga.Question;
import tpg.domain.Exam;
import tpg.domain.KnowledgePoint;
import tpg.domain.QuestionDetails;
import tpg.domain.Subject;
import tpg.domain.User;

public interface UserDao {

	User getUserByUsernameAndRole(String username, String type);

	int saveExam(Map<String, Object> paramMap);

	void saveExamQuestion(int examId, String questionid, String questiontype);

	List<Exam> getExamsByUser(String gonghao);

	List<Question> getQuestionsByExamId(String examId);

	Exam getExamById(String examid);

	List<KnowledgePoint> getAppointedKnowledgePoints(String[] kps);

	Subject getSubjectById(String kcbianhao);

}
