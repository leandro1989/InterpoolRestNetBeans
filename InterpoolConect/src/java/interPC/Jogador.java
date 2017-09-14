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