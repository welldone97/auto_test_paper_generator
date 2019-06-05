package tpg.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import ga.Individual;
import ga.Paper;
import tpg.domain.Exam;
import tpg.domain.QuestionDetails;
import tpg.domain.User;

public interface UserService {

	boolean login(User user);
	
	Individual makePaper(String subject, List<Integer> kpList, double diff, Map<Integer, Integer> typeCountMapping, Map<Integer, Integer> typeScoreMapping);


	void savePaper(Individual paper, List<QuestionDetails> questionDetails, Map<String, String> examInfo);

	List<Exam> getHistoryPapers(String gonghao);

	Exam getAppointedExamPaper(String examid);

	String output(Exam exam, Map<String, String> examTermInfo);

	boolean download(String path, OutputStream os) throws IOException;
}
