package com.shann.shortenurl.repositories;

import com.shann.shortenurl.models.ShortenedUrl;
import com.shann.shortenurl.models.UrlAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlAccessLogRepository extends JpaRepository<UrlAccessLog, Long> {
    List<UrlAccessLog> findByShortenedUrl(ShortenedUrl shortenedUrl);
}
