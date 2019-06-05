package tpg.domain;

import tpg.common.DataBaseUtil;

public class KnowledgePoint {
	private int id;
	private int subjectId;
	private String name;
	
	public KnowledgePoint() {	}
	
	public KnowledgePoint(int id, int subjectId, String name) {
		super();
		this.id = id;
		this.subjectId = subjectId;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static String getKnowledgePointById(int id) {
		String knowledgePoint = "";
		DataBaseUtil db = new DataBaseUtil();
		String sql = "select * from knowledge_point where id=?";
		System.out.println(sql);
		Object[] params = {id};
		Class<Subject> clazz = Subject.class;
		knowledgePoint = db.query(sql, params, clazz).get(0).getName();
		System.out.println(knowledgePoint);
		return knowledgePoint;
	}
	
}
