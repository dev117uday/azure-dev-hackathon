package com.backend.linkservice.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.backend.linkservice.repository.LinkRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import dto.CollectionDTO;
import dto.CollectionLinkDTO;
import dto.LinkDTO;
import exception.RestException;
import models.Links;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public LinkService(LinkRepository linkRepository, RestTemplate restTemplate) {
        this.linkRepository = linkRepository;
        this.restTemplate = restTemplate;
    }

    public LinkDTO getLinkService(Long linkId) throws RestException {
        Optional<Links> linkOp = linkRepository.findById(linkId);
        if (linkOp.isEmpty()) {
            throw new RestException("link not found", HttpStatus.NOT_FOUND);
        }
        return LinkDTO.linksTolinkDTO(linkOp.get());
    }

    public LinkDTO saveLinkService(LinkDTO linkDTO) throws RestException {

        try {
            var collectionDTO = restTemplate.getForObject("http://COLLECTION-SERVICE/api/v1/collection/" + linkDTO.getCollectionId(), CollectionDTO.class);
            if (collectionDTO == null) {
                throw new RestException("collection not found", HttpStatus.BAD_REQUEST);
            }
            var linkTOSave = LinkDTO.linkDtoToLink(linkDTO);
            var savedLink = linkRepository.save(linkTOSave);
            return LinkDTO.linksTolinkDTO(savedLink);

        } catch (IllegalArgumentException e) {
            throw new RestException("link object cannot be null", HttpStatus.BAD_REQUEST);
        } catch (RestClientException e) {
            throw new RestException("collection not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RestException("internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public LinkDTO updateLinkService(Long linkId, LinkDTO linkDTO) throws RestException {

        Optional<Links> linkOp = linkRepository.findById(linkId);

        if (linkOp.isEmpty()) {
            throw new RestException("link not found", HttpStatus.NOT_FOUND);
        }

        var linkToUpdate = linkOp.get();
        linkToUpdate.setUrlLink(
                linkDTO.getUrlLink() == null ? linkToUpdate.getUrlLink() : linkDTO.getUrlLink()
        );
        linkToUpdate.setLinkDescription(
                linkDTO.getLinkDescription() == null ? linkToUpdate.getLinkDescription() : linkDTO.getLinkDescription()
        );
        var savedLink = linkRepository.save(linkToUpdate);
        return LinkDTO.linksTolinkDTO(savedLink);
    }


    public void deleteLinkService(Long linkId) throws RestException {
        try {
            linkRepository.deleteById(linkId);
        } catch (EmptyResultDataAccessException e) {
            throw new RestException("link not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public CollectionLinkDTO getLinkWCollection(Long linkId) throws RestException {
        Optional<Links> linkOp = linkRepository.findById(linkId);
        if (linkOp.isEmpty()) {
            throw new RestException("link not found", HttpStatus.NOT_FOUND);
        }
        var link = linkOp.get();
        CollectionLinkDTO dto = new CollectionLinkDTO();
        dto.setCollectionDTO(CollectionDTO.collectionToCollectionDTO(link.getCollection()));
        dto.setLinkDTO(LinkDTO.linksTolinkDTO(link));
        return dto;
    }
}
