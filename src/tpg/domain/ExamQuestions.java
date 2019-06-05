package tpg.domain;

import java.util.ArrayList;
import java.util.List;

public class ExamQuestions {
	private List<QuestionDetails> questions;
	
	public ExamQuestions(List<QuestionDetails> questions) {
		this.setQuestions(questions);
	}

	public List<QuestionDetails> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDetails> questions) {
		this.questions = questions;
	}
	
	
	
}
