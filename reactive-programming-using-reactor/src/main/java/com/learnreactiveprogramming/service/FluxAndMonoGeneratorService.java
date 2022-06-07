package com.learnreactiveprogramming.service;

import java.util.List;

import reactor.core.publisher.Flux;

public class FluxAndMonoGeneratorService {

	public Flux<String> namesFlux(){
		//Simula una chiamata a db o servizio remoto
		return Flux.fromIterable(List.of("alex", "ben", "chloe")); 
	}
	
   public static void main(String[] args) {
	   FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
	   fluxAndMonoGeneratorService.namesFlux()
	   					.subscribe(name -> System.out.println("Name is "+name));
   }

}

