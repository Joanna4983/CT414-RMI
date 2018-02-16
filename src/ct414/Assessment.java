/**
 * 
 */
package ct414;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;

/**
 * @author root
 *
 */
public class Assessment implements Assessment_Interface {
	
	private String info;
	private Date close_d;
	private List<Question> mcqs = new ArrayList<Question>();
	private int id;
	private String course_code;
	private HashMap<Integer, Integer> selectedAnswers = new HashMap<Integer, Integer>();
	
	public Assessment(String info, Date close_d, List<Question> mcqs, int id, String c_c) {
		this.info = info;
		this.close_d = close_d;
		this.mcqs = mcqs;
		this.id = id;
		this.course_code = c_c;
		
	}
	
	public String getCourseCode() {
		return this.course_code;
	}

	@Override
	public String getInformation() {
		return this.course_code;
	}

	
	@Override
	public Date getClosingDate() {
		// TODO Auto-generated method stub
		return close_d;
	}

	
	@Override
	public List<Question> getQuestions() {
		// TODO Auto-generated method stub
		return this.mcqs;
	}

	
	@Override
	public Question getQuestion(int questionNumber) throws InvalidQuestionNumber {
		// TODO Auto-generated method stub
		if(questionNumber <= mcqs.size()) {
			return mcqs.get(questionNumber);
		}
		throw new InvalidQuestionNumber();
	}

	
	@Override
	public void selectAnswer(int questionNumber, int optionNumber) throws InvalidQuestionNumber, InvalidOptionNumber {
		System.out.println("SelectAnswer called: " + questionNumber + ", " + optionNumber);
		selectedAnswers.put(questionNumber, optionNumber);
	}

	
	@Override
	public int getSelectedAnswer(int questionNumber) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public int getAssociatedID() {
		// TODO Auto-generated method stub
		return id;
	}

}
