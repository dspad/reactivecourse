package com.learnreactiveprogramming.service;

import java.time.Duration;
import java.util.List;
import java.util.Random;

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
	 * Esempio di utilizzo di flatMap
	 * @param stringLength
	 * @return
	 */
	public Flux<String> namesFluxFlatMap(int stringLength){
		//Simula una chiamata a db o servizio remoto
		return Flux.fromIterable(List.of("alex", "ben", "chloe"))
				.map(String::toUpperCase)
				//.map(s -> s.toUpperCase()) //come sopra
				.filter(s -> s.length() > stringLength) //solo le stringhe con lunghezza > 3
				.flatMap(s-> splitString(s)) //Splitta ALEX,CHLOE in A,L,E,X,C,H,L,O,E
				//log consente di tenere traccia di ogni passaggio eseguito nel flux
				//Gli eventi tracciati sono onSubscribe, request, onNext (per ogni elemento) e onComplete
				.log();
	}

	/**
	 * Esempio di utilizzo di flatMap in modo asincrono
	 * @param stringLength
	 * @return
	 */
	public Flux<String> namesFluxFlatMap_async(int stringLength){
		//Simula una chiamata a db o servizio remoto
		return Flux.fromIterable(List.of("alex", "ben", "chloe"))
				.map(String::toUpperCase)
				//.map(s -> s.toUpperCase()) //come sopra
				.filter(s -> s.length() > stringLength) //solo le stringhe con lunghezza > 3
				.flatMap(s-> splitString_withDelay(s)) //Splitta ALEX,CHLOE in A,L,E,X,C,H,L,O,E
				//log consente di tenere traccia di ogni passaggio eseguito nel flux
				//Gli eventi tracciati sono onSubscribe, request, onNext (per ogni elemento) e onComplete
				.log();
	}

	/**
	 * Esempio di utilizzo di concatMap.
	 * Rispetto a flatMap, concatMap preserva l'ordinamento dei valori anche in caso di inserimento di
	 * modalita' asincrone
	 * @param stringLength
	 * @return
	 */
	public Flux<String> namesFluxConcatMap(int stringLength){
		//Simula una chiamata a db o servizio remoto
		return Flux.fromIterable(List.of("alex", "ben", "chloe"))
				.map(String::toUpperCase)
				//.map(s -> s.toUpperCase()) //come sopra
				.filter(s -> s.length() > stringLength) //solo le stringhe con lunghezza > 3
				.concatMap(s-> splitString_withDelay(s)) //Splitta ALEX,CHLOE in A,L,E,X,C,H,L,O,E
				//log consente di tenere traccia di ogni passaggio eseguito nel flux
				//Gli eventi tracciati sono onSubscribe, request, onNext (per ogni elemento) e onComplete
				.log();
	}

	public Flux<String> splitString(String name){
		var charArray = name.split("");
		return Flux.fromArray(charArray);
	}

	public Flux<String> splitString_withDelay(String name){
		var charArray = name.split("");
		var delay = new Random().nextInt(1000);
		return Flux.fromArray(charArray)
				.delayElements(Duration.ofMillis(delay));
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

	/**
	 * Esempio di uso di flatMap su un Mono
	 * @param stringLength
	 * @return
	 */
	public Mono<List<String>> namesMono_flatmap(int stringLength) {
		return Mono.just("alice")
				.map(String::toUpperCase)
				.filter(s->s.length() > stringLength)
				.flatMap(this::splitStringMono)
				.log();
	}

	/**
	 * Creazione di un Mono con una lista di stringhe a partire da una stringa
	 * @param s
	 * @return
	 */
	private Mono<List<String>> splitStringMono(String s) {
		var chararray = s.split("");
		var charlist = List.of(chararray);
		return Mono.just(charlist);
	}

}

