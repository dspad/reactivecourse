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
			.expectNext("alex", "ben", "chloe")
			.verifyComplete();
	}
	
}