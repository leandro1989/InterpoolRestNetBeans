/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.entidades;

/**
 * A classe UiGraph encapsula os dados e os m�todo para criar h� partir da classe
 * Maps um interface gr�fica com o �suario representando o mapa da cidade.
 */

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import handleEvents.HandlerMouseEvents;

/**
 *
 * @author fc.corporation
 */
public class UiGraph extends Application {
    private Circle stationall;
    private final Group GROUPSTATION;
    private final Group GROUPSTREET;
    private final Group GROUPSTATIONBUS;
    private final Group GROUPSTATIONTRAIN;
    private final int RADIUS = 4; //raio do circulo que representa a esta��o
    private static int QUANTIINLINE; // quantidade de esta��es na mesma linha
    private static int QUANTSTATION; // quantidade de esta��es no total
    private Maps maps;
    private HandlerMouseEvents handlerMouseEvents;
    private final Color[] standartColorsOfStation;
    
    private ClienteJava client;
    
    /**
     * O construtor da classe n�o recebe nenhum argumento e instancia os objetos
     * e seta o mapa de acordo com as constantes.
     */
    public UiGraph(){
        this.standartColorsOfStation = new Color[]{Color.BLUE, Color.BLACK, Color.GREEN};
        this.GROUPSTATION = new Group();
        this.GROUPSTREET = new Group();
        this.GROUPSTATIONBUS = new Group();
        this.GROUPSTATIONTRAIN = new Group();
        
        this.client = new ClienteJava();
    }
    /**
     * O m�todo createStation cria os Nodes que s�o circulos que representam as 
     * esta��es de taxi, da� h� partir de uma das matrizes de adjacencias faz as
     * devidas liga��es entre as esta��es de taxi com o m�todo createStreets
     * @param quant quantidade de esta��es
     * @param distancia distancia em pixels entra cada esta��o no eixo x
     * @param quantInLine quantidade de esta��es na mesma linha
     */
    public void createStation(int quant, int distancia, int quantInLine){
        UiGraph.QUANTIINLINE = quantInLine;
        UiGraph.QUANTSTATION = quant;
        //this.maps = new Maps(UiGraph.QUANTSTATION, UiGraph.QUANTIINLINE);
        this.maps = this.client.mapas();
        int j = 1;
        int x = distancia;
        int y = distancia;
        for(int i=0;i < quant;i++){
            this.stationall = new Circle(RADIUS);
            this.stationall.setTranslateX(x);
            this.stationall.setTranslateY(y);
            this.stationall.setOnMouseClicked(new HandlerMouseEvents(
                    distancia, distancia, this.GROUPSTATION, quantInLine,
                    this.standartColorsOfStation, UiGraph.QUANTSTATION, this.RADIUS, this.maps));
            this.createStreets(i, x, y, distancia);
            x += distancia;
            this.GROUPSTATION.getChildren().add(this.stationall);
            if(i == (quantInLine*j)-1){
                y+= distancia;
                x=distancia;
                j++;
            }
        }
    }
    /**
     * O m�todo createStreets recebe quatro argumentos da� baseado na matriz de 
     * adjacencias cria linhas que representam as liga��es entre as esta��es de taxi
     * @param vertce vertce que ser� criado a liga��o
     * @param starts_x come�o da linha (aresta) em pixels no eixo x
     * @param starts_Y come�o da linha (aresta) em pixels no eixo y
     * @param distance distancia entre as esta��es no eixo x em pixels
     */
    public void createStreets(int vertce, int starts_x, int starts_Y, int distance){
        Line tempLine;
        final Color color = Color.RED;
        int[] vertces = this.maps.possibleEges(vertce);
            if(this.maps.thereEdge(vertce, vertces[0])){
                tempLine = new Line(starts_x, starts_Y, starts_x+distance, starts_Y);
                tempLine.setStroke(color);
                this.GROUPSTREET.getChildren().add(tempLine);
            }
            if(this.maps.thereEdge(vertce, vertces[1])){
                tempLine = new Line(starts_x, starts_Y, starts_x, starts_Y + distance);
                tempLine.setStroke(color);
                this.GROUPSTREET.getChildren().add(tempLine);
            }
    }
    /**
     * O m�todo createstationBusTaxi cria as esta��es de �nibus
     * chamando o m�todo genericCreateStation.
     */
    public void createstationBusTaxi(){
        final int distanceBetweenStreets = 3;
        this.genericCreateStation(this.GROUPSTATIONBUS, true, Color.BLUE, distanceBetweenStreets);
    }
    /**
     * O m�todo createStationTrain cria as esta��es de trem chamando o m�todo 
     * genericCreateStation.
     */
    public void createStationTrain(){
        final int distanceBetweenStreets = -3;
        this.genericCreateStation(this.GROUPSTATIONTRAIN, false, Color.GREEN, distanceBetweenStreets);
    }
    /**
     * O m�todo genericCreateStation cria as esta��es de �nibus ou trem de acordo
     * par�metros passados
     * @param genericGroup Group que pode ser o de trem ou de �nibus
     * @param busTrainSelector se True seleciona a matriz de adjacencias que 
     * representa as esta��es de �nibus, caso contr�rio seleciona a matriz de 
     * adjacencias que representa as esta��es de trem
     * @param genericColor cor para as esta��es
     * @param increment incremento para que as ruas criadas entre as esta��es
     * n�o fiquem sobrepostas
     */
    public void genericCreateStation(Group genericGroup, boolean busTrainSelector,
            Color genericColor, int increment){
        Circle tempNodeDestiny, tempNodeFrom;
        ArrayList<Integer> edges;
        Line line;
        for(int i=0;i < UiGraph.QUANTSTATION;i++){
            edges = maps.returnEdge(i, busTrainSelector);
            if(edges.isEmpty()){
                continue;
            }
            else{
                int iterador = 0;
                while(iterador < edges.size()){
                    tempNodeFrom =(Circle) this.GROUPSTATION.getChildren().get(i);
                    tempNodeFrom.setFill(genericColor);
                    tempNodeDestiny =(Circle) this.GROUPSTATION.getChildren().get(edges.get(iterador));
                    tempNodeDestiny.setFill(genericColor);
                    double xCoordDestiny = tempNodeDestiny.getTranslateX();
                    double yCoordDestiny = tempNodeDestiny.getTranslateY();
                    double xCoordFrom = tempNodeFrom.getTranslateX();
                    double yCoordFrom = tempNodeFrom.getTranslateY();
                    line = new Line(xCoordFrom+increment, yCoordFrom+increment,
                            xCoordDestiny+increment, yCoordDestiny+increment);
                    line.setStroke(genericColor);
                    genericGroup.getChildren().add(line);
                    iterador++;
                }
            }
        }
    }
    /**
     * O m�todo start � implementado obrigatoriamente por causa da heran�a
     * que a classe tem com a classe Aplication
     * @param primaryStage n�o fa�o idea do que � isso DATTEBAYO!!
     */
    @Override
    public void start(Stage primaryStage) {
        this.createStation(200, 70, 20);
        Group allGroup = new Group();
        this.createstationBusTaxi();
        this.createStationTrain();
        //allGroup.getChildren().add(this.GROUPSTATION);
        allGroup.getChildren().addAll(this.GROUPSTATION,this.GROUPSTREET, 
                this.GROUPSTATIONBUS, this.GROUPSTATIONTRAIN);
        Scene scene = new Scene(allGroup);
        primaryStage.setTitle("UiGraph");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * @param args the command line arguments
     */
    //public static void main(String[] args) {
    //    launch(args);
    //}
}