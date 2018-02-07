package ct414;

public class Question implements Question_Interface {
	
	private int qn;
	private String[] options;
	private String actual_question;

	
	public Question(int qn, String[] options, String actual_question) {
		this.qn = qn;
		this.options = options;
		this.actual_question = actual_question;
	}
	
	@Override
	public int getQuestionNumber() {
		// TODO Auto-generated method stub
		return qn;
	}

	@Override
	public String getQuestionDetail() {
		// TODO Auto-generated method stub
		return actual_question;
	}

	@Override
	public String[] getAnswerOptions() {
		// TODO Auto-generated method stub
		return options;
	}

}
