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

		Flux<String> names = fluxAndMonoGeneratorService.namesFluxMap();

		StepVerifier.create(names)
				.expectNext("ALEX","BEN","CHLOE")
				.verifyComplete();
	}

	@Test
	void namesFluxImmutableTest() {

		Flux<String> names = fluxAndMonoGeneratorService.namesFluxImmutabilty();

		StepVerifier.create(names)
				.expectNext("alex","ben","chloe")
				.verifyComplete();
	}
}