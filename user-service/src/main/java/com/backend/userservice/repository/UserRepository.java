package com.backend.userservice.repository;

import models.User;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;

////@EnableJpaRepositories("com.backend.urlink-microservice.shared.models.*")
////@ComponentScan(basePackages = { "com.backend.urlink-microservice.shared.models.*" })
//@EntityScan("models")
public interface UserRepository extends JpaRepository<User, Long> {
}
