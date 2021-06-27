package com.campus.vcamp;

public class teacher_leave_acceptance_modal {
    private String Name, Date, Reason, Admission;

    teacher_leave_acceptance_modal() {}

    teacher_leave_acceptance_modal(String Name, String Date, String Reason, String Admission) {
        this.Name = Name;
        this.Date = Date;
        this.Reason = Reason;
        this.Admission = Admission;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getAdmission() {
        return Admission;
    }

    public void setAdmissionID(String admission) {
        Admission = admission;
    }
}
