package com.tpe.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="tbl_book")

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "title Can Not Be null")
    @NotBlank(message = "title can not be White Space..")
    @Size(min = 4,max = 25 ,message = "title '${validatedValue}' must be between : {min} and {max}  ...")//2
    private String title;

    private String author;

    @Column(nullable = false)
    private String publicationDate;

    @ManyToMany(mappedBy = "books",cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    private List<Teacher> teachers = new ArrayList<>();


}
