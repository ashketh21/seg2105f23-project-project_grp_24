package com.example.grimpeurscyclingclubgcc;

import java.util.ArrayList;
import java.util.List;

public class Event {
        private String eventName;
        private String minimumAge;
        private String description;

        private String region;
        private String pace;

        private String fee;

        private String difficulty;
        private String participants;

        private String date;

        private Double distance;
        private Double elevation;
        private String landmarks;

        private String clubOwnerName;

        private List<Participant> participantList = new ArrayList<>();


        public Event() {
            // Default constructor required for Firebase
        }

        public Event(String eventName, String description){
            this.eventName = eventName;
            this.description = description;
        }

        public Event(String eventName, String minimumAge, String fee, String description, String difficulty,String region, String pace, String participants) {
            this.clubOwnerName = "";
            this.eventName = eventName;
            this.minimumAge = minimumAge;
            this.fee = fee;
            this.description = description;
            this.region = region;
            this.pace = pace;
            this.difficulty = difficulty;
            this.participants = participants;
            this.date = "";
            this.distance = 0.0;
            this.elevation = 0.0;
            this.landmarks = "";
        }

    public Event(String clubOwnerName,String eventName, String minimumAge, String fee, String description, String difficulty,String region, String pace, String participants, String date, Double distance,Double elevation, String landmark) {
        this.clubOwnerName = clubOwnerName;
            this.eventName = eventName;
        this.minimumAge = minimumAge;
        this.fee = fee;
        this.description = description;
        this.region = region;
        this.pace = pace;
        this.difficulty = difficulty;
        this.participants = participants;
        this.date = date;
        this.distance = distance;
        this.elevation = elevation;
        this.landmarks = landmark;
    }


    public String getClubOwnerName(){return this.clubOwnerName;}

    public void setClubOwnerName(String clubOwnerName){
            this.clubOwnerName = clubOwnerName;
    }

    public List<Participant> getParticipantList(){return this.participantList;}

    public void setParticipantList(List<Participant> participantList){
            this.participantList = participantList;
    }
    public void addParticipants(Participant participant){
            this.participantList.add(participant);
    }



    public Double getDistance() {
        return distance;
    }

    // Setter for distance
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    // Getter for elevation
    public Double getElevation() {
        return elevation;
    }

    // Setter for elevation
    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    // Getter for landmarks
    public String getLandmarks() {
        return landmarks;
    }

    // Setter for landmarks
    public void setLandmarks(String landmarks) {
        this.landmarks = landmarks;
    }
        public void setDate(String date) {this.date = date;}

        public String getDate() {return this.date;}
        public void setRegion(String region) {this.region = region;}

        public String getRegion() {return this.region;}
        public void setDifficulty(String difficulty) {this.difficulty = difficulty;}
        public String getDifficulty() {return difficulty;}

        public void setFee(String fee) {this.fee = fee;}
        public String getFee() { return fee;}
        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getMinimumAge() {
            return minimumAge;
        }

        public void setMinimumAge(String minimumAge) {
            this.minimumAge = minimumAge;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPace() {
            return pace;
        }

        public void setPace(String pace) {
            this.pace = pace;
        }

        public String getParticipants() {
            return participants;
        }

        public void setParticipants(String participants) {
            this.participants = participants;
        }
}
