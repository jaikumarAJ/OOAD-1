package goru.assign3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Scanner;

/**@author SwathiGoru
 * @version 3.0
 **/
/**
 * Launches a quiz for 6th graders by taking question from QuestionBank.
 * @see QuestionBank
 * @see Question
 * @author SwathiGoru
 *
 */
public class QuizApp implements ActionListener {

	// GUI components
	JFrame frame;
	JPanel titlePanel, timerPanel, topicsPanel, questionsPanel, resultPanel; 
	
	JLabel titleLabel;
	
	JLabel topicsLabel;
	JRadioButton topicBtn1;
	JRadioButton topicBtn2;
	JRadioButton topicBtn3;
	JButton btnChangeTopic;
	
	JLabel questionLabel;
	JTextField ansField;
	JRadioButton optionRBtn_A;
	JRadioButton optionRBtn_B;
	JRadioButton optionRBtn_C;
	JRadioButton optionRBtn_D;
	JButton submitBtn;
	JButton finishBtn;
	Question currQuestion;
	ButtonGroup optionsGroup;
	DrawBarChart dbc;
	JLabel imgLabel ;
	Timer countDown;
	JComboBox<String> topicsCBox;
	
	JLabel resultLabel;
	JLabel pointsLabel;
	JLabel resultLabel2;
	JLabel resultScience;
	JLabel resultEnglish;
	JLabel resultGeo;
	JLabel topicsSeperate;
	JProgressBar timerBar;
	JLabel hintLabel;
	
	
	// Variable Used
	String questionsFile = new String("questionBank.txt");
	String [] topicsList = {"SCIENCE", "ENGLISH", "GEOGRAPHY"};
	int qTopicIndex;
	int totalScore = 0;
	int totalAttempted = 0;
	int totalCorrectAns = 0;
	int totalQuestionAttempted = 0;
	int scoreScience = 0;
	int scienceCorrectAns = 0;
	int scoreScienceAttempted = 0;
	int scienceQuestionAttempted = 0;
	int scoreEnglish = 0;
	int englishCorrectAns = 0;
	int scoreEnglishAttempted = 0;
	int englishQuestionAttempted = 0;
	int scoreGeo = 0;
	int geoCorrectAns = 0;
	int scoreGeoAttempted = 0;
	int geoQuestionAttempted = 0;
	int count = 60;
	public final static int ONE_MINUTE = 60;
	String [][] results;
	private int progress = 0;
	
	// QuestionBank arraylists having questions based on topics 
	QuestionBank scienceBank, englishBank, geoBank;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					QuizApp window = new QuizApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor to initiate the GUI and load question.
     * 
     * @see loadQuestions
     * @see initializeGUI
     * @see setupListeners
     * 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 * 
	 * @author SwathiGoru
	 */
	public QuizApp() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		initializeGUI();
		loadQuestions();
		setupListeners();
	}

/**
 * This function creates GUI components used in the QuizApp.
 * @throws ClassNotFoundException
 * @throws InstantiationException
 * @throws IllegalAccessException
 * @throws UnsupportedLookAndFeelException
 * 
 * @author SwathiGoru
 */
	public void initializeGUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		frame = new JFrame("Quiz Application for 6th graders");
		frame.setBounds(400, 400, 500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		titlePanel = new JPanel();
		titlePanel.setBounds(5, 5, 500, 30);
		frame.getContentPane().add(titlePanel);
		
		titleLabel = new JLabel("Top Quiz");
		titleLabel.setFont(new Font("Courier New", Font.BOLD, 16));
		titlePanel.add(titleLabel);
		
		topicsPanel = new JPanel();
		topicsPanel.setBounds(6, 41, 299, 40);
		frame.getContentPane().add(topicsPanel);
		
		topicsLabel = new JLabel("  Topics   ", SwingConstants.LEFT);
		topicsPanel.add(topicsLabel);
		
		topicsCBox = new JComboBox<String>();
		topicsPanel.add(topicsCBox);
		topicsCBox.addItem("Topic 1");
		topicsCBox.addItem("Topic 2");
		topicsCBox.addItem("Topic 3");
		topicsCBox.setSelectedIndex(0);
		
		timerPanel = new JPanel();
		timerPanel.setBounds(304, 40, 190, 40);
		frame.getContentPane().add(timerPanel);
		
		timerBar = new JProgressBar();
		timerBar.setStringPainted(true);
		timerBar.setMaximum(60);
		timerBar.setForeground(Color.red);
		timerBar.setBackground(Color.green);
		timerBar.setMaximumSize(new Dimension(120, 30));
		timerPanel.add(timerBar);
		
		questionsPanel = new JPanel();
		questionsPanel.setBounds(5, 80, 464, 200);
		frame.getContentPane().add(questionsPanel);
		GridBagLayout gblQuestionsPanel = new GridBagLayout();
		gblQuestionsPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gblQuestionsPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gblQuestionsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gblQuestionsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		questionsPanel.setLayout(gblQuestionsPanel);
		
		questionLabel = new JLabel("Question");
		GridBagConstraints gbcQuestionsLabel = new GridBagConstraints();
		gbcQuestionsLabel.gridheight = 2;
		gbcQuestionsLabel.gridwidth = 3;
		gbcQuestionsLabel.insets = new Insets(0, 0, 5, 5);
		gbcQuestionsLabel.gridx = 2;
		gbcQuestionsLabel.gridy = 1;
		questionsPanel.add(questionLabel, gbcQuestionsLabel);
		
		hintLabel = new JLabel("Hint:");
		GridBagConstraints gbc_lblHint = new GridBagConstraints();
		gbc_lblHint.anchor = GridBagConstraints.WEST;
		gbc_lblHint.insets = new Insets(0, 0, 5, 5);
		gbc_lblHint.gridx = 0;
		gbc_lblHint.gridy = 2;
		questionsPanel.add(hintLabel, gbc_lblHint);
		hintLabel.setVisible(false);
		
		pointsLabel = new JLabel("points");
		pointsLabel.setBackground(Color.YELLOW);
		pointsLabel.setForeground(new Color(25, 25, 112));
		pointsLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		GridBagConstraints gbc_lblPoints = new GridBagConstraints();
		gbc_lblPoints.insets = new Insets(0, 0, 5, 0);
		gbc_lblPoints.gridx = 6;
		gbc_lblPoints.gridy = 2;
		questionsPanel.add(pointsLabel, gbc_lblPoints);
			
		ansField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 3;
		questionsPanel.add(ansField, gbc_textField);
		ansField.setColumns(10);
		
		optionRBtn_A = new JRadioButton("Option A", true);
		optionRBtn_A.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_rdbtnNewRadioButton_3 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_3.fill = GridBagConstraints.BOTH;
		gbc_rdbtnNewRadioButton_3.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_3.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_3.gridx = 3;
		gbc_rdbtnNewRadioButton_3.gridy = 4;
		questionsPanel.add(optionRBtn_A, gbc_rdbtnNewRadioButton_3);
		
		optionRBtn_B = new JRadioButton("Option B", false);
		optionRBtn_B.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_rdbtnNewRadioButton_4 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_4.gridwidth = 2;
		gbc_rdbtnNewRadioButton_4.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_4.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_4.gridx = 4;
		gbc_rdbtnNewRadioButton_4.gridy = 4;
		questionsPanel.add(optionRBtn_B, gbc_rdbtnNewRadioButton_4);
		
		optionRBtn_C = new JRadioButton("Option C", false);
		optionRBtn_C.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_rdbtnNewRadioButton_5 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_5.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_5.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_5.gridx = 3;
		gbc_rdbtnNewRadioButton_5.gridy = 5;
		questionsPanel.add(optionRBtn_C, gbc_rdbtnNewRadioButton_5);
		
		optionRBtn_D = new JRadioButton("Option D", false);
		optionRBtn_D.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_rdbtnNewRadioButton_6 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_6.gridwidth = 2;
		gbc_rdbtnNewRadioButton_6.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_6.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_6.gridx = 4;
		gbc_rdbtnNewRadioButton_6.gridy = 5;
		questionsPanel.add(optionRBtn_D, gbc_rdbtnNewRadioButton_6);
		
		optionsGroup = new ButtonGroup();
		optionsGroup.add(optionRBtn_A);
		optionsGroup.add(optionRBtn_B);
		optionsGroup.add(optionRBtn_C);
		optionsGroup.add(optionRBtn_D);
		
		submitBtn = new JButton("Submit Answer");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 6;
		questionsPanel.add(submitBtn, gbc_btnNewButton);
		
		finishBtn = new JButton("Finish Quiz");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 4;
		gbc_btnNewButton_1.gridy = 6;
		questionsPanel.add(finishBtn, gbc_btnNewButton_1);
		
		ImageIcon img = new ImageIcon("usa.png");
		GridBagConstraints gbc_temp = new GridBagConstraints();
		gbc_temp.insets = new Insets(0, 0, 5, 5);
		imgLabel = new JLabel(img);
		gbc_temp.gridwidth = 1;
		gbc_temp.gridheight = 3;
		gbc_temp.gridx = 0;
		gbc_temp.gridy = 3;
		gbc_temp.weightx = 1;
		gbc_temp.weighty = 1;
		questionsPanel.add(imgLabel,gbc_temp);
		imgLabel.setVisible(false);

		resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		resultPanel.setBounds(5, 40, 480, 220);
		frame.getContentPane().add(resultPanel);
		
		resultLabel = new JLabel("Total", SwingConstants.LEFT);
		resultPanel.add(resultLabel);
		
		
		resultLabel2 = new JLabel("Questions", SwingConstants.LEFT);
		resultPanel.add(resultLabel2);
		
		topicsSeperate = new JLabel ("\n================= Scores By Topics  ====================");
		resultPanel.add(topicsSeperate);

		resultScience = new JLabel("Sciecnce", SwingConstants.LEFT);
		resultPanel.add(resultScience);
		
		resultEnglish = new JLabel("English", SwingConstants.LEFT);
		resultPanel.add(resultEnglish);
		
		resultGeo = new JLabel("Geography", SwingConstants.LEFT);
		resultPanel.add(resultGeo);

		resultPanel.setVisible(false);
	}
	
	/**
	 * This function returns a question from an ArrayList of questions
	 * @param questions 
	 * @return a Question object
	 * 
	 * @author SwathiGoru
	 */
	public Question getQuestion(ArrayList<Question> questions) {
		Question q = null;
		boolean found = false;

		Iterator<Question> iterator = questions.iterator();
		while (iterator.hasNext()) {
			q = iterator.next();
			if(!q.getVisitedQuestion()) {
				found = true;
				break;
			}
		}
		if (!found) {
			q = null;
		}
		return q;
	}
	
	/**
	 * Loads questions from the txt file and categorizes them according to the question topic. 
	 * It also adds the questions to appropriate QuestionBank based on the topic.
	 * Runs a timer of 60 secs for each question.
	 * 
	 * @author SwathiGoru
	 */
	public void loadQuestions() {	
		
		File file = new File(questionsFile);
		Scanner scan = null;
		
		scienceBank = new QuestionBank("SCIENCE", new ArrayList<Question>());
		englishBank = new QuestionBank("ENGLISH", new ArrayList<Question>());
		geoBank = new QuestionBank("GEOGRAPHY", new ArrayList<Question>());
		
		try {
			scan = new Scanner(file);
			
			while(scan.hasNextLine()) {
				String line = scan.nextLine();

				String [] details = line.split(":");
				String topic = details[0];
				String question = details[1];
				String typeOfQuestion = details[2];
				String correctAns = details[3];
				String optionA = details[4];
				String optionB = details[5];
				String optionC = details[6];
				String optionD = details[7];
				int points = Integer.parseInt(details[8]);
				String image = details[9];
				
				Question q = new Question(topic, question, typeOfQuestion, 
						                  correctAns, optionA, optionB, optionC, 
						                  optionD, points, image, false);
				if (topic.equals("SCIENCE")) {
					scienceBank.addQuestion(q);;
				} else if (topic.equals("ENGLISH")) {
					englishBank.addQuestion(q);
				} else {
					geoBank.addQuestion(q);
				}				
				
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found"+ file.toString());
		} finally {
			if (scan != null)
				scan.close();
		}

		topicsCBox.removeAllItems();
		topicsCBox.addItem("SCIENCE");
		topicsCBox.addItem("ENGLISH");
		topicsCBox.addItem("GEOGRAPHY");
		
		
		countDown = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				count--;
				if (count == 0) {
					timeOut();
				}
				timerBar.setValue(progress);
				timerBar.setString(count + " secs remaining");
				progress++;
			}
			
		});
		
		
		Question firstQuestion = null;
		if (scienceBank.getQuestionList().size() != 0) {
		    firstQuestion = getQuestion(scienceBank.getQuestionList());
		    topicsCBox.setSelectedIndex(0);
		    qTopicIndex = 0;
		} else if(englishBank.getQuestionList().size() != 0) {
			firstQuestion = getQuestion(englishBank.getQuestionList());
			 topicsCBox.setSelectedIndex(1);
			 qTopicIndex = 1;
		} else if(geoBank.getQuestionList().size() != 0) {
			firstQuestion =  getQuestion(geoBank.getQuestionList());
			topicsCBox.setSelectedIndex(2);
			qTopicIndex = 2;
		} 

		if (firstQuestion == null) {
			topicsCBox.setSelectedIndex(0);
			refreshErrorPanel();
		} else {
			currQuestion = firstQuestion;
			refreshPanel(firstQuestion);
			count = 60;
			countDown.start();
		}
	}
	
	/**
	 * When the timer expires, the question is marked as attempted. The control moves on to the next question.
	 * 
	 * @author SwathiGoru
	 */
	public void timeOut() {
		currQuestion.setVisitedQuestion();
		totalAttempted += currQuestion.getPoints();
		selectTopic(true);
	}
	
	/**
	 * Sets all the listeners for user actions on several UI components.
	 *  
	 * @author SwathiGoru
	 */
	public void setupListeners() {
		submitBtn.addActionListener(this);
		finishBtn.addActionListener(this);
		topicsCBox.addActionListener(this);
	}
	
	/**
	 * When there are no more questions for display in a specified topic, 
	 * this function is used to refresh the panel and notifies the user.
	 * 
	 * @author SwathiGoru
	 */
	public void refreshErrorPanel() {
		optionRBtn_A.setVisible(false);
		optionRBtn_B.setVisible(false);
		optionRBtn_C.setVisible(false);
		optionRBtn_D.setVisible(false);
		ansField.setVisible(false);
		submitBtn.setVisible(false);
		pointsLabel.setVisible(false);
		timerBar.setVisible(false);
		questionLabel.setText("No more questions in this topic. Please choose a different topic.");

	}
	
	/**
	 * This function populates the panel with the information for the question related UI fields.
	 * 
	 * @param q Question object
	 * 
	 * @author SwathiGoru
	 */
	public void refreshPanel(Question q) { 
		questionLabel.setText(q.getQuestion());
		if (q.getTypeOfQuestion().equals("M")) {
			ansField.setVisible(false);
			optionRBtn_A.setText(q.getOptionA());
			optionRBtn_B.setText(q.getOptionB());
			optionRBtn_C.setText(q.getOptionC());
			optionRBtn_D.setText(q.getOptionD());
			optionRBtn_A.setVisible(true);
			optionRBtn_B.setVisible(true);
			optionRBtn_C.setVisible(true);
			optionRBtn_D.setVisible(true);
			submitBtn.setVisible(true);
		} else {
			optionRBtn_A.setVisible(false);
			optionRBtn_B.setVisible(false);
			optionRBtn_C.setVisible(false);
			optionRBtn_D.setVisible(false);
			ansField.setText("");
			ansField.setVisible(true);
			submitBtn.setVisible(true);
		}
		
		if (!q.getImage().equals("NONE")) {
			String image = q.getImage();
			ImageIcon imgIcon = new ImageIcon(image);
			imgLabel.setIcon(imgIcon);
			imgLabel.setVisible(true);
			hintLabel.setVisible(true);
		}
		pointsLabel.setText(" Points: " + Integer.toString(q.getPoints()));
		pointsLabel.setVisible(true);
	}
	
	/**
	 * Specifies all the actions to be performed for the listeners in setupListeners()
	 * @see setupListeners
	 * @see submitAnswer
	 * @see nextQuestion
	 * @see selectTopic
	 * @see displayResult
	 * @author SwathiGoru
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		imgLabel.setVisible(false);
		hintLabel.setVisible(false);
		
		if(e.getSource() == topicsCBox) {
			selectTopic(false);
		} else if (e.getSource() == submitBtn) {
			submitAnswer();
			nextQuestion();
		} else if (e.getSource() == finishBtn) {
			displayResult();
		} else {
			System.out.println("Something went wrong.");
		}
		
	}
	
	/**
	 * This function verifies the correct answer vs. the user given answer for a specific question.
	 * It updates the scores and questions attempted by the user.
	 * @param ans
	 * 
	 * @author SwathiGoru
	 */
	public void checkAnswer(String ans) {
		String topic = currQuestion.getTopic();
		if (ans.equals((currQuestion.getCorrectAns().trim()).toLowerCase())) {
		    if (topic.equals("SCIENCE")) {
		    	scoreScience += currQuestion.getPoints();
		    	scienceCorrectAns++;
		    } else if (topic.equals("ENGLISH")) {
		    	scoreEnglish += currQuestion.getPoints();
		    	englishCorrectAns++;
		    } else if (topic.equals("GEOGRAPHY")) {
		    	scoreGeo += currQuestion.getPoints();
		    	geoCorrectAns++;
		    }
		    totalScore += currQuestion.getPoints();
		    totalCorrectAns++;
		} 
		
	    if (topic.equals("SCIENCE")) {
	    	scoreScienceAttempted += currQuestion.getPoints();
	    	scienceQuestionAttempted++;
	    } else if (topic.equals("ENGLISH")) {
	    	scoreEnglishAttempted += currQuestion.getPoints();
	    	englishQuestionAttempted++;
	    } else if (topic.equals("GEOGRAPHY")) {
	    	scoreGeoAttempted += currQuestion.getPoints();
	    	geoQuestionAttempted++;
	    }
	    totalAttempted += currQuestion.getPoints();
	    totalQuestionAttempted++;
	}
	
	/**
	 * This function selects a question for the given topic.
	 * @param next flag for next Question
	 * 
	 * @author SwathiGoru
	 */
	public void selectTopic(boolean next) {
		countDown.stop();
		if (topicsCBox.getSelectedIndex() != -1) {
			int index = topicsCBox.getSelectedIndex();
			if ((index != qTopicIndex) || next) {
				qTopicIndex = index;
				if (index == 0) {
					currQuestion = getQuestion(scienceBank.getQuestionList());
				} else if (index ==1) {
					currQuestion = getQuestion(englishBank.getQuestionList());
				} else if (index == 2) {
					currQuestion = getQuestion(geoBank.getQuestionList());
				}
				
				if (currQuestion == null) {
					refreshErrorPanel();
				} else {
					refreshPanel(currQuestion);
					count = ONE_MINUTE;
					progress = 0;
					countDown.start();
				}
		    
			}
		}
	}
	
	/**
	 * Based on the type of question, this function calls in checkAnswer.
	 * 
	 * @see checkAnswer
	 * @author SwathiGoru
	 */
	public void submitAnswer() {
		currQuestion.setVisitedQuestion();
		if (currQuestion.getTypeOfQuestion().equals("M")) {
		    Enumeration<AbstractButton> radioBtn_all = optionsGroup.getElements();
		    while (radioBtn_all.hasMoreElements()) {
		    	JRadioButton temp = (JRadioButton) radioBtn_all.nextElement();
		    	if (temp.isSelected()) {
		    		checkAnswer((temp.getText().trim()).toLowerCase());
		    	}
		    }
		} else {
			checkAnswer((ansField.getText().trim()).toLowerCase());
		}
	}
	
	/**
	 * Selects the next question
	 * 
	 * @author SwathiGoru
	 */
	public void nextQuestion() {
		selectTopic(true);
	}
	
	/**
	 * Displays the final results once the quiz is complete
	 * Also displays a graphical representation of the final scores
	 * @author SwathiGoru
	 */
	public void displayResult() {	
		countDown.stop();
		timerPanel.setVisible(false);
		topicsPanel.setVisible(false);
		questionsPanel.setVisible(false);
		
		resultLabel.setText("  Total Score: " + Integer.toString(totalScore) + "/" + Integer.toString(totalAttempted));
		resultLabel2.setText("| Total Questions Correct: " + totalCorrectAns + " | Total Attempted: " + totalAttempted);
		resultScience.setText("SCIENCE :-  Score " + scoreScience +  "/" + scoreScienceAttempted + "| Total Questions Correct: " + scienceCorrectAns + " |   Total Attempted: " + scienceQuestionAttempted);
		resultEnglish.setText("ENGLISH :-  Score " + scoreEnglish +  "/" + scoreEnglishAttempted + "| Total Questions Correct: " + englishCorrectAns + " |   Total Attempted: " + englishQuestionAttempted);
		resultGeo.setText("GEO :-  Score " + scoreScience +  "/" + scoreGeoAttempted + "| Total Questions Correct: " + geoCorrectAns + " |   Total Attempted: " + geoQuestionAttempted);		
		results = new String[3][3];
		results[0][0] = "SCIENCE";
		results[0][1] = Integer.toString(this.scienceCorrectAns);
		results[1][0] = "ENGLISH";
		results[1][1] = Integer.toString(this.englishCorrectAns );
		results[2][0] = "GEOGRAPHY";
		results[2][1] = Integer.toString(this.geoCorrectAns);
	    dbc = new DrawBarChart(results, resultPanel.getWidth(), resultPanel.getHeight());
		resultPanel.add(dbc);
		resultPanel.setVisible(true);
		dbc.refreshPaint();
	}
}

