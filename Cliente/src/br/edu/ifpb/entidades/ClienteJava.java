package br.edu.ifpb.entidades;

import br.edu.ifpb.entidades.Jogador;
import br.edu.ifpb.entidades.Maps;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.spi.scanning.Scanner;

/**
 * 
 * @author Leandro Albuquerque
 */

public class ClienteJava {

private static int HTTP_COD_SUCESSO = 200;
    /**
     *
     * @return
     */
    public Maps mapas(){
        Maps mp = null;
        try {
            //Recebendo do servidor
            Client client = Client.create();

            WebResource webResource = client
               .resource("http://localhost:8080/InterpoolConect/webresources/InterpoolConect/conect");

            ClientResponse response = webResource.accept("application/json")
               .get(ClientResponse.class);

            if (response.getStatus() != HTTP_COD_SUCESSO) {
               throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
            }
            String output = response.getEntity(String.class);

            Gson gson=new Gson();
            mp =gson.fromJson(output,Maps.class);	

      } catch (Exception e) {

            e.printStackTrace();
      }
        return mp;
    }
    /*
    try {
    //Enviando para o servidor
          Client client = Client.create();
          pessoa.setJogada(moviment);

          WebResource webResource = client
             .resource("http://localhost:8080/InterpoolConect/webresources/interpoolConect/jogar");

          //Convertendo ubjeto pra json, ultilizando a bibloteca d google
          Gson gson = new Gson();
          String input = gson.toJson(pessoa);

          ClientResponse response = webResource.type("application/json")
             .post(ClientResponse.class, input);

          if (response.getStatus() != 201) {
                  throw new RuntimeException("Failed : HTTP error code : "
                       + response.getStatus());
          }

          System.out.println("Output from Server .... \n");
          String output = response.getEntity(String.class);
          System.out.println(output);

    } catch (Exception e) {
          e.printStackTrace();
    }
   */
  }