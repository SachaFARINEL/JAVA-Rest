package fr.lpdlis.rest.todo.client;

import fr.lpdlis.rest.todo.model.Todo;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;

import java.net.URI;

public class ClientTest {
    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget service = client.target(getBaseURI());
// Créer un todo
        Todo todo = new Todo("3", "Blabla");
        Response response = service.path("rest").
                path("todos").
                path(todo.getId()).
                request(MediaType.APPLICATION_XML).
                put(Entity.entity(todo, MediaType.APPLICATION_XML), Response.class);
// Code de retour 201 == ressource créer
        System.out.println(response.getStatus());
// Obtient les Todos
        System.out.println(service.path("rest").path("todos").request().accept(MediaType.TEXT_XML).get(String.class));
// Pour JSON
//System.out.println(service.path("rest").path("todos").request().
//accept(MediaType.APPLICATION_JSON).get(String.class));
// XML pour l'application
        System.out.println(service.path("rest").path("todos").request()
                .accept(MediaType.APPLICATION_XML).get(String.class));
//Todo avec id 1
        Response checkDelete = service.path("rest").
                path("todos/1").
                request().
                accept(MediaType.APPLICATION_XML).
                get();
//Supprime Todo avec id 1
        service.path("rest").path("todos/1").request().delete();
//Obtient tous les Todos (id 1 doit être supprimer)
        System.out.println(service.path("rest").path("todos").request()
                .accept(MediaType.APPLICATION_XML).get(String.class));
//Creation d'un Todo
        Form form = new Form();
        form.param("id", "4");
        form.param("summary", "Demonstration d'un client lib pour les formulaires");
        response = service.path("rest").
                path("todos").
                request().
                post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
        System.out.println("Réponse Formulaire " + response.getStatus());
//Obtient tous les todos, id 4 doit être créer
        System.out.println(service.path("rest").path("todos").request()
                .accept(MediaType.APPLICATION_XML).get(String.class));
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/ue5.ws.todo").build();
    }
}