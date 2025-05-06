package com.shann.shortenurl.services;

import com.shann.shortenurl.exceptions.UrlNotFoundException;
import com.shann.shortenurl.exceptions.UserNotFoundException;
import com.shann.shortenurl.models.ShortenedUrl;

public interface UrlService {
    public ShortenedUrl shortenUrl(String url, int userId) throws UserNotFoundException;

    public String resolveShortenedUrl(String shortUrl) throws UrlNotFoundException;
}
