package br.edu.ifpb.entidades;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.spi.scanning.Scanner;

public class ClienteJava {

private static int HTTP_COD_SUCESSO = 200;

  public static void main(String[] args) {
    Jogador pessoa = new Jogador();
    String moviment = "Bus";
    
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
        Jogador user=gson.fromJson(output,Jogador.class);	

        System.out.println(user.getJogada());
        System.out.println(user.getId());

  } catch (Exception e) {

        e.printStackTrace();
  }
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
    
  }
}