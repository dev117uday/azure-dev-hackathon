package com.backend.collectionservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.backend.collectionservice.repository.CollectionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import dto.CollectionDTO;
import dto.UserCollectionDTO;
import dto.UserDTO;
import exception.RestException;
import models.UrlCollections;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(CollectionService.class);

    @Autowired
    public CollectionService(CollectionRepository collectionRepository, RestTemplate restTemplate) {
        this.collectionRepository = collectionRepository;
        this.restTemplate = restTemplate;
    }

    public List<CollectionDTO> getAllCollectionByUserService(Long userId) {
        List<UrlCollections> lstCollection = collectionRepository.findByUser(userId);
        List<CollectionDTO> collectionDTOList = new ArrayList<>();
        for (var element : lstCollection) {
            collectionDTOList.add(CollectionDTO.collectionToCollectionDTO(element));
        }

        return collectionDTOList;
    }

    public CollectionDTO getCollectionByIdService(Long collectionId) throws RestException {
        Optional<UrlCollections> collectionOp = collectionRepository.findById(collectionId);

        if (collectionOp.isEmpty()) {
            logger.warn("collection not found :: getCollectionByIdService");
            throw new RestException("collection not found", HttpStatus.NOT_FOUND);
        }
        return CollectionDTO.collectionToCollectionDTO(collectionOp.get());
    }

    public CollectionDTO saveCollectionService(CollectionDTO collection) throws RestException {

        try {
            restTemplate.getForObject("http://USER-SERVICE/api/v1/user/"+collection.getUserId(), UserDTO.class);
        } catch (RestClientException e) {
            logger.warn("user not found in :: saveCollectionService");
            throw new RestException("user not found", HttpStatus.BAD_REQUEST);
        }

        UrlCollections collectionToSave = CollectionDTO.collectionDTOToUrlCollection(collection);
        var savedCollection = collectionRepository.save(collectionToSave);
        return CollectionDTO.collectionToCollectionDTO(savedCollection);
    }

    @Transactional
    public CollectionDTO updateCollectionService(CollectionDTO collection, Long collectionId) throws RestException {

        Optional<UrlCollections> collectionOp = collectionRepository.findById(collectionId);

        if (collectionOp.isEmpty()) {
            logger.warn("collection not found in :: updateCollectionService");
            throw new RestException("collection not found", HttpStatus.NOT_FOUND);
        }

        var collectionToUpdate = collectionOp.get();

        collectionToUpdate.setCollectionName(collection.getCollectionName() == null ? collectionToUpdate.getCollectionName() : collection.getCollectionName());

        collectionToUpdate.setCollectionDescription(collection.getCollectionDescription() == null ? collectionToUpdate.getCollectionDescription() : collection.getCollectionDescription());

        var savedCollection = collectionRepository.save(collectionToUpdate);
        return CollectionDTO.collectionToCollectionDTO(savedCollection);
    }

    public void deleteCollectionService(Long collectionId) throws RestException {
        try {
            collectionRepository.deleteById(collectionId);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("collection not found :: deleteCollectionService");
            throw new RestException("collection not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public UserCollectionDTO getCollectionWUserService(Long collectionId) throws RestException {
        Optional<UrlCollections> collectionOp = collectionRepository.findById(collectionId);
        if (collectionOp.isEmpty()) {
            logger.warn("collection not found :: getCollectionWUserService");
            throw new RestException("collection not found", HttpStatus.NOT_FOUND);
        }
        UserCollectionDTO dto = new UserCollectionDTO();
        var collection = collectionOp.get();
        dto.setCollectionDTO(CollectionDTO.collectionToCollectionDTO(collection));
        dto.setUserDTO(UserDTO.userToUserDTO(collection.getUser()));
        return dto;
    }

}
