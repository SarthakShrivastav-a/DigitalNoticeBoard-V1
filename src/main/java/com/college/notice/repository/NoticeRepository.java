package com.college.notice.repository;

import com.college.notice.entity.Notice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoticeRepository extends MongoRepository<Notice, String> {
    List<Notice> findByVisibility(String visibility);
}
