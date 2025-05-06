package com.shann.shortenurl.controllers;

import com.shann.shortenurl.dtos.*;
import com.shann.shortenurl.services.UrlService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shorten-url")
public class UrlController {

  private UrlService urlService;

  public UrlController(UrlService urlService) {
    this.urlService = urlService;
  }

  @PostMapping("/shorten")
  public ShortenUrlResponseDto shortenUrl(@RequestBody ShortenUrlRequestDto requestDto) {
    var responseDto = new ShortenUrlResponseDto();
    try {
      var shortenUrl = urlService.shortenUrl(requestDto.getOriginalUrl(), requestDto.getUserId());
      responseDto.setShortUrl(shortenUrl.getShortUrl());
      responseDto.setExpiresAt(shortenUrl.getExpiresAt());
      responseDto.setStatus(ResponseStatus.SUCCESS);
    } catch (Exception exception) {
      responseDto.setStatus(ResponseStatus.FAILURE);
    }
    return responseDto;
  }

  @PostMapping("/resolve")
  public ResolveShortenUrlResponseDto resolveShortenedUrl(
      @RequestBody ResolveShortenUrlRequestDto requestDto) {
    var responseDto = new ResolveShortenUrlResponseDto();
    try {
      var originalUrl = urlService.resolveShortenedUrl(requestDto.getShortenUrl());
      responseDto.setOriginalUrl(originalUrl);
      responseDto.setStatus(ResponseStatus.SUCCESS);
    } catch (Exception exception) {
      responseDto.setStatus(ResponseStatus.FAILURE);
    }
    return responseDto;
  }
}
