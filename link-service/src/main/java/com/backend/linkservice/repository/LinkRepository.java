package com.backend.linkservice.repository;

import models.Links;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Links,Long> {
}
