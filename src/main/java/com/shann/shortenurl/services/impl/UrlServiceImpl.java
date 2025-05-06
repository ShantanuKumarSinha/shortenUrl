package com.shann.shortenurl.services.impl;

import com.shann.shortenurl.exceptions.UrlNotFoundException;
import com.shann.shortenurl.exceptions.UserNotFoundException;
import com.shann.shortenurl.models.ShortenedUrl;
import com.shann.shortenurl.models.UrlAccessLog;
import com.shann.shortenurl.repositories.ShortenedUrlRepository;
import com.shann.shortenurl.repositories.UrlAccessLogRepository;
import com.shann.shortenurl.repositories.UserRepository;
import com.shann.shortenurl.services.UrlService;
import com.shann.shortenurl.utils.ShortUrlGenerator;
import org.springframework.stereotype.Service;

/**
 * UrlServiceImpl is the implementation of the UrlService interface. It provides methods to shorten
 * a URL and resolve a shortened URL.
 */
@Service
public class UrlServiceImpl implements UrlService {
  /**
   * The ShortenedUrlRepository is used to interact with the database for storing and retrieving
   * shortened URLs.
   */
  private ShortenedUrlRepository shortenedUrlRepository;

  /**
   * The UrlAccessLogRepository is used to interact with the database for storing and retrieving URL
   * access logs.
   */
  private UrlAccessLogRepository urlAccessLogRepository;

  /**
   * The UserRepository is used to interact with the database for storing and retrieving user
   * information.
   */
  private UserRepository userRepository;

  /**
   * Constructor for UrlServiceImpl.
   *
   * @param shortenedUrlRepository the ShortenedUrlRepository
   * @param urlAccessLogRepository the UrlAccessLogRepository
   * @param userRepository the UserRepository
   */
  public UrlServiceImpl(
      ShortenedUrlRepository shortenedUrlRepository,
      UrlAccessLogRepository urlAccessLogRepository,
      UserRepository userRepository) {
    this.shortenedUrlRepository = shortenedUrlRepository;
    this.urlAccessLogRepository = urlAccessLogRepository;
    this.userRepository = userRepository;
  }

  /**
   * Shortens a given URL for a user.
   *
   * @param url the URL to shorten
   * @param userId the ID of the user
   * @return the shortened URL
   * @throws UserNotFoundException if the user is not found
   */
  @Override
  public ShortenedUrl shortenUrl(String url, int userId) throws UserNotFoundException {
    var user =
        userRepository
            .findById((long) userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    var shortUrlValue = ShortUrlGenerator.generateShortUrl();
    var shortenedUrl = new ShortenedUrl();
    shortenedUrl.setOriginalUrl(url);
    shortenedUrl.setShortUrl(shortUrlValue);
    switch (user.getUserPlan()) {
      case FREE -> shortenedUrl.setExpiresAt(System.currentTimeMillis() + 86400000L); // 1 day
      case TEAM -> shortenedUrl.setExpiresAt(System.currentTimeMillis() + 604800000L); // 7 days
      case BUSINESS ->
          shortenedUrl.setExpiresAt(System.currentTimeMillis() + 2592000000L); // 30 days
      case ENTERPRISE ->
          shortenedUrl.setExpiresAt(System.currentTimeMillis() + 31536000000L); // 1 year
      default -> shortenedUrl.setExpiresAt(System.currentTimeMillis() + 86400000L); // 1 days
    }
    return shortenedUrlRepository.save(shortenedUrl);
  }

  /**
   * Resolves a shortened URL to its original URL.
   *
   * @param shortUrl the shortened URL
   * @return the original URL
   * @throws UrlNotFoundException if the shortened URL is not found
   */
  @Override
  public String resolveShortenedUrl(String shortUrl) throws UrlNotFoundException {
    var shortenedUrl =
        shortenedUrlRepository
            .findByShortUrl(shortUrl)
            .orElseThrow(() -> new UrlNotFoundException("URL not found"));
    if (shortenedUrl.getExpiresAt() <= System.currentTimeMillis())
      throw new UrlNotFoundException("URL expired");
    var urlAccessLogs = urlAccessLogRepository.findByShortenedUrl(shortenedUrl);
    var urlAccessLog = new UrlAccessLog();
    urlAccessLog.setShortenedUrl(shortenedUrl);
    urlAccessLog.setAccessedAt(System.currentTimeMillis());
    urlAccessLogRepository.save(urlAccessLog);
    return shortenedUrl.getOriginalUrl();
  }
}
