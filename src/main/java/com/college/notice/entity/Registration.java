package com.college.notice.entity;

import java.time.LocalDateTime;
import java.util.Map;

public class Registration {
    private String userEmail;
    private RegistrationStatus status;
    private LocalDateTime registrationDate;
    private boolean attended;
//    private Map<String, String> customFields;

    public enum RegistrationStatus {
        PENDING, CONFIRMED, CANCELLED
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

//    public Map<String, String> getCustomFields() {
//        return customFields;
//    }
//
//    public void setCustomFields(Map<String, String> customFields) {
//        this.customFields = customFields;
//    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }
}