package tpg.domain;

public class SingleChoice extends Question {
	private String optionA;
	private String imageA;
	private String optionB;
	private String imageB;
	private String optionC;
	private String imageC;
	private String optionD;
	private String imageD;
	
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	
	public String getImageA() {
		return imageA;
	}
	public void setImageA(String imageA) {
		this.imageA = imageA;
	}
	public String getImageB() {
		return imageB;
	}
	public void setImageB(String imageB) {
		this.imageB = imageB;
	}
	public String getImageC() {
		return imageC;
	}
	public void setImageC(String imageC) {
		this.imageC = imageC;
	}
	public String getImageD() {
		return imageD;
	}
	public void setImageD(String imageD) {
		this.imageD = imageD;
	}
	
	@Override
	public String toString() {
		return "SingleChoice [optionA=" + optionA + ", optionB=" + optionB + ", optionC=" + optionC + ", optionD="
				+ optionD + "]";
	}
	
	
}
