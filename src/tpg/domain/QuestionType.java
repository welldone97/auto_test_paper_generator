package tpg.domain;

public class QuestionType {
	
	public static final String SINGLE_CHOICE = "1";
	public static final String MULTI_CHOICE = "2";
	public static final String BLANK = "3";
	public static final String JUDGEMENT = "4";
	public static final String SHORT_ANSWER = "5";
	public static final String COMPREHENSIVE = "6";
	
	private int id;
	private String name;
	
	public QuestionType(){ }
	
	public QuestionType(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static String numToChineseString(int num){
		String result = "";
		switch(String.valueOf(num)){
			case SINGLE_CHOICE:result="单选题";break;
			case MULTI_CHOICE:result="多选题";break;
			case BLANK:result="填空题";break;
			case JUDGEMENT:result="判断题";break;
			case SHORT_ANSWER:result="简答题";break;
			case COMPREHENSIVE:result="综合题";break;
			default:break;
		}
		return result;
	}
	
}