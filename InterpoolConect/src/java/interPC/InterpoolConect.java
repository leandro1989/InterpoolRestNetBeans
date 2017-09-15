/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interPC;

import com.google.gson.Gson;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Leandro Albuquerque
 */
@Path("InterpoolConect")
public class InterpoolConect {

    Jogador jogador=new Jogador();
    ArrayList<Integer> ids = new ArrayList<Integer>();
    int id = 0;
    Maps mapa = new Maps(200,20);
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of InterpoolConect
     */
    public InterpoolConect() {
        this.ids.add(5);
        this.ids.add(2);
        this.ids.add(3);
        this.ids.add(4);
        this.ids.add(5);
        this.ids.add(6);
    }

    /**
     * Retrieves representation of an instance of interPC.InterpoolConect
     * @return an instance of java.lang.String
     */
    @Path("conect")
    @GET
    @Produces("application/json")
    public String getMovimentacao() {
    	this.jogador.setJogada("bete");
        this.jogador.setId(ids.get(this.id));
        this.ids.remove(this.id);
        Gson jog  = new Gson();
        return jog.toJson(jogador);
    }

    /**
     * PUT method for updating or creating an instance of InterpoolConect
     * @param content representation for the resource
     */
   
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
//http://localhost:8080/InterpoolConect/webresources/interpoolConect
