package com.lifesight.service;


import com.lifesight.service.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Test
    public void getAdminUsers() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/v1/getAdmins";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assert.assertEquals(HttpStatus.ACCEPTED.value(), result.getStatusCodeValue());
        Assert.assertEquals(true, !result.getBody().isEmpty());
    }

    @Test
    public void getUserById() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/v1/getUser/108";
        URI uri = new URI(baseUrl);

        ResponseEntity<User> result = restTemplate.getForEntity(uri, User.class);

        Assert.assertEquals("Luke", result.getBody().getName());
    }

    @Test
    public void getUserByInvalidId() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/v1/getUser/11111";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assert.assertEquals(true, result.getBody() != null);
        Assert.assertEquals("User not exists", result.getBody());
    }

    @Test
    public void dropUser() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/v1/dropUser/101";
        URI uri = new URI(baseUrl);

        ResponseEntity<User> result = restTemplate.exchange(baseUrl, HttpMethod.DELETE, null, User.class);

        Assert.assertEquals(true, result.getBody() != null);
        Assert.assertEquals("Aaron", result.getBody().getName());
    }


}
