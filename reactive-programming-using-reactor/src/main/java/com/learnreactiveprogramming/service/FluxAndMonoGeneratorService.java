package com.learnreactiveprogramming.service;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxAndMonoGeneratorService {

	/**
	 * Esempio di Flux di stringhe
	 * Un flux puo' pubblicare da 0 a n elementi
	 * @return
	 */
	public Flux<String> namesFlux(){
		//Simula una chiamata a db o servizio remoto
		return Flux.fromIterable(List.of("alex", "ben", "chloe")); 
	}
	
	/**
	 * Esempio di Mono applicato su stringa
	 * Un mono puo' pubblicare da 0 a 1 elemento
	 * @return
	 */
	public Mono<String> nameMono() {
		return Mono.just("alice");
	}
	
   public static void main(String[] args) {
	   FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
	   
	   //Richiamo il flux e eseguo il subscribe di ogni elemento del flux
	   fluxAndMonoGeneratorService.namesFlux()
	   					.subscribe(name -> System.out.println("Name is "+name));
	   
	   //Richiamo il mono ed eseguo il subscribe del singolo elemento
	   fluxAndMonoGeneratorService.nameMono()
			.subscribe(name -> System.out.println("Mono Name is "+name));
   }

}

