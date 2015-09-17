package com.rest.web.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rest.dao.NoteDao;
import com.rest.model.Note;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@Path("/api/notes")
public class RestfulService {
	
	public RestfulService(){}

    public static final String RESOURCE_URL = "api/notes";

    @Autowired
    NoteDao dao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> getNotes() {
        List<Note> notes = dao.findAll();
        return notes;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Note getNote(@PathParam("id") Integer id) {
    	Note note = dao.findById(id);
        return note;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(Note note, @Context UriInfo uriInfo) {
    	Long id = dao.insert(note);
        URI createdURI = URI.create(uriInfo.getBaseUri().toString() + RESOURCE_URL + "/" + id);
        note.setId(id);
        return Response
                .status(Status.CREATED)
                .contentLocation(createdURI)
                .entity(note)
                .build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/content") //you need this or else you get ambiguous path with getNotes()
    public List<Note> getNotesByContent(@QueryParam("content") String content) {
    	List<Note> notes = dao.findByContent(content);
        return notes;
    }

	public void setDao(NoteDao noteDao) {
		dao = noteDao;
	}

}