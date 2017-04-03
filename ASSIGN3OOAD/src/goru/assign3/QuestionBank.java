package goru.assign3;

import java.util.ArrayList;

import goru.assign3.Question;

public class QuestionBank {
	private String topic;
	private ArrayList<Question> questionList;
	
	public QuestionBank(String topic, ArrayList<Question> questionList) {
		this.topic = topic;
		this.questionList = questionList;
	}
	
	public String getTopic() {
		return this.topic;
	}
	
	public void addQuestion(Question q) {
		this.questionList.add(q);
	}
	
	public ArrayList<Question> getQuestionList() {
		return this.questionList;
	}
	
	public void setQuestionBank(ArrayList<Question> questionList) {
		this.questionList = questionList;
	}
}
