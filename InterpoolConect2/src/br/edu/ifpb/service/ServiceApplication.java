package br.edu.ifpb.service;

import java.util.HashSet;
import java.util.Set;



import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class ServiceApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public ServiceApplication() {
		
		// Serviços		
		this.singletons.add(new Services());
		//this.singletons.add(new CoordenadorController());
		//this.singletons.add(new RecursoQuadro());
	}

	public Set<Class<?>> getClasses() {
		return this.empty;
	}

	public Set<Object> getSingletons() {
		return this.singletons;
	}
}