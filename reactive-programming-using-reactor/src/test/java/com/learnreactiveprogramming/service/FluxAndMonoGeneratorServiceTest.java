package com.learnreactiveprogramming.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

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
}