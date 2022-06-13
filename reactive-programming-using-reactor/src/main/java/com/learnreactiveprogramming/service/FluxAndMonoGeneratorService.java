package com.learnreactiveprogramming.service;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

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
	 * @return restituisce un altro Mono
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

	/**
	 * Esempio di utilizzo di flatMapMany su un Mono
	 * @param stringLength
	 * @return Restituisce un Flux
	 */
	public Flux<String> namesMono_flatmapmany(int stringLength) {
		return Mono.just("alice")
				.map(String::toUpperCase)
				.filter(s->s.length() > stringLength)
				.flatMapMany(this::splitString)
				.log();
	}

	/**
	 * Esempio di utilizzo di transform e defaultIfEmpty
	 * @param stringLength
	 * @return
	 */
	public Flux<String> namesFluxTransform(int stringLength){
		//Inserisci il map e il filter in una function che prende in input un Flux<String> e restituisce un Flux<String>
		Function<Flux<String>,Flux<String>> filtermap = name -> name.map(String::toUpperCase)
																.filter(s -> s.length() > stringLength);

		return Flux.fromIterable(List.of("alex", "ben", "chloe"))
				.transform(filtermap) //Applica la function
				.flatMap(s-> splitString(s)) //Splitta ALEX,CHLOE in A,L,E,X,C,H,L,O,E
				//log consente di tenere traccia di ogni passaggio eseguito nel flux
				//Gli eventi tracciati sono onSubscribe, request, onNext (per ogni elemento) e onComplete
				.defaultIfEmpty("default") //se a seguito del transform il Flux e' vuoto, di default inserisci una stringa
				.log();
	}

	/**
	 * Esempio di utilizzo di SwitchIfEmpty
	 * @param stringLength
	 * @return
	 */
	public Flux<String> namesFluxTransform_SwitchIfEmpty(int stringLength){
		//Inserisci il map e il filter in una function che prende in input un Flux<String> e restituisce un Flux<String>
		Function<Flux<String>,Flux<String>> filtermap = name -> name.map(String::toUpperCase)
				.filter(s -> s.length() > stringLength);

		var defaultflux = Flux.just("default")
				.transform(filtermap)
				.flatMap(s-> splitString(s));

		return Flux.fromIterable(List.of("alex", "ben", "chloe"))
				.transform(filtermap) //Applica la function
				.switchIfEmpty(defaultflux) //se a seguito del transform il Flux e' vuoto, switcha su un altro Flux
				.log();
	}

	/**
	 * Esempio di utilizzo di concat per concatenare due o piu' Flux/Mono
	 * @return
	 */
	public Flux<String> explore_concat(){
		var abcFlux = Flux.just("A","B","C");
		var defFlux = Flux.just("D","E","F");
		return Flux.concat(abcFlux,defFlux).log();
	}

	/**
	 * Esempio di utilizzo di concatWith per concatenare due Mono/Flux
	 * @return restiuisce un Flux
	 */
    public Flux<String> explore_concatWith(){
		var aMono = Mono.just("A");
		var bMono = Mono.just("B");
		return aMono.concatWith(bMono);
	}

	/**
	 * Esempio di utilizzo di merge che, a differenza di concat, non concatena ma restiutisce
	 * un flux merge-ato in base all'esecuzione degli stream
	 * E' possibile anche usare mergeWith (commentato alla fine)
	 * @return
	 */
	public Flux<String> explore_merge(){
		var abcFlux = Flux.just("A","B","C")
				.delayElements(Duration.ofMillis(100)); //passa al prossimo elemento tra 100 ms
		var defFlux = Flux.just("D","E","F")
				.delayElements(Duration.ofMillis(125)); //passa al prossimo elemento tra 125 ms
		return Flux.merge(abcFlux,defFlux).log();
		//return abcFlux.mergeWith(defFlux);
	}

	public Flux<String> explore_mergeSequential(){
		var abcFlux = Flux.just("A","B","C")
				.delayElements(Duration.ofMillis(100)); //passa al prossimo elemento tra 100 ms
		var defFlux = Flux.just("D","E","F")
				.delayElements(Duration.ofMillis(125)); //passa al prossimo elemento tra 125 ms
		return Flux.mergeSequential(abcFlux,defFlux).log();
		//return abcFlux.mergeWith(defFlux);
	}

	/**
	 * Esempio di zip(): esegui fino a 8 Flux indicando all'interno di un Lambda l'operazione da eseguire sugli elementi
	 * @return
	 */
	public Flux<String> explore_zip(){
		var abcFlux = Flux.just("A","B","C");
		var defFlux = Flux.just("D","E","F");
		//concatena gli elementi n dei due flux
		return Flux.zip(abcFlux, defFlux, (first,second) -> first+second);//AD, BE, CF

	}

	/**
	 * Esempio di zip(): utilizzo con map
	 * @return
	 */
	public Flux<String> explore_zipMap(){
		var abcFlux = Flux.just("A","B","C");
		var defFlux = Flux.just("D","E","F");
		var _123Flux = Flux.just("1","2","3");
		var _456Flux = Flux.just("4","5","6");

		//concatena gli elementi n dei due flux
		return Flux.zip(abcFlux, defFlux, _123Flux,_456Flux)
				//Accedi agli elementi utilizzando Tuple4
				.map(t4 -> t4.getT1()+t4.getT2()+t4.getT3()+t4.getT4())//AD14, BE25, CF36
				.log();

	}

	/**
	 * Esempio di utilizzo di zipWith()
	 * @return
	 */
	public Flux<String> explore_zipWith(){
		var abcFlux = Flux.just("A","B","C");
		var defFlux = Flux.just("D","E","F");
		//concatena gli elementi n dei due flux
		return abcFlux.zipWith(defFlux, (first,second) -> first+second);//AD, BE, CF

	}


	/**
	 * Esempio di utilizzo di zipWith() con i Mono
	 * @return
	 */
	public Mono<String> explore_zipWithMono(){
		var aMono = Mono.just("A");
		var bMono = Mono.just("B");
		return aMono.zipWith(bMono)
				.map(t2 -> t2.getT2()+t2.getT1())
				.log();  //BA
	}
}

