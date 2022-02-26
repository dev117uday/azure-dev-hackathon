package com.backend.linkservice.controller;

import com.backend.linkservice.service.LinkService;

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

import dto.CollectionLinkDTO;
import dto.LinkDTO;
import exception.RestException;

@RestController
@RequestMapping("api/v1/link")
public class LinkController {

    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }


    @GetMapping("/{linkId}")
    public ResponseEntity<LinkDTO> getLinkById(@PathVariable("linkId") Long linkId) throws RestException {
        var link = linkService.getLinkService(linkId);
        return ResponseEntity.status(HttpStatus.OK).body(link);
    }

    @GetMapping("/{linkId}/collection")
    public ResponseEntity<CollectionLinkDTO> getLinkWCollection(@PathVariable("linkId") Long linkId) throws RestException {
        var link = linkService.getLinkWCollection(linkId);
        return ResponseEntity.status(HttpStatus.OK).body(link);
    }


    @PostMapping
    public ResponseEntity<LinkDTO> saveLink(@RequestBody LinkDTO linkDTO) throws RestException {
        var savedLinkDTO = linkService.saveLinkService(linkDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLinkDTO);
    }

    @PatchMapping("/{linkId}")
    public ResponseEntity<LinkDTO> updateUser(@PathVariable("linkId") Long linkId, @RequestBody LinkDTO linkDTO)
            throws RestException {

        var updatedLink = linkService.updateLinkService(linkId, linkDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedLink);
    }

    @DeleteMapping("/{linkId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("linkId") Long linkId) throws RestException {
        linkService.deleteLinkService(linkId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
