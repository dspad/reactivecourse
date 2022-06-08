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
	public Flux<String> namesFluxMap(int stringLength){
		//Simula una chiamata a db o servizio remoto
		return Flux.fromIterable(List.of("alex", "ben", "chloe"))
				.map(String::toUpperCase)
				//.map(s -> s.toUpperCase()) //come sopra
				.filter(s -> s.length() > stringLength) //solo le stringhe con lunghezza > 3
				.map(s -> s.length()+"-"+s)
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

}

