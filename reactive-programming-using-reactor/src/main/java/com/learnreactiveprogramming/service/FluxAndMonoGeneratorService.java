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
		return Flux.fromIterable(List.of("alex", "ben", "chloe"))
				//log consente di tenere traccia di ogni passaggio eseguito nel flux
				//Gli eventi tracciati sono onSubscribe, request, onNext (per ogni elemento) e onComplete
				.log(); 
	}

	/**
	 * Esempio di Flux di stringhe con utilizzo di map()
	 * @return
	 */
	public Flux<String> namesFluxMap(){
		//Simula una chiamata a db o servizio remoto
		return Flux.fromIterable(List.of("alex", "ben", "chloe"))
				.map(String::toUpperCase)
				//.map(s -> s.toUpperCase()) //come sopra
				//log consente di tenere traccia di ogni passaggio eseguito nel flux
				//Gli eventi tracciati sono onSubscribe, request, onNext (per ogni elemento) e onComplete
				.log();
	}

	/**
	 * Dimostrazione che i valori all'interno di un flux sono immutabili
	 * map() restituisce un altro Flux anziche' sovrascrivere
	 * @return
	 */
	public Flux<String> namesFluxImmutabilty(){
		//Simula una chiamata a db o servizio remoto
		var namesFlux =  Flux.fromIterable(List.of("alex", "ben", "chloe"));
		    namesFlux.map(String::toUpperCase);
		return namesFlux;
	}

	/**
	 * Esempio di Mono applicato su stringa
	 * Un mono puo' pubblicare da 0 a 1 elemento
	 * @return
	 */
	public Mono<String> nameMono() {
		return Mono.just("alice")
				//Idem per il mono
				.log();
	}
	
   public static void main(String[] args) {
	   FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
	   
	   //Richiamo il flux e eseguo il subscribe di ogni elemento del flux
	   fluxAndMonoGeneratorService.namesFlux()
	   					.subscribe(name -> System.out.println("Name is "+name));
	   
	   //Richiamo il mono ed eseguo il subscribe del singolo elemento
	   fluxAndMonoGeneratorService.nameMono()
			.subscribe(name -> System.out.println("Mono Name is "+name));

	   //Richiamo il flux e eseguo il subscribe di ogni elemento del flux
	   fluxAndMonoGeneratorService.namesFluxMap()
			   .subscribe(name -> System.out.println("Name is "+name));
   }

}

