package clubConnect.recommendation;
import clubConnect.model.Student;
import clubConnect.model.Event;
import java.util.List;
public interface RecommendationStrategy {
    List<Event> recommend(Student student);
}
