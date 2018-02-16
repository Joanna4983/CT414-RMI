package ct414Client;

import java.rmi.Naming;
import java.util.List;

import ct414.ExamServer;
import ct414.UnauthorizedAccess;
import ct414.Assessment_Interface;

public class ClientController {
	private ExamServer examServer; // = (ExamServer) Naming.lookup("//localhost/ExamServer");
	private int studentId;
	private String password = "";
	private int loginToken = 0;
	
//	int studentId = 12345678;
//	String password = "coder4567";

	public ClientController() {
		try {
			examServer = (ExamServer) Naming.lookup("//localhost/ExamServer");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean login(int username, String password) {
		this.studentId = username;
		this.password = password;
		try {
			this.loginToken = examServer.login(this.studentId, this.password);
		} catch (UnauthorizedAccess e) {
			this.loginToken = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.loginToken == 1;
	}
	
	public List<String> getAvailableAssessments() {
		List<String> availableAssessments = null;
		try {
			availableAssessments = examServer.getAvailableSummary(this.loginToken, this.studentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return availableAssessments;
	}
	
	public Assessment_Interface getAssessment(String courseCode) {
		Assessment_Interface assessment = null;
		try {
			assessment = examServer.getAssessment(this.loginToken, this.studentId, courseCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assessment;
	}
	
	public void submitAssessment(Assessment_Interface assessment) {
		try {
			examServer.submitAssessment(this.loginToken, this.studentId, assessment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
