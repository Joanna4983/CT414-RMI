/**
 * 
 */
package ct414;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author root
 *
 */
public class Assessment implements Assessment_Interface {
	
	private String info;
	private Date close_d;
	private List<Question> mcqs = new ArrayList<Question>();
	private int id;
	
	public Assessment(String info, Date close_d, List<Question> mcqs, int id) {
		this.info = info;
		this.close_d = close_d;
		this.mcqs = mcqs;
		this.id = id;
		
	}

	@Override
	public String getInformation() {
		// TODO Auto-generated method stub
		return info;
	}

	
	@Override
	public Date getClosingDate() {
		// TODO Auto-generated method stub
		return close_d;
	}

	
	@Override
	public List<Question_Interface> getQuestions() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Question_Interface getQuestion(int questionNumber) throws InvalidQuestionNumber {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void selectAnswer(int questionNumber, int optionNumber) throws InvalidQuestionNumber, InvalidOptionNumber {
		// TODO Auto-generated method stub

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
