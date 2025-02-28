package com.college.notice.repository;

import com.college.notice.entity.AUser;
import com.college.notice.entity.AuthUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AUserRepository extends MongoRepository<AUser,String> {
    Optional<AUser> findByEmail(String email);
}
