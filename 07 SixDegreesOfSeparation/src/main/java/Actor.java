import java.util.List;
import java.util.Objects;

public class Actor {

    private String name;

    private String id;

    private List<Movie> movies;

    public Actor() {
    }

    public Actor(String id) {
        this.id = id;
    }

    public Actor(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Actor{" + "name='" + name + '\'' + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Actor actor = (Actor) o;
        return Objects.equals(name, actor.name) && Objects.equals(id, actor.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, id);
    }
}
