package com.example.grimpeurscyclingclubgcc;

public class Event {
        private String eventName;
        private String minimumAge;
        private String description;
        private String pace;
        private String participants;

        public Event() {
            // Default constructor required for Firebase
        }

        public Event(String eventName, String minimumAge, String description, String pace, String participants) {
            this.eventName = eventName;
            this.minimumAge = minimumAge;
            this.description = description;
            this.pace = pace;
            this.participants = participants;
        }

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
