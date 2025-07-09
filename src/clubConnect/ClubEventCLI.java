package clubConnect;
import clubConnect.dao.EventDAO;
import clubConnect.model.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClubEventCLI {

    private static Scanner sc = new Scanner(System.in);
    private static EventDAO eventDAO = new EventDAO();

    public static void showClubEventMenu(int clubId) {
        while (true) {
            System.out.println("\n--- Club Event Management ---");
            System.out.println("1. View My Events");
            System.out.println("2. Create Event");
            System.out.println("3. Edit Event");
            System.out.println("4. Delete Event");
            System.out.println("5. Back");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> viewClubEvents(clubId);
                case 2 -> createEvent(clubId);
                case 3 -> editEvent(clubId);
                case 4 -> deleteEvent(clubId);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    private static void viewClubEvents(int clubId) {
        List<Event> events = eventDAO.getAllEvents();
        boolean found = false;
        for (Event e : events) {
            if (e.getClubID() == clubId) {
                System.out.println("\n" + e);
                found = true;
            }
        }
        if (!found) System.out.println("No events found for your club.");
    }
    private static void createEvent(int clubId) {
        System.out.print("Title: ");
        String title = sc.nextLine();

        System.out.print("Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(sc.nextLine());

        System.out.print("Time (HH:MM): ");
        LocalTime time = LocalTime.parse(sc.nextLine());

        System.out.print("Venue: ");
        String venue = sc.nextLine();

        System.out.print("Capacity: ");
        int capacity = Integer.parseInt(sc.nextLine());

        System.out.print("Registration Fee: ");
        double regFee = Double.parseDouble(sc.nextLine());

        System.out.print("Tags (comma-separated): ");
        List<String> tags = Arrays.asList(sc.nextLine().split("\\s*,\\s*"));

        Event event = new Event(clubId, title, date, time, venue, capacity, regFee, tags);

        if (eventDAO.createEvent(event)) {
            System.out.println("Event created successfully with ID: " + event.getEventID());
        } else {
            System.out.println("Failed to create event.");
        }
    }
    private static void editEvent(int clubId) {
        System.out.print("Enter Event ID to edit: ");
        int eventId = Integer.parseInt(sc.nextLine());

        Event event = eventDAO.getEventById(eventId);

        if (event == null || event.getClubID() != clubId) {
            System.out.println("Event not found or doesn't belong to your club.");
            return;
        }

        System.out.print("New Title (" + event.getTitle() + "): ");
        String title = sc.nextLine();
        if (!title.isEmpty()) event.setTitle(title);

        System.out.print("New Date (" + event.getDate() + "): ");
        String dateStr = sc.nextLine();
        if (!dateStr.isEmpty()) event.setDate(LocalDate.parse(dateStr));

        System.out.print("New Time (" + event.getTime() + "): ");
        String timeStr = sc.nextLine();
        if (!timeStr.isEmpty()) event.setTime(LocalTime.parse(timeStr));

        System.out.print("New Venue (" + event.getVenue() + "): ");
        String venue = sc.nextLine();
        if (!venue.isEmpty()) event.setVenue(venue);

        System.out.print("New Capacity (" + event.getCapacity() + "): ");
        String capacityStr = sc.nextLine();
        if (!capacityStr.isEmpty()) event.setCapacity(Integer.parseInt(capacityStr));

        System.out.print("New Reg Fee (" + event.getRegFee() + "): ");
        String regStr = sc.nextLine();
        if (!regStr.isEmpty()) event.setRegFee(Double.parseDouble(regStr));

        System.out.print("New Tags (comma-separated): ");
        String tagStr = sc.nextLine();
        if (!tagStr.isEmpty()) event.setTags(Arrays.asList(tagStr.split("\\s*,\\s*")));

        if (eventDAO.updateEvent(event)) {
            System.out.println("Event updated successfully.");
        } else {
            System.out.println("Failed to update event.");
        }
    }
    private static void deleteEvent(int clubId) {
        System.out.print("Enter Event ID to delete: ");
        int eventId = Integer.parseInt(sc.nextLine());

        Event event = eventDAO.getEventById(eventId);

        if (event == null || event.getClubID() != clubId) {
            System.out.println("Event not found or doesn't belong to your club.");
            return;
        }

        if (eventDAO.deleteEvent(eventId)) {
            System.out.println("Event deleted.");
        } else {
            System.out.println("Failed to delete event.");
        }
    }}



