package com.saki.gestion_stock.controller;

import com.flickr4java.flickr.FlickrException;

import java.io.IOException;
import java.io.InputStream;

import com.saki.gestion_stock.controller.api.PhotoApi;
import com.saki.gestion_stock.services.strategy.StrategyPhotoContext;
import com.saki.gestion_stock.dto.ArticleDto;
import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidOperationException;
import com.saki.gestion_stock.services.ArticleService;
import com.saki.gestion_stock.services.FlickrService;
import com.saki.gestion_stock.services.strategy.Strategy;
import com.flickr4java.flickr.FlickrException;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PhotoController implements PhotoApi {

    private StrategyPhotoContext strategyPhotoContext;

    @Autowired
    public PhotoController(StrategyPhotoContext strategyPhotoContext) {
        this.strategyPhotoContext = strategyPhotoContext;
    }

    @Override
    public Object savePhoto(String context, Integer id, MultipartFile photo, String title) throws IOException, FlickrException {
        return strategyPhotoContext.savePhoto(context, id, photo.getInputStream(), title);
    }
}