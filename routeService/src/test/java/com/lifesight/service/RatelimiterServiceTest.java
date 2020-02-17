package com.lifesight.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RatelimiterServiceTest {

    @LocalServerPort
    int randomServerPort;

    @Test
    public void getAdminUsersTestGlobalLevelLimits() throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/adminService/getAdmins";
        URI uri = new URI(baseUrl);

        int counter = 0;
        for (int i = 0; i < 10; i++) {
            ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
            if(result.getBody().equals("Ratelimit exceeded. Please try after sometime.")) {
                counter++;
            }
        }
        Assert.assertEquals(5, counter);

        System.out.println("waiting for a sec");
        Thread.sleep(1000);

        counter = 0;
        for (int i = 0; i < 10; i++) {
            ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
            if(result.getBody().equals("Ratelimit exceeded. Please try after sometime.")) {
                counter++;
            }
        }
        Assert.assertEquals(10, counter);

        System.out.println("waiting for a min");
        Thread.sleep(60 * 1000);

        counter = 0;
        for (int i = 0; i < 10; i++) {
            ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
            if(result.getBody().equals("Ratelimit exceeded. Please try after sometime.")) {
                counter++;
            }
        }
        Assert.assertEquals(5, counter);
    }

    @Test
    public void getPixelTestToCheckGlobalAndAPILevelLimits() throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/pixelService/getPixel/HD";
        URI uri = new URI(baseUrl);

        int counter = 0;
        for (int i = 0; i < 10; i++) {
            ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
            if(result.getBody().equals("Ratelimit exceeded. Please try after sometime.")) {
                counter++;
            }
        }
        Assert.assertEquals(7, counter);

        System.out.println("waiting for a sec");
        Thread.sleep(1000);

        counter = 0;
        ResponseEntity<String> result_1 = restTemplate.getForEntity(uri, String.class);
        for (int i = 0; i < 10; i++) {
            ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
            if(result.getBody().equals("Ratelimit exceeded. Please try after sometime.")) {
                counter++;
            }
        }
        Assert.assertEquals(8, counter);

        System.out.println("waiting for 5 sec");
        Thread.sleep(5 * 1000);

        counter = 0;
        for (int i = 0; i < 10; i++) {
            ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
            if(result.getBody().equals("Ratelimit exceeded. Please try after sometime.")) {
                counter++;
            }
        }
        Assert.assertEquals(7, counter);
    }
}
