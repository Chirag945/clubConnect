package clubConnect.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class Event {
    private int eventID;
    private int clubID;
    private String title;
    private LocalDate date;
    private LocalTime time;
    private String venue;
    private int capacity;
    private double regFee;
    private List<String> tags;

    // Constructors
    public Event() {}

    public Event(int eventID, int clubID, String title, LocalDate date, LocalTime time,
                 String venue, int capacity, double regFee, List<String> tags) {
        this.eventID = eventID;
        this.clubID = clubID;
        this.title = title;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.capacity = capacity;
        this.regFee = regFee;
        this.tags = tags;
    }

    // Overloaded constructor without eventID (for creation)
    public Event(int clubID, String title, LocalDate date, LocalTime time,
                 String venue, int capacity, double regFee, List<String> tags) {
        this(0, clubID, title, date, time, venue, capacity, regFee, tags);
    }

    // Getters and Setters
    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getRegFee() {
        return regFee;
    }

    public void setRegFee(double regFee) {
        this.regFee = regFee;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    // Utility method to convert List<String> tags to comma-separated string
    public String getTagString() {
        return String.join(",", tags);
    }

    // Utility method to populate tags from a comma-separated DB string
    public static List<String> parseTags(String tagString) {
        if (tagString == null || tagString.trim().isEmpty()) return List.of();
        return Arrays.asList(tagString.split("\\s*,\\s*"));
    }

    @Override
    public String toString() {
        return "Event ID: " + eventID +
                "\nClub ID: " + clubID +
                "\nTitle: " + title +
                "\nDate: " + date +
                "\nTime: " + time +
                "\nVenue: " + venue +
                "\nCapacity: " + capacity +
                "\nRegistration Fee: ₹" + regFee +
                "\nTags: " + tags;
    }
}

