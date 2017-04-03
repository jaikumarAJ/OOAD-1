package goru.assign3;

/**@author SwathiGoru
 * @version 3.0
 **/
/**
 * Creates a Question with attributes like description, topic, type of question, options.
 * Each new question is added to the QuestionBank class.
 * @see QuestionBank
 * @author SwathiGoru
 *
 */
class Question {
	private String topic, question, typeOfQuestion, correctAns, optionA, optionB, optionC, optionD, image;
	private int points;
	private boolean visitedQuestion;
	
	public Question(String topic, String question, String typeOfQuestion, 
			String correctAns, String optionA, String optionB, String optionC, 
			String optionD, int points, String image, boolean visitedQuestion) {
		this.topic = topic;
		this.question = question;
		this.typeOfQuestion = typeOfQuestion;
		this.correctAns = correctAns;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.points = points;
		this.image = image;
		this.visitedQuestion = visitedQuestion;
	}
	
	/**
     * Returns a string that specifies the name of the topic.
     * @return the string "topic"
     * @see QuizApp#loadQuestions
     */
	public String getTopic() {
		return this.topic;
	}
	
	/**
     * Returns a string that specifies the description of the question.
     * @return the string "question"
     * @see QuizApp#loadQuestions
     */
	public String getQuestion() {
		return this.question;
	}
	
	/**
     * Returns a string that specifies the type of the question.
     * @return the string "typeOfQuestion"
     * @see QuizApp#loadQuestions
     */
	public String getTypeOfQuestion() {
		return this.typeOfQuestion;
	}
	
	/**
     * Returns a string that specifies the correct answer for the question.
     * @return the string "correctAns"
     * @see QuizApp#loadQuestions
     */
	public String getCorrectAns() {
		return this.correctAns;
	}
	
	/**
     * Returns a string that gets the optionA for the question.
     * @return the string "optionA"
     * @see QuizApp#loadQuestions
     */
	public String getOptionA() {
		return this.optionA;
	}
	
	/**
     * Returns a string that gets the optionB for the question.
     * @return the string "optionB"
     * @see QuizApp#loadQuestions
     */
	public String getOptionB() {
		return this.optionB;
	}
	
	/**
     * Returns a string that gets the optionC for the question.
     * @return the string "optionC"
     * @see QuizApp#loadQuestions
     */
	public String getOptionC() {
		return this.optionC;
	}
	
	/**
     * Returns a string that gets the optionD for the question.
     * @return the string "optionD"
     * @see QuizApp#loadQuestions
     */
	public String getOptionD() {
		return this.optionD;
	}
	
	/**
     * Returns an integer value that specifies the points for the question.
     * @return the integer "points"
     * @see QuizApp#loadQuestions
     */
	public int getPoints() {
		return this.points;
	}
	
	/**
     * Returns a boolean value for the question that has been visited already.
     * @return boolean
     * @see QuizApp#loadQuestions
     */
	public boolean getVisitedQuestion() {
		return this.visitedQuestion;
	}
	
	/**
     * Sets a flag to a question once it is visited/viewed in the quiz.
     * @see QuizApp#loadQuestions
     */
	public void setVisitedQuestion() {
		this.visitedQuestion = true;
	}
	
	/**
     * Returns the image name associated to a question.
     * @see QuizApp#loadQuestions
     */
	public String getImage() {
		return this.image;
	}
}
