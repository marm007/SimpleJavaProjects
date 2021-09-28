package com.company.springbootimageeditor.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    private ImageProcessorController imageProcessorController = new ImageProcessorController();

    @PostMapping(value = "/image", produces = "application/json")
    public String addImage(HttpServletRequest requestEntity) throws Exception {

        return imageProcessorController.setImage(requestEntity.getInputStream());
    }

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable("id") String id) throws Exception {
        return imageProcessorController.getImage(id);
    }

    @GetMapping(value = "/image/{id}/size")
    public String getData(@PathVariable("id") String id) throws Exception {
        return imageProcessorController.getImageData(id);
    }

    @GetMapping(value = "/image/{id}/scale/{percent}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getScaledImage(@PathVariable("id") String id, @PathVariable("percent") String percent)
            throws Exception {
        return imageProcessorController.getScaledImage(id, Float.valueOf(percent));
    }

    @GetMapping(value = "image/{id}/histogram")
    public int[][] getHistogram(@PathVariable("id") String id) throws Exception {
        return imageProcessorController.getHistogram(id);
    }

    @GetMapping(value = "image/{id}/greyscale", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getGreyscaleImage(@PathVariable("id") String id) throws Exception {
        return imageProcessorController.getGreyscaleImage(id);
    }

    @GetMapping(value = "image/{id}/crop")
    public ResponseEntity<byte[]> getCroppedImage(@PathVariable("id") String id,
            @RequestParam(value = "start") int start, @RequestParam(value = "stop") int stop,
            @RequestParam(value = "width") int width, @RequestParam(value = "height") int height) throws Exception {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imageProcessorController.getCroppedImage(id, start, stop, width, height), headers,
                HttpStatus.OK);
    }

    @GetMapping(value = "image/{id}/blur", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getBlurImage(@PathVariable("id") String id, @RequestParam(value = "radius") int radius)
            throws Exception {
        return imageProcessorController.getBlurImage(id, radius);
    }

    @ExceptionHandler
    public void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response)
            throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.toString());
    }

    @ExceptionHandler
    public void handleNullPointerException(NullPointerException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), e.toString());
    }

}
