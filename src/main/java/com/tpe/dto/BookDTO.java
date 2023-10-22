package com.tpe.dto;

import com.tpe.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {


    private Long id;

    @NotNull(message = "title Can Not Be null")
    @NotBlank(message = "title can not be White Space..")
    @Size(min = 4,max = 25 ,message = "title '${validatedValue}' must be between : {min} and {max}  ...")//2
    private String title;
    private String author;
    private String publicationDate;

    public BookDTO(Book book) {
        this.id=book.getId();
        this.title=book.getTitle();
        this.author=book.getAuthor();
        this.publicationDate=book.getPublicationDate();
    }


}
