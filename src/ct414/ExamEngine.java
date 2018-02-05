
package ct414;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ExamEngine implements ExamServer {
	
	//some session var here: new class? or impl here?
	private List<Student> students;
	private List<Assessment> assessments;
	

    // Constructor is required
    public ExamEngine() {
        super();
        //inst session here ?
        students = new ArrayList<Student>();
        assessments = new ArrayList<Assessment>();
        
        
        students.add(new Student(123456, "Hi", "Hi")); //testing
    }

    // Implement the methods defined in the ExamServer interface...
    // Return an access token that allows access to the server for some time period
    public int login(int studentid, String password) throws UnauthorizedAccess, RemoteException {
    	
    	for(Student s: students) {
    		if(studentid == s.getStudentId() && password.equals(s.getStudentPassword())) {
    			System.out.println("[+] " + studentid + " has been logged in.");
    			//login user
    			//activate session now
    			return 0;
    		}
    	}
    	throw new UnauthorizedAccess("[-] User " + studentid + " does not exist in our system.");
	// TBD: You need to implement this method!
	// For the moment method just returns an empty or null value to allow it to compile
    }

    // Return a summary list of Assessments currently available for this studentid
    public List<String> getAvailableSummary(int token, int studentid) throws UnauthorizedAccess, NoMatchingAssessment, RemoteException {
    		
    	for(Assessment a: assessments) {
    		if(studentid == a.getAssociatedID()) {
    			a.getInformation();
    		}
    	}
    	throw new NoMatchingAssessment("[-] There are no assessmets for " + studentid + ".");
    	
        // TBD: You need to implement this method!
        // For the moment method just returns an empty or null value to allow it to compile

    }

    // Return an Assessment object associated with a particular course code
    public Assessment_Interface getAssessment(int token, int studentid, String courseCode) throws UnauthorizedAccess, NoMatchingAssessment, RemoteException {

        // TBD: You need to implement this method!
        // For the moment method just returns an empty or null value to allow it to compile

        return null;
    }

    // Submit a completed assessment
    public void submitAssessment(int token, int studentid, Assessment_Interface completed) throws UnauthorizedAccess, NoMatchingAssessment, RemoteException {

    	for(Assessment a: assessments) {
    		if(studentid == a.getAssociatedID()) { // and date?
    			//mark as completed
    			System.out.println("[+] The submission for " + studentid + " has been successful.");
    		}
    	}
    	throw new NoMatchingAssessment("[-] The submission for " + studentid + "was unsuccessful.");
        // TBD: You need to implement this method!
    }

    public static void main(String[] args) {
    	
    	try {
    		System.setProperty("java.security.policy","file:/home/jo/Documents/CT414-RMI/bin/ct414/ct414.policy");
    	
    	}catch (Exception e) {
    		System.out.println("[-] Check policy file");
    		e.printStackTrace();
    	}
    	
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "ExamServer";
            ExamServer engine = new ExamEngine();
            ExamServer stub = (ExamServer) UnicastRemoteObject.exportObject(engine, 0);
            stub.login(14407508, "Hi");
            //changed getRegistry() to createRegistry(int) : was throwing java.rmi.UnmarshalException and java.lang.ClassNotFoundException otherwise
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(name, stub);
            System.out.println("ExamEngine bound");
        } catch (Exception e) {
            System.err.println("ExamEngine exception:");
            e.printStackTrace();
        }
    }
}
