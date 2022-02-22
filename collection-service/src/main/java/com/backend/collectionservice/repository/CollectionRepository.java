package com.backend.collectionservice.repository;

import models.UrlCollections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollectionRepository extends JpaRepository<UrlCollections, Long> {

    @Query(
            value = "select * from tbl_url_collections where user_id = ?1",
            nativeQuery = true
    )
    List<UrlCollections> findByUser(Long userId);

}
