package com.backend.collectionservice.controller;

import java.util.List;

import com.backend.collectionservice.service.CollectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.CollectionDTO;
import dto.UserCollectionDTO;
import exception.RestException;


@RestController
@RequestMapping("api/v1/collection")
public class CollectionController {

    private final CollectionService collectionService;

    @Autowired
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("/{collectionId}")
    public ResponseEntity<CollectionDTO> getCollectionById(@PathVariable("collectionId") Long userId) throws RestException {
        var collection = collectionService.getCollectionByIdService(userId);
        return ResponseEntity.status(HttpStatus.OK).body(collection);
    }

    @GetMapping("/{collectionId}/user")
    public ResponseEntity<UserCollectionDTO> getCollectionWUser(@PathVariable("collectionId") Long userId) throws RestException {
        var collection = collectionService.getCollectionWUserService(userId);
        return ResponseEntity.status(HttpStatus.OK).body(collection);
    }

    @GetMapping("/collection/{userid}")
    public ResponseEntity<List<CollectionDTO>> getAllCollectionByUser(@PathVariable("userid") Long userId) {
        var result = collectionService.getAllCollectionByUserService(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    public ResponseEntity<CollectionDTO> saveCollection(@RequestBody CollectionDTO collectionDTO) throws RestException {
        var savedDto = collectionService.saveCollectionService(collectionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    @PatchMapping("/{collectionId}")
    public ResponseEntity<CollectionDTO> updateCollection(@PathVariable("collectionId") Long collectionId, @RequestBody CollectionDTO collectionDTO) throws RestException {
        var updateCollectionDto = collectionService.updateCollectionService(collectionDTO, collectionId);
        return ResponseEntity.status(HttpStatus.OK).body(updateCollectionDto);
    }

    @DeleteMapping("/{collectionId}")
    public ResponseEntity<Void> deleteCollection(@PathVariable("collectionId") Long collectionId) throws RestException {
        collectionService.deleteCollectionService(collectionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

