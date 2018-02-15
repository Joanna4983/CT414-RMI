package ct414;

public class Question implements Question_Interface {
	
	private int qn;
	private String[] options;
	private String actual_question;

	
	public Question(int qn, String actual_question, String[] options, String answer) {
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
		for (String option: this.options){
			System.out.println(option);
		}
		return options;
	}

}
