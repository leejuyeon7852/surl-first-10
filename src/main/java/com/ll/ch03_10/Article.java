package com.ll.ch03_10;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Article {
    @Id //primary Key라는 뜻
    @GeneratedValue(strategy = IDENTITY) //auto increment
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String body;
}
