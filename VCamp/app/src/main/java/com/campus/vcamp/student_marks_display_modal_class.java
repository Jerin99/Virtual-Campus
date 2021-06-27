package com.campus.vcamp;

public class student_marks_display_modal_class {
    private String mark1, mark2, mark3, mark4, mark5, mark6, sub1, sub2, sub3, sub4, sub5, sub6, total, grade, Semester;

    student_marks_display_modal_class() {}

    student_marks_display_modal_class(String mark1, String mark2, String mark3, String mark4, String mark5, String mark6, String grade,
                                      String sub1, String sub2, String sub3, String sub4, String sub5, String sub6, String total, String Semester) {
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.mark3 = mark3;
        this.mark4 = mark4;
        this.mark5 = mark5;
        this.mark6 = mark6;
        this.sub1 = sub1;
        this.sub2 = sub2;
        this.sub3 = sub3;
        this.sub4 = sub4;
        this.sub5 = sub5;
        this.sub6 = sub6;
        this.total = total;
        this.grade = grade;
        this.Semester = Semester;
    }

    public String getMark1() {
        return mark1;
    }

    public void setMark1(String mark1) {
        this.mark1 = mark1;
    }

    public String getMark2() {
        return mark2;
    }

    public void setMark2(String mark2) {
        this.mark2 = mark2;
    }

    public String getMark3() {
        return mark3;
    }

    public void setMark3(String mark3) {
        this.mark3 = mark3;
    }

    public String getMark4() {
        return mark4;
    }

    public void setMark4(String mark4) {
        this.mark4 = mark4;
    }

    public String getMark5() {
        return mark5;
    }

    public void setMark5(String mark5) {
        this.mark5 = mark5;
    }

    public String getMark6() {
        return mark6;
    }

    public void setMark6(String mark6) {
        this.mark6 = mark6;
    }

    public String getSub1() {
        return sub1;
    }

    public void setSub1(String sub1) {
        this.sub1 = sub1;
    }

    public String getSub2() {
        return sub2;
    }

    public void setSub2(String sub2) {
        this.sub2 = sub2;
    }

    public String getSub3() {
        return sub3;
    }

    public void setSub3(String sub3) {
        this.sub3 = sub3;
    }

    public String getSub4() {
        return sub4;
    }

    public void setSub4(String sub4) {
        this.sub4 = sub4;
    }

    public String getSub5() {
        return sub5;
    }

    public void setSub5(String sub5) {
        this.sub5 = sub5;
    }

    public String getSub6() {
        return sub6;
    }

    public void setSub6(String sub6) {
        this.sub6 = sub6;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }
}
