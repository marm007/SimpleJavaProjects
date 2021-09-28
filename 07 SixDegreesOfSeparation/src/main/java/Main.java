import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.graph.SimpleGraph;
import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main {

    static void runSimpleGraphExample() {

        long tStart = System.currentTimeMillis();

        Actor q = new Actor("Boguslaw Linda", "nm0511277");
        // Actor w = new Actor("Keanu Reeves","nm0000206");
        // Actor w = new Actor("Cezary Julski","nm0432374");
        Actor w = new Actor("Katarzyna Figura", "nm0276758");

        Queue<Actor> actorsQueue = new ArrayDeque<>();
        actorsQueue.add(q);

        List<String> allActors = new ArrayList<>();
        List<String> allMovies = new ArrayList<>();

        Graph<Actor, Movie> g = new SimpleGraph<>(Movie.class);

        final boolean[] found = { false };

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();

        while (true) {

            Actor actor = actorsQueue.remove();

            Request request = new Request.Builder().url("https://java.kisim.eu.org/actors/" + actor.getId()).build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response);

                Actor _actor = new ObjectMapper().readValue(response.body().string(), Actor.class);
                System.out.println(_actor.toString());

                Request moviesRequest = new Request.Builder()
                        .url("https://java.kisim.eu.org/actors/" + _actor.getId() + "/movies").build();

                try (Response moviesResponse = client.newCall(moviesRequest).execute()) {
                    if (!moviesResponse.isSuccessful())
                        throw new IOException("Unexpected code " + moviesResponse);

                    JsonFactory factory = new JsonFactory();
                    JsonParser parser = factory.createParser(moviesResponse.body().string());

                    MappingIterator<Movie[]> _movies = new ObjectMapper().readValues(parser, Movie[].class);
                    List<Movie> movies = Arrays.asList(_movies.readAll().get(0));

                    actor.setMovies(movies);

                    for (Movie movie : movies) {

                        if (allMovies.contains(movie.getId())) {
                            System.out.println("CONTAINS : " + movie.toString());
                            return;
                        }

                        allMovies.add(movie.getId());

                        Request movieRequest = new Request.Builder()
                                .url("https://java.kisim.eu.org/movies/" + movie.getId()).build();

                        try (Response movieResponse = client.newCall(movieRequest).execute()) {
                            if (!movieResponse.isSuccessful())
                                throw new IOException("Unexpected code " + movieResponse);

                            String jsonData = movieResponse.body().string();
                            System.out.println(jsonData);
                            JSONObject jsonObject = new JSONObject(jsonData);
                            JSONArray jsonArray = jsonObject.getJSONArray("actors");

                            JsonFactory actorFactory = new JsonFactory();
                            JsonParser actorParser = actorFactory.createParser(jsonArray.toString());

                            MappingIterator<Actor[]> _actors = new ObjectMapper().readValues(actorParser,
                                    Actor[].class);
                            List<Actor> actors = Arrays.asList(_actors.readAll().get(0));

                            for (Actor a : actors) {
                                if (!allActors.contains(a.getId())) {
                                    actorsQueue.add(a);
                                    g.addVertex(a);
                                    allActors.add(a.getId());
                                    System.out.println(a.toString());
                                }
                            }

                            for (int i = 0; i < actors.size(); i++) {
                                for (int j = i + 1; j < actors.size(); j++) {
                                    g.addEdge(actors.get(i), actors.get(j), (Movie) movie.clone());
                                }
                            }

                            if (actors.contains(w)) {
                                found[0] = true;
                                long tEnd = System.currentTimeMillis();
                                long tDelta = tEnd - tStart;
                                double elapsedSeconds = tDelta / 1000.0;

                                System.out.println("\nTime = " + elapsedSeconds);
                                return;
                            }

                            System.out.println("\n\n");
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                            System.err.println(movieRequest.url());
                            System.err.println(e.getCause());
                        }

                        // --------------------------------
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (found[0])
                break;
        }

        BellmanFordShortestPath<Actor, Movie> bfsp = new BellmanFordShortestPath<>(g);

        GraphPath<Actor, Movie> shortestPath = bfsp.getPath(q, w);

        List<Movie> edges = shortestPath.getEdgeList();
        List<Actor> actors = shortestPath.getVertexList();

        for (int i = 0; i < actors.size(); ++i) {
            if (i == actors.size() - 1)
                System.out.print(actors.get(i));
            else
                System.out.print(actors.get(i) + " -> " + edges.get(i).toString() + " -> ");
        }

        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        double elapsedSeconds = tDelta / 1000.0;

        System.out.println("\nTime = " + elapsedSeconds);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runSimpleGraphExample();
            }
        });
    }
}
