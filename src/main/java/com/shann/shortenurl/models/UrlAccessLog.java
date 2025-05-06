package com.shann.shortenurl.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class UrlAccessLog extends BaseModel{
    @ManyToOne
    @JoinColumn(name = "shortened_url_id", referencedColumnName = "id")
    private ShortenedUrl shortenedUrl;
    private long accessedAt;
}
