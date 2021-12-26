package com.example.outlab9.ui.dataModel;

public class DataModel {
    String name;
    String description;
    String deadlineDate;
    String deadlineTime;
    String course;
    Boolean isExpanded;
    int type;

    public DataModel(String name, String description, String deadlineDate, String deadlineTime, String course, int type) {
        this.name = name;
        this.description = description;
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
        this.course = course;
        this.type = type;
        this.isExpanded = false;
    }

    public DataModel(String name, String description, String deadlineDate, String course) {
        this.name = name;
        this.description = description;
        this.deadlineDate = deadlineDate;
        this.course = course;
        isExpanded = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadline) {
        this.deadlineDate = deadline;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Boolean getExpanded() {
        return isExpanded;
    }

    public void setExpanded(Boolean expanded) {
        isExpanded = expanded;
    }

    public String getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
