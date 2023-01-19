package fr.lpdlis.rest.todo.resources;

import fr.lpdlis.rest.todo.dao.TodoDao;
import fr.lpdlis.rest.todo.model.Todo;
import jakarta.ws.rs.Produces;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.UriInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/todos")
public class TodosResource {
    // Permet d'ajouter des objets contextuels dans la Classe
// e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    // Retourne la Liste des Todos pour l'utilisateur dans le navigateur
    @GET
    @Produces(MediaType.TEXT_XML)
    public List<Todo> getTodosBrowser() {
        List<Todo> todos = new ArrayList<Todo>();
        todos.addAll(TodoDao.instance.getModel().values());
        return todos;
    }

    // Retourne la Liste des Todos pour l'application
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Todo> getTodos() {
        List<Todo> todos = new ArrayList<Todo>();
        todos.addAll(TodoDao.instance.getModel().values());
        return todos;
    }

    // retourne le nombre de todos
// http://localhost:8080/jersey.todo/rest/todos/count
// pour obtenir le nombre total d'enregistrement
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        int count = TodoDao.instance.getModel().size();
        return String.valueOf(count);
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void newTodo(@FormParam("id") String id,
                        @FormParam("summary") String summary,
                        @FormParam("description") String description,
                        @Context HttpServletResponse servletResponse) throws IOException {
        Todo todo = new Todo(id, summary);
        if (description != null) {
            todo.setDescription(description);
        }
        TodoDao.instance.getModel().put(id, todo);
        servletResponse.sendRedirect("../create_todo.html");
    }

    // Défini que le prochain "path" après "todos" est vu comme un paramètre et passé sous la forme "TodoResources"
// http://localhost:8080/jersey.todo/rest/todos/1
// 1 est vu comme un paramètre "todo" et est passé sousla forme "TodoResource"
    @Path("{todo}")
    public TodoResource getTodo(@PathParam("todo") String id) {
        return new TodoResource(uriInfo, request, id);
    }
}