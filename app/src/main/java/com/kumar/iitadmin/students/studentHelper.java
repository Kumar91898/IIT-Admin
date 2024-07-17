package com.kumar.iitadmin.students;

public class studentHelper {
    String Name;
    String StudentID;
    String Email;
    String Contact;
    String Gender;
    String Course;

    public studentHelper(){

    }
    public studentHelper(String name, String studentID, String email, String contact, String gender, String course) {
        Name = name;
        StudentID = studentID;
        Email = email;
        Contact = contact;
        Gender = gender;
        Course = course;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }
}
