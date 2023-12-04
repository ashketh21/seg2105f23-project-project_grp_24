package com.example.grimpeurscyclingclubgcc;

import java.util.ArrayList;
import java.util.List;

public class Participant extends User {
    private List<String> enrolledEvents = new ArrayList<>();

    public Participant(){

    }

    public List<String> getEnrolledEvents() {return this.enrolledEvents;}

    public void setEnrolledEvents(List<String> enrolledEvents){this.enrolledEvents = enrolledEvents;}

    public void addEnrolledEvent(String enrolledEvent){
        this.enrolledEvents.add(enrolledEvent);
    }

    public void removeEvent(Event enrolledEvent){
        this.enrolledEvents.remove(enrolledEvent);
    }

}
