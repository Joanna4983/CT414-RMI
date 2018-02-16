package ct414Client;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import ct414.Assessment_Interface;
import ct414.Question;

public class ClientGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ClientController controller;
	
	private Container container;
	private JTextField errorMessage;
	
	/* Login Components */
	private JTextField usernameText, usernameInput, passwordText;
	private JPasswordField passwordField;
	private JButton loginButton;
	
	/* Assessments View */
	private JComboBox<String> availableAssessmentsComboBox;
	private JButton getAssessmentButton; 
	
	/* Specific Assessment View */
	// TODO
	private JTextField questionText;
	private ButtonGroup answerOptionsGroup;
	private ArrayList<JRadioButton> answerOptions = new ArrayList<JRadioButton>(100); 
	private JButton assessmentButton;
	private int nextQuestionToDisplay = 0;
	
	
	/*
	 * Flow goes:
	 * 
	 * loginGUI -> AssessmentsListView -> SpecificAssessmentsView - |
	 * 						^----------------------------------- <- 
	 * */
	
	public ClientGUI() {
		super( "CT414 Assessment System" );
		controller = new ClientController();
		container = getContentPane();
		container.setLayout( new FlowLayout() );
		
		setupLoginGUI();
		
		setSize( 325, 300 );
		setVisible( true );
		
	}
	
	private void setupLoginGUI() {
		usernameText = new JTextField( "Username:", 10 );
		usernameText.setEditable( false );
		container.add( usernameText );
		usernameInput = new JTextField("12345678", 10 );
		container.add( usernameInput );
		
		passwordText = new JTextField( "Password:", 10 );
		passwordText.setEditable( false );
		container.add( passwordText );
		passwordField = new JPasswordField( "coder4567", 10 );
		container.add( passwordField );
		
		loginButton = new JButton("Login");
		container.add(loginButton);
		LoginHandler loginHandler = new LoginHandler();
		loginButton.addActionListener(loginHandler);
	}
		
	private class LoginHandler implements ActionListener {
	    // process text field events
	    public void actionPerformed( ActionEvent event )
	    {
		    int username = Integer.parseInt(usernameInput.getText());
		    String password = new String(passwordField.getPassword());
		    boolean success = controller.login(username, password);
		    if (success) {
		    	clearUI();
		    	setupAssessmentsView();
		    } else {
		    	displayErrorMessage("Could not find login user " + username);
		    }
		}
	}
	
	private void clearUI() {
		container.removeAll();
		container.validate();
		container.repaint();
	}
	
	private void setupAssessmentsView() {
		
		ArrayList<String> assessments = (ArrayList<String>) controller.getAvailableAssessments();
		String[] assessmentsList = new String[assessments.size()];
		assessmentsList = (String[]) assessments.toArray(assessmentsList);
		
		availableAssessmentsComboBox = new JComboBox<String>( assessmentsList );
		availableAssessmentsComboBox.setMaximumRowCount( 5 );
		container.add(availableAssessmentsComboBox);
		
		getAssessmentButton = new JButton("Get Selected Assessment");
		container.add(getAssessmentButton);
		AssessmentsListViewerHandler assessmentHandler = new AssessmentsListViewerHandler();
		getAssessmentButton.addActionListener(assessmentHandler);
		
		setVisible(true);
		
	}
	
	private void displayErrorMessage(String message) {
		errorMessage = new JTextField( message, 20 );
		errorMessage.setEditable( false );
		container.add( errorMessage );
		container.validate();
		container.repaint();
	}
	
	private class AssessmentsListViewerHandler implements ActionListener {
	    public void actionPerformed( ActionEvent event )
	    {
		    String selected = (String) availableAssessmentsComboBox.getSelectedItem();
		    Assessment_Interface assessment = controller.getAssessment(selected);
		    if (assessment != null) {
		    	clearUI();
		    	setupSpecificAssessmentView(assessment);
		    } else {
		    	displayErrorMessage("No assessments available!");
		    }
		}
	}
	
	private void setupSpecificAssessmentView(Assessment_Interface assessment) {
		ArrayList<Question> questions = (ArrayList<Question>) assessment.getQuestions();

		displayQuestion(questions.get(nextQuestionToDisplay));
		nextQuestionToDisplay++;
		
		ActionListener handler;
		String buttonText = "";
		if (nextQuestionToDisplay >= questions.size()) {
			buttonText = "Submit";			
			handler = new SubmitAssessmentHandler(assessment);
		} else {
			buttonText = "Next Question";
			handler = new NextQuestionHandler(assessment);
		}
		assessmentButton = new JButton(buttonText);
		container.add(assessmentButton);
		assessmentButton.addActionListener(handler);

		setVisible(true);
	}
	
	private void displayQuestion(Question question) {
		String questionFull = Integer.toString(question.getQuestionNumber()) + ": " +
				question.getQuestionDetail();
		questionText = new JTextField( questionFull , 10 );
		questionText.setEditable( false );
		container.add(questionText);
		
		String[] questionAnswerOptions = question.getAnswerOptions();
		answerOptions = new ArrayList<JRadioButton>(100);
		for (String answer: questionAnswerOptions) {
			answerOptions.add(new JRadioButton(answer, false));	
		}
		
		answerOptionsGroup = new ButtonGroup();
		for (JRadioButton answerOption: answerOptions) {
			container.add(answerOption);
			answerOptionsGroup.add(answerOption);
		}
	}
	
	private class SubmitAssessmentHandler implements ActionListener {
	    Assessment_Interface currentAssessment;
	    public SubmitAssessmentHandler(Assessment_Interface assessment) {
	    	this.currentAssessment = assessment;
	    }
	    
		public void actionPerformed( ActionEvent event )
	    {
			boolean success = selectAnswersFromGUI(this.currentAssessment);

	    	if (success) {
	    		controller.submitAssessment(this.currentAssessment);
	    		JOptionPane.showMessageDialog( null, "Assessment Submitted!");
	    		clearUI();
	    		setupAssessmentsView();
	    	} else {
	    		displayErrorMessage("Please select an Answer!");
	    	}
		}
	}
	
	private class NextQuestionHandler implements ActionListener {
	    Assessment_Interface currentAssessment;
	    public NextQuestionHandler(Assessment_Interface assessment) {
	    	this.currentAssessment = assessment;
	    }
	    
		public void actionPerformed( ActionEvent event )
	    {
	    	boolean success = selectAnswersFromGUI(this.currentAssessment);
	    	
	    	if (success) {
	    		clearUI();
		    	setupSpecificAssessmentView(this.currentAssessment);	
	    	} else {
	    		displayErrorMessage("Please select an Answer!");
	    	}
		}
	}
	
	private boolean selectAnswersFromGUI(Assessment_Interface assessment) {
		int currentQuestionIndex = this.nextQuestionToDisplay - 1;
		ButtonModel selectedAnswer = answerOptionsGroup.getSelection();
		if (selectedAnswer == null) {
			return false;
		}
		String selectedAnswerText = selectedAnswer.getActionCommand(); 
		String[] answerOptions = assessment.getQuestions().get(currentQuestionIndex).getAnswerOptions();
		int currentQuestionsAnswerIndex = 0;
		for (int i=0; i < answerOptions.length; i++) {
			if (answerOptions[i].equals(selectedAnswerText)) {
				currentQuestionsAnswerIndex = i;
				break;
			}
		}
		try {
			assessment.selectAnswer(currentQuestionIndex, currentQuestionsAnswerIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
