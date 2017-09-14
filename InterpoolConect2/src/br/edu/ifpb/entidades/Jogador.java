package br.edu.ifpb.entidades;

public class Jogador {
	String jogada;
	int id;
	
	public Jogador(){
		this.jogada = null;
		this.id = 0;
	}

	public String getJogada() {
		return jogada;
	}
	public void setJogada(String jogada) {
		this.jogada = jogada;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
