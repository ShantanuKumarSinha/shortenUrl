package com.shann.shortenurl.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "shorten-url-user")
@Data
public class User extends BaseModel{
    private String name;
    private String email;
    @Enumerated(EnumType.ORDINAL)
    private UserPlan userPlan;
}
