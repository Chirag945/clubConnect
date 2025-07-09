package clubConnect.recommendation;

import clubConnect.model.Event;
import clubConnect.model.Student;
import java.util.List;

public class EventRecommendationEngine {
    private RecommendationStrategy strategy;

    public EventRecommendationEngine(RecommendationStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Event> getRecommendations(Student student) {
        return strategy.recommend(student);
    }
}

