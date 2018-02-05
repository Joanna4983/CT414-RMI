package ct414;

public class Student { // impl Serializable?

	private int id;
	private String c_code;
	private String pw;
	
	public Student(int id, String c_code, String pw) {
		this.id = id;
		this.c_code = c_code;
		this.pw = pw;
	}
	
	public int getStudentId() {
		return id;
	}
	
	public String getStudentCourseCode() {
		return c_code;
	}
	
	public String getStudentPassword() {
		return pw;
	}
}
