package com.lifesight.service.manager;

import com.lifesight.service.model.Pixel;
import com.lifesight.service.repository.PixelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PixelManager {

    @Autowired
    PixelRepository repository;

    public ResponseEntity<?> getAllPixels() {
        try {
            List<Pixel> pixels = repository.getPixelsByName(null);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(pixels);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<?> getPixelByName(String name) {
        try {
            if(name != null) {
                Pixel pixel = repository.isExists(new Pixel(name));
                if(pixel != null) {
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(pixel);
                } else {
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body("Pixel not exists");
                }
            } else {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Pixel name shouldn't be null");
            }
        } catch (Exception e) {
            log.error("Error", e);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(e.getMessage());
        }
    }

    public ResponseEntity<?> createPixel(Pixel pixel) {
        try {
            if(pixel != null && pixel.getName() != null) {
                Pixel e_pixel = repository.isExists(pixel);
                if(e_pixel == null) {
                    pixel = repository.createPixel(pixel);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(pixel);
                } else {
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body("Pixel exists");
                }
            } else {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Pixel details shouldn't be null");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(e.getMessage());
        }
    }

    public ResponseEntity<?> updatePixel(Pixel pixel) {
        try {
            if(pixel != null && pixel.getName() != null) {
                pixel = repository.isExists(pixel);
                if(pixel != null) {
                    pixel = repository.updatePixel(pixel);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(pixel);
                } else {
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body("Pixel exists");
                }
            } else {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Pixel details shouldn't be null");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(e.getMessage());
        }
    }

    public ResponseEntity<?> dropPixel(String name) {
        try {
            if(name != null) {
                Pixel pixel = repository.isExists(new Pixel(name));
                if(pixel == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pixel not exists");
                repository.dropPixel(pixel);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(pixel);
            } else {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Pixel name shouldn't be null");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(e.getMessage());
        }
    }
}
