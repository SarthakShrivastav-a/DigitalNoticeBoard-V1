package com.college.notice.controller;

import com.college.notice.entity.AuthUser;
import com.college.notice.entity.Notice;
import com.college.notice.repository.AuthUserRepository;
import com.college.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private AuthUserRepository authUserRepository;

    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY', 'CLUBLEAD')")
    @PostMapping("/create")
    public Notice createNotice(@RequestBody Notice notice) {
        String email = getCurrentUserEmail();
        AuthUser user = authUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        notice.setCreatedBy(user.getName());
        return noticeService.createNotice(notice);
    }

    @PreAuthorize("hasAnyRole('USER', 'FACULTY', 'CLUBLEAD', 'ADMIN')")
    @GetMapping("/public")
    public List<Notice> getPublicNotices() {
        return noticeService.getPublicNotices();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/student")
    public List<Notice> getStudentNotices() {
        return noticeService.getNoticesByVisibility("STUDENT");
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'ADMIN')")
    @GetMapping("/faculty")
    public List<Notice> getFacultyNotices() {
        return noticeService.getNoticesByVisibility("FACULTY");
    }

    @PreAuthorize("hasAnyRole('USER', 'FACULTY', 'CLUBLEAD', 'ADMIN')")
    @GetMapping("/{id}")
    public Optional<Notice> getNoticeById(@PathVariable String id) {
        return noticeService.getNoticeById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    @PutMapping("/update/{id}")
    public Notice updateNotice(@PathVariable String id, @RequestBody Notice updatedNotice) {
        String email = getCurrentUserEmail();
        return noticeService.updateNotice(id, updatedNotice, email);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteNotice(@PathVariable String id) {
        noticeService.deleteNotice(id);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/subscribed")
    public List<Notice> getNoticesForSubscribedCategories() {
        String email = getCurrentUserEmail();
        return noticeService.getNoticesForUser(email);
    }


    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}

