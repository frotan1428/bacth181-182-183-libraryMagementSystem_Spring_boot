package com.tpe.service;

import com.tpe.domain.Book;
import com.tpe.domain.Teacher;
import com.tpe.dto.BookDTO;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TeacherService teacherService;

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Book is not found with id  : " + id));
    }


    public void deleteBookById(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public void updateBookByDTO(Long id, BookDTO bookDTO) {
       Book existBook = getBookById(id);

       //update filed of the exist book with new Value
        existBook.setTitle(bookDTO.getTitle());
        existBook.setAuthor(bookDTO.getAuthor());
        existBook.setPublicationDate(bookDTO.getPublicationDate());

        bookRepository.save(existBook);

    }


    @Transactional
    public ResponseEntity<Map<String, String>> addBookForTeacher(Long teacherId, Long bookId) {

        //step 1: find Teacher by id
       Teacher  teacher = teacherService.getTeacherByid(teacherId);
       //Step 2: find  Book By Id
        Optional<Book> optionalBook =  bookRepository.findById(bookId); // return null
        if (optionalBook.isEmpty()){
            Map<String,String> response=new HashMap<>();
            response.put("message","Book With Id "+bookId+ " does not Exist");
            response.put("success","false");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);// 404 NOT FOUND
        }
        Book book=optionalBook.get();
        //step 3  check if the book Already  Exist for Teacher T 1  B=1
       boolean bookExist = teacher.getBooks().stream().anyMatch(b->
                b.getId().equals(book.getId()));
        if (bookExist){
            Map<String,String> response=new HashMap<>();
            response.put("message","Book  Already exist for the teacher   With id "+ teacher );
            response.put("success","false");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);//400;
        }
        /// add the book for the Teacher return new response
        teacher.getBooks().add(book);
        Map<String,String> response=new HashMap<>();
        response.put("message","Book With id "+bookId +" has been added for the Teacher with Id : " +teacherId );
        response.put("success","true");

        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }
}
