package com.lifesight.service.repository;


import com.lifesight.service.model.Pixel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class PixelRepository {

    private List<Pixel> pixels = new ArrayList<>();

    public PixelRepository() {
        pixels.add(new Pixel());
        pixels.add(new Pixel(640, 480, "VGA"));
        pixels.add(new Pixel(800, 600, "SVGA"));
        pixels.add(new Pixel(1024, 480, "XGA"));
        pixels.add(new Pixel(1366, 768, "HD"));
        pixels.add(new Pixel(1920, 1080, "FHD"));
    }


    public List<Pixel> getPixelsByName(String name) {
        if(name != null) {
            return pixels.stream()
                    .filter(pixel -> pixel.getName().equalsIgnoreCase(name.trim()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>(pixels);
    }

    public Pixel createPixel(Pixel pixel) {
        if(pixels.add(pixel)) {
            return pixel;
        }
        return null;
    }

    public Pixel updatePixel(Pixel pixel) {
        Pixel pixelExists = isExists(pixel);
        if(pixelExists != null) {
            pixelExists.setSizeX(pixel.getSizeX());
            pixelExists.setSizeY(pixel.getSizeY());
        }
        return pixelExists;
    }

    public Pixel isExists(Pixel pixel) {
        if(pixel == null || pixel.getName() == null) return null;

        return pixels.stream().
                filter(pixel1 -> (pixel1 != null &&
                        pixel1.getName() != null &&
                        pixel1.getName().equalsIgnoreCase(pixel.getName())))
                .findFirst().orElse(null);
    }

    public boolean dropPixel(Pixel pixel) {
        return pixels.remove(pixel);
    }
}
