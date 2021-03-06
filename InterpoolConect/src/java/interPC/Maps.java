/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interPC;

/**
 *
 * @author Leandro
 */
import java.util.ArrayList;
import java.util.Random;

public class Maps {
    private int[][] adjacenciesMatrix;
    private int[][] stationBusTaxi;
    private int[][] stationTrainBus;
    private ArrayList<ArrayList<Integer>> pathBusStation;
    private ArrayList<ArrayList<Integer>> pathTrainstation;
    private final Random WEIGHT;
    private final Random COORD;
    private final int[] LIMITEEDGE;
    private static final int LIMITEWEIGHT = 9;
    private final int SIZE;
    private final int quantInLine;
    private int iterador;
    
    /**
     * O contrutor da classe recebe dois argumentos, daÃ­ instancia os objetos
     * e seta algumas constantes
     * @param tamanho tamanho das matrizes de adjacencias quadradas, ou seja, NxN
     * @param quantInLine quantidade de nÃ³s da mesma linha
     */
    public Maps(int tamanho, int quantInLine){
        this.LIMITEEDGE = new int[]{1,tamanho};
        this.iterador = 1;
        this.quantInLine = quantInLine;
        this.adjacenciesMatrix = new int[tamanho][tamanho];
        this.stationBusTaxi = new int[tamanho][tamanho];
        this.stationTrainBus = new int[tamanho][tamanho];
        this.pathBusStation = new ArrayList<>();
        this.pathTrainstation = new ArrayList<>();
        this.WEIGHT = new Random();
        this.COORD = new Random();
        this.SIZE = adjacenciesMatrix.length;
        this.setAdjacencieMatrix(quantInLine);
    }
    /**
     * O mÃ©todo setAdjacencieMatrix inicializa todas as matrizes de adjacencias
     * @param quantInLine quantidade de nÃ³s na mesma linha
     */
    private void setAdjacencieMatrix(int quantInLine){
        for(int i=0;i < this.adjacenciesMatrix.length;i++){
            int j = 0;
            int[] edges = this.coordGenerator(i, quantInLine);
            while(j < edges.length){
                if(edges[j] == 0)
                    break;
                try{
                    this.adjacenciesMatrix[i][edges[j]] = 1+this.WEIGHT.nextInt(
                            Maps.LIMITEWEIGHT);
                    j++;
                }catch(IndexOutOfBoundsException ioe){
                    break;
                }
            }
        }
        this.generatorBusTaxiStation();
        this.generatorTrainTaxiStation();
    }
    /**
     * O mÃ©todo coordGenerator gera coordenadas para as estaÃ§Ãµes de taxÃ­
     * baseados nos dois parÃ¢metros que recebe, sabendo que sÃ³ gera ligaÃ§Ãµes
     * para um vertce
     * @param vertce vertce do qual as ligaÃ§Ãµes partiram tendo destino baseado em
     * "aleatoriedade"
     * @param quantInLine quantidade de nÃ³s na mesma linha
     * @return retorna um array com as coordenadas dos vertces destinos para as
     * ligaÃ§Ãµes hÃ¡ partir de vertce.
     */
    public int[] coordGenerator(int vertce, int quantInLine){
        final int LENEDGES = 2, MAXRANDOMGENERATED = 3, CASE0 = 0, CASE1 = 1, CASE2 = 2;
        int[] point = new int[LENEDGES];
        int quali = this.WEIGHT.nextInt(MAXRANDOMGENERATED);
        if(vertce == (quantInLine*this.iterador)-1){
            quali = this.WEIGHT.nextInt(MAXRANDOMGENERATED-2);
            this.iterador++;
            switch(quali){
                case CASE0: point[0] = quantInLine+vertce; break;
            }
            return point;
        }
        else{
            if(vertce < this.LIMITEEDGE[1]-quantInLine)
                switch(quali){
                    case CASE0: point[0] = vertce+1; break;
                    case CASE1: point[0] = quantInLine+vertce; break;
                    case CASE2: point[0] = vertce+1; point[1] = quantInLine+vertce; break;
                }
            else{
                quali = this.WEIGHT.nextInt(MAXRANDOMGENERATED-2);
                switch(quali){
                    case CASE0: point[0] = vertce+1; break;
                    case CASE1: point[1] = quantInLine+vertce; break; 
                }
            }
        }
        return point;
        
    }
    /**
     * O mÃ©todo imprime, imprime o contÃ©udo de forma organizada da matriz de a
     * djacencias que representa as estaÃ§Ãµes de taxÃ­.
     */
    public void imprime(){
        for(int i=0;i < this.adjacenciesMatrix.length;i++){
            for(int j=0; j< this.adjacenciesMatrix.length;j++){
                System.out.printf("%d, ", adjacenciesMatrix[i][j]);
                if(j+1 == this.adjacenciesMatrix.length)
                    System.out.printf("\n");
            }
        }
    }
    /**
     * O mÃ©todo imprime2, imprime todo o contÃ©udo de forma organizada da matriz
     * de adjacencias que representa as estaÃ§Ãµes de Ã´nibus.
     */
    public void imprime2(){
        for (int[] stationBusTaxi1 : this.stationBusTaxi) {
            for (int j = 0; j< this.stationBusTaxi.length; j++) {
                System.out.printf("%d, ", stationBusTaxi1[j]);
                if(j+1 == this.stationBusTaxi.length)
                    System.out.printf("\n");
            }
        }
    }
    /**
     * O mÃ©todo dijkstra retorna o menor caminho entre quaisquer dois vertces
     * sÃ³ que este mÃ©todo Ã© para grafos nÃ£o direcionados o que Ã© o caso de qualquer
     * uma das matrizes de adjacencias usadas neste projeto
     * @param u vertice de partida
     * @param v vertice de destino
     * @return retorna um ArrayList com o menor caminho entre os vertces caso este
     * exista
     */
    public ArrayList<Integer> dijkstra(int u, int v){
        ArrayList<Integer> piFiltrade = new ArrayList<>();
        int w = u;
        int infinito = 999999999;
        int[] gama = new int[this.SIZE];
        int[] beta = setBeta(this.SIZE);
        int[] pi = new int[this.SIZE];
        gama[u] = 1;
        beta[u] = 0;
        while(w != v){
            int menorBeta = infinito;
            int r_asteristico = -1;
            ArrayList<Integer> edgesW_R = this.edges(w);
            for(Integer r: edgesW_R){
                r = (int)r;
                int alphaW_R = this.alpha(w, r);
                if(gama[r] == 0 && beta[r] > beta[w]+alphaW_R){
                    beta[r] = beta[w] + alphaW_R;
                    pi[r] = w;
                }
            }
            for(int j=0;j < beta.length;j++){
                if(beta[j] != infinito && gama[j] == 0 && beta[j] <= menorBeta){
                    menorBeta = beta[j];
                    r_asteristico = j;
                }
            }
            if(r_asteristico == -1)
                break;
            gama[r_asteristico] = 1;
            w = r_asteristico;
        }
        this.filterDijkstra(pi, u, v, piFiltrade);
        piFiltrade.add(u);
        return piFiltrade;
    }
    /**
     * O mÃ©todo filterDijkstra filtra recursivamente o vetor que contÃ©m o menor
     * caminho no caso de Dijkstra, ele filtra o vetor pi
     * @param pi vetor que contÃ©n o menor caminho
     * @param u vertice de origem
     * @param v vertice de destino
     * @param smallerPath array que conterÃ¡ o menor caminho filtrado
     * @return um vetor que contÃ©m o menor caminho, agora filtrado
     */
    public int filterDijkstra(int[] pi,int u, int v, 
            ArrayList<Integer> smallerPath){
        if(u != v){
            smallerPath.add(v);
            return this.filterDijkstra(pi, u, pi[v], smallerPath);
        }
        return 0;
    }
    /**
     * O mÃ©todo setBeta inicia o vetor beta com cada um de seus elementos contendo
     * infinito, ou seja, um nÃºmero tÃ£o grande quanto possivel
     * @param tamanho tamanho do vetor
     * @return retorna um vetor com cada elemento igual a infinito
     */
    public int[] setBeta(int tamanho){
        int[] gama = new int[tamanho];
        int infinito = 999999999;
        for(int i=0; i < tamanho;i++){
            gama[i] = infinito;
        }
        return gama;
    }
    /**
     * O mÃ©todo alpha retorna o peso da aresta a(w,r)
     * @param w vertice de origem
     * @param r vertice de destino
     * @return retorna um inteiro que Ã© o peso da aresta a(w,r)
     */
    public int alpha(int w, int r){
        int edge;
        if(this.adjacenciesMatrix[w][r] != 0)
            edge =  this.adjacenciesMatrix[w][r];
        else
            edge = this.adjacenciesMatrix[r][w];
        return edge;
    }
    /**
     * O mÃ©todo edges retorna as ligaÃ§Ãµes de um vertice passado como parÃ¢metro,
     * ou seja, retorna os vertices que estÃ£o ligados por meio de arestas a(v,x)
     * isso para a matriz de adjacencias que representa as estaÃ§Ãµes de taxÃ­
     * @param vertice vertice que serÃ¡ retornado suas ligaÃ§Ãµes (arestas)
     * @return retorna um vetor que contÃ©n as ligaÃ§Ãµes de vertice
     */
    public ArrayList<Integer> edges(int vertice){
        ArrayList<Integer> eadge = new ArrayList<Integer>();
        for(int i=0;i < this.SIZE;i++){
            if (this.adjacenciesMatrix[vertice][i] != 0)
                eadge.add(i);
            if(this.adjacenciesMatrix[i][vertice] != 0)
                eadge.add(i);
        }
        return eadge;
    }
    /**
     * O mÃ©todo setAdjacenciesMatriz recebe um argumento que Ã© uma 
     * matriz de adjacencias
     * @param mat matriz de adjacencias
     */
    public void setAdjacenciesMatriz(int[][] mat){
        this.adjacenciesMatrix = mat;
    }
    /**
     * O mÃ©todo geniricGeneratorStation cria estaÃ§Ãµes de Ã´nibus ou trem
     * @param limiteStation vetor com constantes necessarias para a geraÃ§Ã£o das estaÃ§Ãµes
     * @param adjacenciesMatrix matriz de adjacencias que serÃ¡ setada
     */
    private void geniricGeneratorStation(int[] limiteStation, int[][] adjacenciesMatrix){
        int iterador = 0, j=1;
        while(iterador < this.stationBusTaxi.length-limiteStation[2]){
            int numberGenereted = limiteStation[0]+this.WEIGHT.nextInt(limiteStation[1]);
            if(iterador+numberGenereted > (this.quantInLine*j)-1){
                iterador+=numberGenereted;
                j++;
            }
            else{
                adjacenciesMatrix[iterador][iterador+numberGenereted] = 1;
                iterador+=numberGenereted;
            }
        }
    }
    /**
     * O mÃ©todo generatorBusTaxiStation invoca o mÃ©todo generatorBusTaxiStation
     * passando um vetor de constantes que condizem para a criaÃ§Ã£o das estaÃ§Ãµes
     * de Ã´nibus.
     */
    public void generatorBusTaxiStation(){
        final int[] LIMITESTATION = {2,4,7};
        this.geniricGeneratorStation(LIMITESTATION, this.stationBusTaxi);
    }
    /**
     * O mÃ©todo generatorTrainTaxiStation invoca o mÃ©todo generatorBusTaxiStation
     * passando um vetor de constantes que condizem para a criaÃ§Ã£o das estaÃ§Ãµes
     * de trem.
     */
    public void generatorTrainTaxiStation(){
        final int[] LIMITESTATION = {4,6,10};
        this.geniricGeneratorStation(LIMITESTATION, this.stationTrainBus);
    }
    /**
     * O mÃ©todo possibleEges retorna as possivÃ©is ligaÃ§Ãµes de um vertice passado
     * como parÃ¢metro
     * @param vertce vertice que serÃ¡ analido suas possiveis ligaÃ§Ãµes
     * @return um vetor com as possiveis ligaÃ§Ãµes do vertice
     */
    public int[] possibleEges(int vertce){
        int[] edges = new int[2];
        if(vertce < this.LIMITEEDGE[1]-this.quantInLine){
            edges[0] = vertce+1;
            edges[1] = this.quantInLine+vertce;
        }
        else{
            edges[0] = vertce + 1;
        }
        return edges;
    }
    /**
     * O mÃ©todo thereEdge retorna true se existe uma aresta que liga dois vertices
     * passados como parÃ¢metros
     * @param vertcePartida vertice de partida
     * @param vertceDestino vertice de destino
     * @return retorna true se existir uma ligaÃ§Ã£o, caso contrÃ¡rio retorna false
     */
    public boolean thereEdge(int vertcePartida, int vertceDestino){
        return this.thereEdge(vertcePartida, vertceDestino, this.adjacenciesMatrix);
    }
    public boolean thereEdge(int vertcePartida, int vertceDestino, int[][] adjacenciesMatrix){
        boolean state = false;
        try{
        if(adjacenciesMatrix[vertcePartida][vertceDestino] != 0)
            state = true;
        }catch(IndexOutOfBoundsException ioe){}
        return state;
    }
    /**
     * O mÃ©todo returnEdge retorna as ligaÃ§Ãµes de um vertice passado como parÃ¢metro
     * da matriz de adjacencias que representa as estaÃ§Ãµes de taxÃ­, ou da matriz
     * de adjacencias que representa as estaÃ§Ãµes de trem
     * @param vertce vertice que serÃ¡ analido
     * @param busTrainSelector boolean para a seleÃ§Ã£o da matriz
     * @return um vetor com as ligaÃ§Ãµes do vertice em uma das matrizes
     */
    public ArrayList<Integer> returnEdge(int vertce, boolean busTrainSelector){
        ArrayList<Integer> edges;
        if(busTrainSelector){
            edges = this.returnEdgeBusStation(vertce);
        }
        else{
            edges = this.returnEdgeTrainStation(vertce);
        }
        return edges;
    }
    /**
     * O mÃ©todo returnEdgeTrainStation retorna as ligaÃ§Ãµes de um vertice passado
     * como parÃ¢metro da matriz de adjacencias que representa as estaÃ§Ãµes de trem
     * @param vertce vertice que serÃ¡ analisado
     * @return retorna um vetor com as ligaÃ§Ãµes do vertice
     */
    private ArrayList<Integer> returnEdgeTrainStation(int vertce){
        ArrayList<Integer> edges = new ArrayList<>();
        for(int i=0;i < this.stationTrainBus.length;i++){
            if(this.stationTrainBus[vertce][i] != 0){
                edges.add(i);
            }
        }
        return edges;
    }
    /**
     *O mÃ©todo returnEdgeTrainStation retorna as ligaÃ§Ãµes de um vertice passado
     * como parÃ¢metro da matriz de adjacencias que representa as estaÃ§Ãµes de Ã´nibus
     * @param vertce vertice que serÃ¡ analisado
     * @return retorna um vetor com as ligaÃ§Ãµes do vertice 
     */
    public ArrayList<Integer> returnEdgeBusStation(int vertce){
        ArrayList<Integer> edges = new ArrayList<>();
        for(int i=0;i < this.stationBusTaxi.length;i++){
            if(this.stationBusTaxi[vertce][i] != 0){
                edges.add(i);
            }
        }
        return edges;
    }
    public int incideArestaInStationBusOrTrain(int vertice){
        int result = 0; //resultado 0 indica que nÃ£o hÃ¡ ligaÃ§Ã£o em nemhuma das matrizes
        for(int i=0;i < this.SIZE;i++){
            if(this.stationTrainBus[vertice][i] != 0 || this.stationTrainBus[i][vertice] != 0){
                result = 1; //resultado 1 indica que hÃ¡ ligaÃ§Ã£o na estaÃ§Ã£o de trem
                return result;
            }
            else if(this.stationBusTaxi[vertice][i] != 0 || this.stationBusTaxi[i][vertice] != 0){
                result = 2; //resultado 2 indica que hÃ¡ ligaÃ§Ã£o na estaÃ§Ã£o de Ã´nibus
            }
        }
        return result;
    }
    public boolean movimentation(int verticePartida, int verticeChegada){
        boolean state = false;
        if(this.genericMovimentation(verticePartida, verticeChegada, this.adjacenciesMatrix)){
            state = true;
        }
        else if(this.genericMovimentation(verticePartida, verticeChegada, this.stationTrainBus)){
            state = true;
        }
        else if (this.genericMovimentation(verticePartida, verticeChegada, this.stationBusTaxi)){
            state = true;
        }
        return state;
    }
    public boolean genericMovimentation(int verticePartida, int verticeChegada, int[][] adjacencieMatriz){
        boolean state = false;
        if(verticePartida < verticeChegada)
            state = this.thereEdge(verticePartida, verticeChegada, adjacencieMatriz);
        else
            state = this.thereEdge(verticeChegada, verticePartida, adjacencieMatriz);
        return state;
    }
}