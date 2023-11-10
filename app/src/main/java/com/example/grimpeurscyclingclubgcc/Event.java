package com.example.grimpeurscyclingclubgcc;

public class Event {
        private String eventName;
        private String minimumAge;
        private String description;

        private String region;
        private String pace;

        private String fee;

        private String difficulty;
        private String participants;

        public Event() {
            // Default constructor required for Firebase
        }

        public Event(String eventName, String description){
            this.eventName = eventName;
            this.description = description;
        }

        public Event(String eventName, String minimumAge, String fee, String description, String difficulty,String region, String pace, String participants) {
            this.eventName = eventName;
            this.minimumAge = minimumAge;
            this.fee = fee;
            this.description = description;
            this.region = region;
            this.pace = pace;
            this.difficulty = difficulty;
            this.participants = participants;
        }

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
