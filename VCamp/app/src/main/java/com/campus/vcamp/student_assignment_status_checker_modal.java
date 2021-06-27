package com.campus.vcamp;

public class student_assignment_status_checker_modal {

    private String Date, Semester, Subject, Teacher, Url, Status;

    student_assignment_status_checker_modal(){}

    student_assignment_status_checker_modal(String Date, String Semester, String Subject, String Teacher, String Url, String Status){
        this.Date = Date;
        this.Semester = Semester;
        this.Subject = Subject;
        this.Teacher = Teacher;
        this.Url = Url;
        this.Status = Status;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
