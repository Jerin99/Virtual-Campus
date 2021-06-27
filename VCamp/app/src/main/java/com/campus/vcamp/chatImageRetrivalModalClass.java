package com.campus.vcamp;

public class chatImageRetrivalModalClass {

    String imageUrl, Time, Name, Date;

    public chatImageRetrivalModalClass() {}

    public chatImageRetrivalModalClass(String imageUrl, String Time, String Date, String Name){
        this.Date = Date;
        this.imageUrl = imageUrl;
        this.Name = Name;
        this.Time = Time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
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
}
