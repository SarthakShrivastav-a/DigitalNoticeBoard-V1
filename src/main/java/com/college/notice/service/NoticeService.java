package com.college.notice.service;

import com.college.notice.entity.AuthUser;
import com.college.notice.entity.Notice;
import com.college.notice.repository.AuthUserRepository;
import com.college.notice.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    public Notice createNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

    public List<Notice> getPublicNotices() {
        return noticeRepository.findByVisibility("PUBLIC");
    }

    public List<Notice> getNoticesByVisibility(String visibility) {
        return noticeRepository.findByVisibility(visibility);
    }

    public Optional<Notice> getNoticeById(String id) {
        return noticeRepository.findById(id);
    }

    public Notice updateNotice(String id, Notice updatedNotice, String email) {
        Optional<AuthUser> edited = authUserRepository.findByEmail(email);
        return noticeRepository.findById(id).map(notice -> {
            notice.setTitle(updatedNotice.getTitle());
            notice.setContent(updatedNotice.getContent());
            notice.setCategory(updatedNotice.getCategory());
            notice.setVisibility(updatedNotice.getVisibility());
            notice.setEditedBy(edited.get().getName());
            return noticeRepository.save(notice);
        }).orElseThrow(() -> new RuntimeException("Notice not found"));
    }

    public void deleteNotice(String id) {
        noticeRepository.deleteById(id);
    }
}
