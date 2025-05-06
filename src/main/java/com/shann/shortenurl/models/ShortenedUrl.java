package com.shann.shortenurl.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class ShortenedUrl extends BaseModel{
    private String originalUrl;
    private String shortUrl;
    private long expiresAt;
    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;
}
