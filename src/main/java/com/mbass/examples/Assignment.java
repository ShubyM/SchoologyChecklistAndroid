package com.mbass.examples;
import java.util.Date;

public class Assignment {

    private String title;
    private Date dueDate;
    private String ID;
    private boolean completed;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueData) {
        this.dueDate = dueDate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return String.format("{Title : %s, Date : %s, ID: %s, Status : %s}",
                title, "placeholder", ID, completed);
    }
}
