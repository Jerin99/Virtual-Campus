package com.campus.vcamp;

public class student_leave_status_modal_class {
    private String GrantedLeave, LeaveGrantedFor, LeaveGrantedTime;

    private student_leave_status_modal_class() {}

    private student_leave_status_modal_class(String GrantedLeave, String LeaveGrantedFor, String LeaveGrantedTime) {
        this.GrantedLeave = GrantedLeave;
        this.LeaveGrantedFor = LeaveGrantedFor;
        this.LeaveGrantedTime = LeaveGrantedTime;
    }


    public String getGrantedLeave() {
        return GrantedLeave;
    }

    public void setGrantedLeave(String grantedLeave) {
        GrantedLeave = grantedLeave;
    }

    public String getLeaveGrantedFor() {
        return LeaveGrantedFor;
    }

    public void setLeaveGrantedFor(String leaveGrantedFor) {
        LeaveGrantedFor = leaveGrantedFor;
    }

    public String getLeaveGrantedTime() {
        return LeaveGrantedTime;
    }

    public void setLeaveGrantedTime(String leaveGrantedTime) {
        LeaveGrantedTime = leaveGrantedTime;
    }
}
