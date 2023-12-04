package com.example.grimpeurscyclingclubgcc;

import java.util.ArrayList;
import java.util.List;


    public class ClubOwner extends User {

        private String socialMediaLink;

        private String contactPersonName;

        private String phoneNumber;

        private List<Event> events = new ArrayList<>();

//        private List<Participant> participants = new ArrayList<>();

        public ClubOwner(){

        }
        public List<Event> getEvents() {
            return events;
        }
        public void setEvents(List<Event> events) {
            this.events = events;
        }

        public void addEvent(Event event){

            this.events.add(event);
        }

        public void removeEvent(Event event){
            this.events.remove(event);
        }

//        public void setParticipants(List<Participant> participants){ this.participants = participants;}
//public List<Participant> getParticipants(){return this.participants;}

        public String getSocialMediaLink() {
            return socialMediaLink;
        }

        public void setSocialMediaLink(String socialMediaLink) {
            this.socialMediaLink = socialMediaLink;
        }

        public String getContactPersonName() {
            return contactPersonName;
        }

        public void setContactPersonName(String contactPersonName) {
            this.contactPersonName = contactPersonName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

    }
