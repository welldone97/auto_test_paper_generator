package tpg.domain;

import tpg.common.DataBaseUtil;

public class Subject {
	private int id;
	private String name;
	
	public Subject(){ }
	
	public Subject(int id, String name) {
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
	
	public static String getSubjectById(int id){
		String subjectName = "";
		DataBaseUtil db = new DataBaseUtil();
		String sql = "select * from subject where id=?";
		System.out.println(sql);
		Object[] params = {id};
		Class<Subject> clazz = Subject.class;
		subjectName = db.query(sql, params, clazz).get(0).getName();
		System.out.println(subjectName);
		return subjectName;
	}
	
}
