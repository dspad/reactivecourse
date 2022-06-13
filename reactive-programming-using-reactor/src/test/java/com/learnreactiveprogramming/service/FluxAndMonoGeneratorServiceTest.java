package com.learnreactiveprogramming.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

class FluxAndMonoGeneratorServiceTest {

	FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
	
	@Test
	void namesFluxTest() {
		
		Flux<String> names = fluxAndMonoGeneratorService.namesFlux();
		
		StepVerifier.create(names)
			.expectNext("alex")
				.expectNextCount(2)
			.verifyComplete();
	}

	@Test
	void namesFluxMapTest() {

		int stringLength = 3;

		Flux<String> names = fluxAndMonoGeneratorService.namesFluxMap(stringLength);

		StepVerifier.create(names)
				.expectNext("4-ALEX","5-CHLOE")
				.verifyComplete();
	}

	@Test
	void namesFluxImmutableTest() {

		Flux<String> names = fluxAndMonoGeneratorService.namesFluxImmutabilty();

		StepVerifier.create(names)
				.expectNext("alex","ben","chloe")
				.verifyComplete();
	}

	@Test
	void namesFluxFlatMapTest() {
		int stringLength = 3;

		Flux<String> names = fluxAndMonoGeneratorService.namesFluxFlatMap(stringLength);

		StepVerifier.create(names)
				.expectNext("A","L","E","X","C","H","L","O","E")
				.verifyComplete();

	}

	@Test
	void namesFluxFlatMap_async() {
		int stringLength = 3;

		Flux<String> names = fluxAndMonoGeneratorService.namesFluxFlatMap_async(stringLength);

		StepVerifier.create(names)
				//.expectNext("A","L","E","X","C","H","L","O","E")
				.expectNextCount(9)
				.verifyComplete();

	}

	@Test
	void namesFluxConcatMap() {

		int stringLength = 3;

		Flux<String> names = fluxAndMonoGeneratorService.namesFluxFlatMap_async(stringLength);

		StepVerifier.create(names)
				.expectNext("A","L","E","X","C","H","L","O","E")
				//.expectNextCount(9)
				.verifyComplete();
	}

	@Test
	void namesMono_flatmap() {
		int stringLength = 3;

		var name = fluxAndMonoGeneratorService.namesMono_flatmap(stringLength);

		StepVerifier.create(name)
				.expectNext(List.of("A","L","I","C","E"))
				//.expectNextCount(9)
				.verifyComplete();
	}

	@Test
	void namesMono_flatmapmany() {
		int stringLength = 3;

		var name = fluxAndMonoGeneratorService.namesMono_flatmapmany(stringLength);

		StepVerifier.create(name)
				.expectNext("A","L","I","C","E")
				//.expectNextCount(9)
				.verifyComplete();
	}

	@Test
	void namesFluxTransform() {

		int stringLength = 3;

		Flux<String> names = fluxAndMonoGeneratorService.namesFluxTransform(stringLength);

		StepVerifier.create(names)
				.expectNext("A","L","E","X","C","H","L","O","E")
				//.expectNextCount(9)
				.verifyComplete();

	}

	@Test
	void namesFluxTransform_1() {

		int stringLength = 6;

		Flux<String> names = fluxAndMonoGeneratorService.namesFluxTransform(stringLength);

		StepVerifier.create(names)
				.expectNext("default")
				//.expectNextCount(9)
				.verifyComplete();

	}

	@Test
	void namesFluxTransform_SwitchIfEmpty() {
		int stringLength = 6;

		Flux<String> names = fluxAndMonoGeneratorService.namesFluxTransform_SwitchIfEmpty(stringLength);

		StepVerifier.create(names)
				.expectNext("D","E","F","A","U","L","T")
				//.expectNextCount(9)
				.verifyComplete();
	}

	@Test
	void explore_concat() {
		Flux<String> concatStrings = fluxAndMonoGeneratorService.explore_concat();

		StepVerifier.create(concatStrings)
				.expectNext("A","B","C","D","E","F")
				.verifyComplete();
	}

	@Test
	void explore_concatWith() {
		Flux<String> concatStrings = fluxAndMonoGeneratorService.explore_concatWith();

		StepVerifier.create(concatStrings)
				.expectNext("A","B")
				.verifyComplete();
	}

	@Test
	void explore_merge() {
		Flux<String> mergeValues = fluxAndMonoGeneratorService.explore_merge();

		StepVerifier.create(mergeValues)
				.expectNext("A","D","B","E","C","F")
				.verifyComplete();

	}

	@Test
	void explore_mergeSequential() {
		Flux<String> mergeValues = fluxAndMonoGeneratorService.explore_mergeSequential();

		StepVerifier.create(mergeValues)
				.expectNext("A","B","C","D","E","F")
				.verifyComplete();

	}

	@Test
	void explore_zip() {
		Flux<String> zipValues = fluxAndMonoGeneratorService.explore_zip();

		StepVerifier.create(zipValues)
				.expectNext("AD","BE","CF")
				.verifyComplete();

	}

	@Test
	void explore_zipMap() {
		Flux<String> zipValues = fluxAndMonoGeneratorService.explore_zipMap();

		StepVerifier.create(zipValues)
				.expectNext("AD14", "BE25", "CF36")
				.verifyComplete();

	}

	@Test
	void explore_zipWith() {
		Flux<String> zipValues = fluxAndMonoGeneratorService.explore_zipWith();

		StepVerifier.create(zipValues)
				.expectNext("AD","BE","CF")
				.verifyComplete();

	}

	@Test
	void explore_zipWithMono() {

		Mono<String> zipWithValues = fluxAndMonoGeneratorService.explore_zipWithMono();

		StepVerifier.create(zipWithValues)
				.expectNext("BA")
				.verifyComplete();
	}
}