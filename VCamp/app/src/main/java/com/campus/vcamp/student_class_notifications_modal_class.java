package com.campus.vcamp;

public class student_class_notifications_modal_class {

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    private String data, name, time, dataType;

    public student_class_notifications_modal_class() {}

    public student_class_notifications_modal_class(String data, String name, String time, String dataType) {
        this.data = data;
        this.name = name;
        this.time = time;
        this.dataType = dataType;
    }

}
