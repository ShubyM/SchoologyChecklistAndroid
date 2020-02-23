package com.mbass.examples;


import java.util.List;

public class Course {
    public String ID;
    public String name;
//    public List<String> assignments;
//
//    public List<String> getAssignments() {
//        return assignments;
//    }
//
//    public void setAssignments(List<String> assignments) {
//        this.assignments = assignments;
//    }

    public String assignments;

    public String getAssignments() {
        return assignments;
    }

    public void setAssignments(String assignments) {
        this.assignments = assignments;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
