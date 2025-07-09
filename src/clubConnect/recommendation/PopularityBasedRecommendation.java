package clubConnect.recommendation;
import clubConnect.dao.EventDAO;
import clubConnect.dao.RSVPDAO;
import clubConnect.model.Event;
import clubConnect.model.Student;
import java.time.LocalDate;
import java.util.*;

public class PopularityBasedRecommendation implements RecommendationStrategy {

    private EventDAO eventDAO;
    private RSVPDAO rsvpDAO;

    public PopularityBasedRecommendation() {
        this.eventDAO = new EventDAO();
        this.rsvpDAO = new RSVPDAO();
    }

    @Override
    public List<Event> recommend(Student student) {
        List<Event> allEvents = eventDAO.getAllEvents();
        Map<Integer, Integer> rsvpCounts = rsvpDAO.getRsvpCountsForAllEvents();

        List<Event> futureEvents = new ArrayList<>();
        for (Event e : allEvents) {
            if (e.getDate().isAfter(LocalDate.now())) {
                futureEvents.add(e);
            }
        }

        futureEvents.sort((a, b) -> {
            int countA = rsvpCounts.getOrDefault(a.getEventID(), 0);
            int countB = rsvpCounts.getOrDefault(b.getEventID(), 0);
            return Integer.compare(countB, countA); // Descending
        });

        return futureEvents;
    }
}

