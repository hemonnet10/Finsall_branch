package com.finsall.dto;

public class NotificationDTO {

    private String notificationText;
    private String timeString;

    public NotificationDTO(String notificationText, String timeString) {
        this.notificationText = notificationText;
        this.timeString = timeString;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }
}
