package com.origamitecnologia.appmodel.control;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.origamitecnologia.appmodel.model.Movie;
import com.origamitecnologia.appmodel.model.User;

import java.lang.reflect.Type;
import java.util.List;

public class JsonCentral {

    private static JsonCentral instance;
    private Gson gson;

    private JsonCentral() {
        gson = new Gson();
    }

    public static JsonCentral getInstance() {
        if(instance == null) {
            instance = new JsonCentral();
        }
        return instance;
    }

    public List<Movie> parseMovies(String jsonMovies) {
        Type listType = new TypeToken<List<Movie>>(){}.getType();
        return (List<Movie>) gson.fromJson(jsonMovies, listType);
    }

    public List<User> parseUsers(String jsonUsers) {
        Type listType = new TypeToken<List<User>>(){}.getType();
        return (List<User>) gson.fromJson(jsonUsers, listType);
    }
}
