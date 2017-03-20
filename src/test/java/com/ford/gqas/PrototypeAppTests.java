package com.ford.gqas;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PrototypeApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" } )
public class PrototypeAppTests {
	
	@LocalServerPort
	private int port;
	
	// management.port
	@Value( "${local.management.port}" )
	private int mgt;
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Test
	public void controllerTestOK() throws Exception {
		@SuppressWarnings("rawtypes")
		ResponseEntity<List> entity = this.testRestTemplate.getForEntity( 
				"http://localhost:" + this.port + "/student/", List.class );
		
		then ( entity.getStatusCode()).isEqualTo( HttpStatus.OK );
	}
	
	@Test
	public void managementEndpointTestOK() throws Exception {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.mgt + "/health", Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
