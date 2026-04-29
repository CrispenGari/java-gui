package helpers;

public class LoggedUser {
    String email;
    public String role;
    public String studentNumber;

    public LoggedUser(String email, String role, String studentNumber) {
        this.email = email;
        this.role = role;
        this.studentNumber = studentNumber;
    }
}