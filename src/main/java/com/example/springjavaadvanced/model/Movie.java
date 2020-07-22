package com.example.springjavaadvanced.model;

import com.example.springjavaadvanced.validation.UniqueMovieTitle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    @NotBlank
    @UniqueMovieTitle
    private String title;
    @NotBlank
    @Size(max = 20)
    private String description;
    @Positive
    private int duration;

    @JsonIgnore
    @ManyToOne(targetEntity = Director.class, optional = false, cascade = CascadeType.PERSIST)
////    @JoinColumn(name = "director_id", insertable = false, updatable = false, nullable = false)
    private Director director;
}
