package com.lifesight.service;


import com.lifesight.service.model.Pixel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PixelControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Test
    public void getAllPixels() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/v1/getAllPixels";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assert.assertEquals(HttpStatus.ACCEPTED.value(), result.getStatusCodeValue());
        Assert.assertEquals(true, !result.getBody().isEmpty());
    }

    @Test
    public void getPixelByName() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/v1/getPixel/HD";
        URI uri = new URI(baseUrl);

        ResponseEntity<Pixel> result = restTemplate.getForEntity(uri, Pixel.class);

        Assert.assertEquals(1366, result.getBody().getSizeX());
        Assert.assertEquals(768, result.getBody().getSizeY());
    }

    @Test
    public void getPixelByInvalid() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/v1/getPixel/4KDigital";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assert.assertEquals(true, result.getBody() != null);
        Assert.assertEquals("Pixel not exists", result.getBody());
    }

    @Test
    public void dropPixel() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/v1/dropPixel/FHD";

        ResponseEntity<Pixel> result = restTemplate.exchange(baseUrl, HttpMethod.DELETE, null, Pixel.class);

        Assert.assertEquals(true, result.getBody() != null);
        Assert.assertEquals("FHD", result.getBody().getName());
    }

    @Test
    public void createPixel() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/v1/createPixel";
        URI uri = new URI(baseUrl);

        Pixel pixel = new Pixel(2000, 2000, "4K");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Pixel> entity = new HttpEntity<>(pixel, httpHeaders);
        ResponseEntity<Pixel> result = restTemplate.exchange(uri, HttpMethod.POST, entity, Pixel.class);

        Assert.assertEquals("4K", result.getBody().getName());
    }


}
