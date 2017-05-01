package com.ford.gqas;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.*;

import redis.clients.jedis.Jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.*;

// import org.springframework.boot.test.
//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PrototypeApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
// @WebIntegrationTest
public class PrototypeAppTests {

	private Jedis jedis;

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private TestRestTemplate templateNoAuth;

	private String host = "localhost";

	// @LocalServerPort ... same as below?
	@Value("${local.server.port}")
	private int port;

	// management.port
	@Value("${local.management.port}")
	private int mgt;

	@Before
	public void clearRedisData() {
		//testRestTemplate = new TestRestTemplate();
		//testRestTemplateWithAuth = new TestRestTemplate("admin", "password", null);
		testRestTemplate.withBasicAuth( "admin", "password" );
		jedis = new Jedis("localhost", 6379);
		jedis.flushAll();
	}

    @Test
    public void testRedisIsEmpty() {
        Set<String> result = jedis.keys("*");
        assertEquals(0, result.size());
    }
   
    @Test
    public void testUnauthenticated() {
    	
    	String url = "http://" + host + ':' + port + "/test";   	
        ResponseEntity<String> result = templateNoAuth.getForEntity( url, String.class);
        then( result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        //assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testAuthenticated() {
    	
    	String url = "http://" + host + ':' + port + "/resources";
    	//testRestTemplate.withBasicAuth( "wfan10", "password2" );
    	@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity( url, Map.class);	    	
    	then( entity.getStatusCode()).isEqualTo(HttpStatus.OK); 
    }

	/*
	 * @Test public void controllerTestOK() throws Exception {
	 * 
	 * @SuppressWarnings("rawtypes") ResponseEntity<Map> entity =
	 * this.testRestTemplate.getForEntity( "http://localhost:" + this.port +
	 * "/resource", Map.class);
	 * 
	 * then(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED); }
	 */

	/*
	 * @Test public void managementEndpointTestOK() throws Exception {
	 * 
	 * @SuppressWarnings("rawtypes") ResponseEntity<Map> entity =
	 * this.testRestTemplate.getForEntity( "http://localhost:" + this.mgt +
	 * "/health", Map.class);
	 * 
	 * then(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED); }
	 */

}
