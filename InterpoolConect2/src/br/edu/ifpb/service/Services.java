package br.edu.ifpb.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.edu.ifpb.entidades.Jogador;



@Path("/interpool")
public class Services {
	Jogador jogador=new Jogador();
	int id=1;
	Maps mapa = new Maps(200,20);
	
	/*
    @Path("/conecta")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response listar() {

    	return Response.ok(id).build();
    }
    */
    
    @Path("/jogada")
    @GET
    @Produces("application/json")  
    public Jogador getJson() {

    	System.out.println("id "+id);
    	jogador.setId(id);
    	id+=1;//alterando o id para limitar o acesso
		
		return jogador;
    }
	@POST
	@Path("/enviar")
	@Consumes("application/json")
	// @Produces("application/json")
	public Response cadastrarUsuarios(Jogador user) {
		
		jogador = user;
		
		String result = "Jogador recebido..  " + user.getId() +" jogada "+user.getJogada();
		return Response.status(201).entity(result).build(); 

	}
}
