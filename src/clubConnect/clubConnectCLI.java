package clubConnect;

import clubConnect.dao.*;
import clubConnect.model.*;
import java.util.*;

public class clubConnectCLI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentDAO studentDAO = new StudentDAO();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Club Connect =====");
            System.out.println("1. Register Student");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> registerStudent();
                case 2 -> loginStudent();
                case 3 -> System.exit(0);
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void registerStudent() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter interests (comma-separated): ");
        String[] interestArray = scanner.nextLine().split(",");
        List<String> interests = new ArrayList<>();
        for (String interest : interestArray) {
            interests.add(interest.trim());
        }

        Student student = new Student(name, email, password, interests);
        studentDAO.insertStudent(student);
    }

    private static void loginStudent() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        boolean valid = studentDAO.validateLogin(email, password);
        if (valid) {
            Student student = studentDAO.getStudentByEmail(email);
            System.out.println("\n--- Welcome, " + student.getName() + " ---");

            while (true) {
                System.out.println("\n1. View Profile");
                System.out.println("2. Update Interests");
                System.out.println("3. View All Events");
                System.out.println("4. View Matching Events");
                System.out.println("5. Register for an Event");
                System.out.println("6. View Registered Events");
                System.out.println("7. Cancel RSVP");
                System.out.println("8. Logout");
                System.out.print("Choose option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> System.out.println(student);
                    case 2 -> updateInterests(student);
                    case 3 -> viewAllEvents();
                    case 4 -> viewMatchingEvents(student);
                    case 5 -> registerForEvent(student);
                    case 6 -> viewRegisteredEvents(student);
                    case 7 -> cancelRSVP(student);
                    case 8 -> { return; }
                    default -> System.out.println("Invalid option.");
                }

            }

        } else {
            System.out.println("❌ Invalid credentials.");
        }
    }

    private static void updateInterests(Student student) {
        System.out.print("Enter new interests (comma-separated): ");
        String[] newInterests = scanner.nextLine().split(",");
        List<String> interestList = new ArrayList<>();
        for (String interest : newInterests) interestList.add(interest.trim());

        InterestDAO interestDAO = new InterestDAO();
        interestDAO.updateStudentInterests(student.getStudentId(), interestList);

        student.setInterests(interestList);
        System.out.println("✅ Interests updated!");
    }

    private static void viewAllEvents() {
        EventDAO eventDAO = new EventDAO();
        List<Event> events = eventDAO.getAllEvents();

        if (events.isEmpty()) {
            System.out.println("No events found.");
        } else {
            for (Event e : events) System.out.println(e);
        }
    }

    private static void viewMatchingEvents(Student student) {
        EventDAO eventDAO = new EventDAO();
        List<Event> events = eventDAO.getEventsMatchingTags(student.getInterests());

        if (events.isEmpty()) {
            System.out.println("No matching events found.");
        } else {
            System.out.println("--- Events Matching Your Interests ---");
            for (Event e : events) System.out.println(e);
        }
    }

    private static void registerForEvent(Student student) {
        System.out.print("Enter Event ID to register: ");
        int eventId = scanner.nextInt();
        scanner.nextLine();

        RSVPDAO rsvpDAO = new RSVPDAO();
        rsvpDAO.register(student.getStudentId(), eventId);
    }

    private static void viewRegisteredEvents(Student student) {
        RSVPDAO rsvpDAO = new RSVPDAO();
        EventDAO eventDAO = new EventDAO(); // Assume this exists

        List<Integer> eventIds = rsvpDAO.getRegisteredEventIds(student.getStudentId());

        if (eventIds.isEmpty()) {
            System.out.println("📭 You have not registered for any events.");
            return;
        }

        System.out.println("\n--- Your Registered Events ---");
        for (int i = 0; i < eventIds.size(); i++) {
            int eventId = eventIds.get(i);
            Event event = eventDAO.getEventById(eventId);
            System.out.println((i + 1) + ". " + event);
        }
    }

    private static void cancelRSVP(Student student) {
        RSVPDAO rsvpDAO = new RSVPDAO();
        EventDAO eventDAO = new EventDAO(); // Assume this exists

        List<Integer> eventIds = rsvpDAO.getRegisteredEventIds(student.getStudentId());

        if (eventIds.isEmpty()) {
            System.out.println("❌ No registrations to cancel.");
            return;
        }

        System.out.println("\n--- Your Registered Events ---");
        for (int i = 0; i < eventIds.size(); i++) {
            Event event = eventDAO.getEventById(eventIds.get(i));
            System.out.println((i + 1) + ". " + event);
        }

        System.out.print("Enter the number of the event to cancel (or 0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > eventIds.size()) {
            System.out.println("❌ Invalid choice.");
            return;
        }

        int eventIdToCancel = eventIds.get(choice - 1);
        rsvpDAO.cancelRSVP(student.getStudentId(), eventIdToCancel);
    }



}
