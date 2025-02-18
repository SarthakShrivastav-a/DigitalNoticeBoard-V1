package com.college.notice.repository;

import com.college.notice.entity.AuthUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthUserRepository extends MongoRepository<AuthUser,String> {
    Optional<AuthUser> findByEmail(String email);
}
