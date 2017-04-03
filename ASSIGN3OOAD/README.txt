Assign 3 (100 + EC 15 pts)						Due: 28th Nov (10 pm)

You are required to design and implement a Swing-based interactive quiz called “TopQuiz” to test the user’s knowledge of a specific subject area and ability to give the answer in the given time (clues may be provided via pictures).
You may select the target audience for the quiz, for example, the target group may be first to 5th graders. Accordingly, you will select the topics and the manner of displaying the questions.
The following description gives some of the main features of the quiz. You are free to design the quiz using your own creative approach.
On the quiz page (interface), a list of subject areas/topics are displayed, for example, US History, movies, geography, animals etc. These are only example topics. Your choice of topics will be based on the choice of your target audience. Please feel free to select topics of your choice. Show at least 3 topics.

1.0 Starting the Quiz
The user chooses a topic/subject area and starts the quiz. The subject areas should be displayed as a list of choices to user. The user selects the subject area of interest; a question (randomly selected from a list of questions) is displayed, along with the options for multiple choices. Along with the question, a picture may be shown and the question may refer to the picture. Or a picture may be given as a clue to the answer. The user may select a different subject area for the next question. See some example Screen shots shown below. Note: These are only examples and you are free to design the interface using your own creative approach.
 

1.1 Ending the Quiz
When an end button is pressed, the total questions attempted, the number questions answered successfully and the total score for the quiz thus far should be displayed as described in 1.3. 

1.2 Timing the Quiz
You will allot a reasonable amount of time to answer a question. You will show the time elapsed (and time left) using a method of your choice. You may choose a method that is friendly and unobtrusive, yet inform the user of time left. For example, you may show a normal face (see the figures below), moving slowly from left edge to the right edge. Before it reaches the right edge, if the question is not answered, the face turns sad, otherwise, it is smiling. You can use your creativity in combining the animation and a timer.
  

1.3 Showing the Scores
The score is shown in two ways:
a)	Using text, show the number of questions attempted, number that were correct and the total score.
b)	Using a graphical display, show a bar graph (no need to show the numbers) where each graph represents a subject category and the height of the bar represents the number of questions correctly answered.



2.0 Some requirements and implementation details
a)	For each question, the data includes the question itself, topic area, choices, points for the question, correct answer and possibly an image should be stored in a database. Your database may consist of a text file, a serialized file, an XML file or an RDBMS. You are free to choose the method of storage.
b)	Each question data read from a file should be used to instantiate a Question object (implies that you define a class called Question) in your program. All question objects should be stored in a QuestionBank object in your program.
c)	You may additionally define different types of Question classes, some multiple choices, some one word answer questions etc. The format used to display the question should be dependent on the Question type. For example, if the question is of multiple choice type, you will use a set of RadioButtons to display the choices. If it is a one word answer question, you will display a text box (or a ComboBox).
d)	You must accommodate the Question display, timer display and score display in the same window.
