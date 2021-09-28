import org.jgrapht.graph.DefaultEdge;

import java.util.List;

public class Movie extends DefaultEdge {

    private String title;

    private String id;

    private List<Actor> actors;

    public Movie() {
    }

    public Movie(String title, String id) {
        this.title = title;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Movie{" + "title='" + title + '\'' + '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
