package clubConnect.dao;

import clubConnect.model.Event;
import clubConnect.util.DBConnection;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;


public class EventDAO {

    // 1. Create a new event
    public boolean createEvent(Event event) {
        String sql = "INSERT INTO events (clubID, title, date, time, venue, capacity, regFee, tags) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, event.getClubID());
            stmt.setString(2, event.getTitle());
            stmt.setDate(3, Date.valueOf(event.getDate()));
            stmt.setTime(4, Time.valueOf(event.getTime()));
            stmt.setString(5, event.getVenue());
            stmt.setInt(6, event.getCapacity());
            stmt.setDouble(7, event.getRegFee());
            stmt.setString(8, event.getTagString());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    event.setEventID(keys.getInt(1)); // set auto-incremented ID
                }
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error creating event: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // 2. Get event by ID
    public Event getEventById(int eventID) {
        String sql = "SELECT * FROM events WHERE eventID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToEvent(rs);
            }

        } catch (Exception e) {
            System.out.println("Error retrieving event: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // 3. Get all events
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events ORDER BY date, time";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                events.add(mapRowToEvent(rs));
            }

        } catch (Exception e) {
            System.out.println("Error fetching events: " + e.getMessage());
            e.printStackTrace();
        }
        return events;
    }

    // 4. Get events by tag
    public List<Event> getEventsByTag(String tag) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE tags LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + tag + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                events.add(mapRowToEvent(rs));
            }

        } catch (Exception e) {
            System.out.println("Error fetching events by tag: " + e.getMessage());
            e.printStackTrace();
        }
        return events;
    }

    // 5. Delete an event
    public boolean deleteEvent(int eventID) {
        String sql = "DELETE FROM events WHERE eventID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventID);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error deleting event: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // 6. Update event details
    public boolean updateEvent(Event event) {
        String sql = "UPDATE events SET title = ?, date = ?, time = ?, venue = ?, " +
                "capacity = ?, regFee = ?, tags = ? WHERE eventID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, event.getTitle());
            stmt.setDate(2, Date.valueOf(event.getDate()));
            stmt.setTime(3, Time.valueOf(event.getTime()));
            stmt.setString(4, event.getVenue());
            stmt.setInt(5, event.getCapacity());
            stmt.setDouble(6, event.getRegFee());
            stmt.setString(7, event.getTagString());
            stmt.setInt(8, event.getEventID());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error updating event: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // 🔁 Private helper: Convert DB row to Event object
    private Event mapRowToEvent(ResultSet rs) throws SQLException {
        int eventID = rs.getInt("eventID");
        int clubID = rs.getInt("clubID");
        String title = rs.getString("title");
        LocalDate date = rs.getDate("date").toLocalDate();
        LocalTime time = rs.getTime("time").toLocalTime();
        String venue = rs.getString("venue");
        int capacity = rs.getInt("capacity");
        double regFee = rs.getDouble("regFee");
        String tagString = rs.getString("tags");
        List<String> tags = Event.parseTags(tagString);

        return new Event(eventID, clubID, title, date, time, venue, capacity, regFee, tags);
    }
}
