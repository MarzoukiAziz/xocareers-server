package com.xoauto.xocareers_user.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Authority {
    @Id
    @Column(name = "authority_id")
    @GeneratedValue
    int id;

    @Column(unique = true)
    private String authority;
}
