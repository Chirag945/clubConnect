package clubConnect.recommendation;

import clubConnect.dao.EventDAO;
import clubConnect.model.Event;
import clubConnect.model.Student;
import java.time.LocalDate;
import java.util.*;

public class TagBasedRecommendation implements RecommendationStrategy {

    private EventDAO eventDAO;

    public TagBasedRecommendation() {
        this.eventDAO = new EventDAO();
    }

    @Override
    public List<Event> recommend(Student student) {
        Set<Event> recommended = new HashSet<>();
        for (String tag : student.getInterests()) {
            List<Event> events = eventDAO.getEventsByTag(tag);
            for (Event e : events) {
                if (e.getDate().isAfter(LocalDate.now())) {
                    recommended.add(e); // exclude past events
                }
            }
        }
        List<Event> result = new ArrayList<>(recommended);
        result.sort(Comparator.comparing(Event::getDate).thenComparing(Event::getTime));
        return result;
    }
}
