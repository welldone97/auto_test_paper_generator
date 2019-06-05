package output;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import cn.hengxin.paper.data.BlankData;
import cn.hengxin.paper.data.HeaderData;
import cn.hengxin.paper.data.JudgementData;
import cn.hengxin.paper.data.MultiChoiceData;
import cn.hengxin.paper.data.SingleChoiceData;
import cn.hengxin.paper.generator.PaperGenerator;
import cn.hengxin.paper.model.BaseModel;
import cn.hengxin.paper.model.Blank;
import cn.hengxin.paper.model.Header;
import cn.hengxin.paper.model.Judgement;
import cn.hengxin.paper.model.MultiChoice;
import cn.hengxin.paper.model.SingleChoice;
import tpg.domain.Exam;
import tpg.domain.QuestionDetails;

public class OutputUtil {

	public static String outputPaper(Exam exam, Map<String, String> examTermInfo) {
		PaperGenerator pg = new PaperGenerator();
		pg.setHeader(getHeader(examTermInfo, exam));
		List<BaseModel> questions = new ArrayList<BaseModel>();
		if(Integer.parseInt(exam.getSinglenum())>0){
			questions.add(getSingleChoice(exam));
		}
		if(Integer.parseInt(exam.getMultinum())>0){
			questions.add(getMultiChoice(exam));
		}
		if(Integer.parseInt(exam.getBlanknum())>0){
			questions.add(getBlank(exam));
		}
		if(Integer.parseInt(exam.getJudgementnum())>0){
			questions.add(getJudgement(exam));
		}
		pg.setQuestions(questions);
		try {
			pg.init();
			pg.output();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return getOutputPath();
		
	}

	private static BaseModel getJudgement(Exam exam) {
		List<JudgementData> judgements = new ArrayList<JudgementData>();
		List<QuestionDetails> qds = exam.getQuestions().getQuestions();
		for(QuestionDetails qd : qds){
			if(qd.getType().equals("judgement")){
				JudgementData jd = new JudgementData();
				jd.setTitle(qd.getTitle());
				judgements.add(jd);
			}
		}
		Judgement judgement = new Judgement();
		judgement.setJudgements(judgements);
		return judgement;
	}

	private static BaseModel getBlank(Exam exam) {
		List<BlankData> blanks = new ArrayList<BlankData>();
		List<QuestionDetails> qds = exam.getQuestions().getQuestions();
		for(QuestionDetails qd : qds){
			if(qd.getType().equals("blank")){
				BlankData bd = new BlankData();
				bd.setTitle(qd.getTitle());
				blanks.add(bd);
			}
		}
		Blank blank = new Blank();
		blank.setBlanks(blanks);
		return blank;
	}

	private static BaseModel getMultiChoice(Exam exam) {
		List<MultiChoiceData> multiChoices = new ArrayList<MultiChoiceData>();
		List<QuestionDetails> qds = exam.getQuestions().getQuestions();
		for(QuestionDetails qd : qds){
			if(qd.getType().equals("multi")){
				MultiChoiceData mcd = new MultiChoiceData();
				mcd.setTitle(qd.getTitle());
				mcd.setaOption(qd.getOptionA());
				mcd.setbOption(qd.getOptionB());
				mcd.setcOption(qd.getOptionC());
				mcd.setdOption(qd.getOptionD());
				multiChoices.add(mcd);
			}
		}
		MultiChoice multiChoice = new MultiChoice();
		multiChoice.setMultiChoices(multiChoices);
		return multiChoice;
	}

	private static BaseModel getSingleChoice(Exam exam) {
		List<SingleChoiceData> singleChoices = new ArrayList<SingleChoiceData>();
		List<QuestionDetails> qds = exam.getQuestions().getQuestions();
		for(QuestionDetails qd : qds){
			if(qd.getType().equals("single")){
				SingleChoiceData scd = new SingleChoiceData();
				scd.setTitle(qd.getTitle());
				scd.setaOption(qd.getOptionA());
				scd.setbOption(qd.getOptionB());
				scd.setcOption(qd.getOptionC());
				scd.setdOption(qd.getOptionD());
				singleChoices.add(scd);
			}
		}
		SingleChoice singleChoice = new SingleChoice();
		singleChoice.setSingleChoices(singleChoices);
		return singleChoice;
	}

	private static Header getHeader(Map<String, String> examTermInfo, Exam exam) {
		int startTime = Integer.parseInt(examTermInfo.get("startYear"));
		int endTime = Integer.parseInt(examTermInfo.get("endYear"));
		int examTerm = Integer.parseInt(examTermInfo.get("term"));
		String examSubject = exam.getCourseName();
		String examType = exam.getExamMethod();
		if(examType!=null){
			switch(examType){
				case "close":examType = "闭卷";break;
				case "open": examType = "开卷";break;
				default:break;
			}
		}
		int examTime = Integer.parseInt(exam.getDuration());
		
		HeaderData hd = new HeaderData();
		hd.setStartTime(startTime);
		hd.setEndTime(endTime);
		hd.setExamTerm(examTerm);
		hd.setExamSubject(examSubject);
		hd.setExamType(examType);
		hd.setExamTime(examTime);
		Header header = new Header();
		header.setHeaderData(hd);
		return header;
	}
	
	private static String getOutputPath(){
		Properties properties = new Properties();
		InputStream in = OutputUtil.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String outputPath = properties.getProperty("OUTPUT_PATH");
		outputPath += "output.docx";
		return outputPath;
	}
	
}
