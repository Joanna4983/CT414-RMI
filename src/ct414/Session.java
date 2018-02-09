package ct414;

import java.util.Date;

public class Session {
	
	private static final int SESSION_DUR = 60*20*1000; // 20 mins duration session
	
	private Date ex_d;
	private int active; //1-yes 0-no
	private int student_id;
	
	public Session(int s_id) {
		this.active = 1;
		this.student_id = s_id;
		this.ex_d = new Date(new Date().getTime() + SESSION_DUR);
	}
	
	public int isActive() {
		if(new Date().before(ex_d)) {
			return 1;
		} else return 0;
	}
	
	public int getAssociatedId() {
		return student_id;
	}
}
