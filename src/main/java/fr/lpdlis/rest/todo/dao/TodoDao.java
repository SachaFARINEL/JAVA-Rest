package fr.lpdlis.rest.todo.dao;

import fr.lpdlis.rest.todo.model.Todo;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;

public enum TodoDao {
    instance;
    private Map<String, Todo> contentProvider = new HashMap<>();

    private TodoDao() {
        Todo todo = new Todo("1", "Learn REST");
        todo.setDescription("Read ");
        contentProvider.put("1", todo);
        todo = new Todo("2", "Do something");
        todo.setDescription("Read complete ");
        contentProvider.put("2", todo);
    }

    public Map<String, Todo> getModel() {
        return contentProvider;
    }
}