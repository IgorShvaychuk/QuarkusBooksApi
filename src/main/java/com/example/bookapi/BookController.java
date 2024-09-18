package com.example.bookapi;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookController {
    @Inject
    BookService service;

    @GET
    public List<Book> getAllBooks() {
        return service.listAllBooks();
    }

    @GET
    @Path("/{id}")
    public Book getBookById(@PathParam("id") Long id) {
        return service.getBookById(id);
    }

    @POST
    public Response createBook(@Valid Book book) {
        try {
            Book createdBook = service.addBook(book);
            return Response.ok(createdBook).status(Response.Status.CREATED).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") Long id, @Valid Book book) {
        try {
            Book updatedBook = service.updateBook(id, book);
            if (updatedBook == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(updatedBook).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        service.deleteBook(id);
        return Response.noContent().build();
    }
}
