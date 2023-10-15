package com.tpe.controller;

import com.tpe.domain.Teacher;
import com.tpe.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("teachers")//http://localhost:8080/teachers
public class TeacherController {


    @Autowired
    private TeacherService teacherService;


    @PostMapping//http://localhost:8080/teachers

    public ResponseEntity<Map<String,String>> saveTeacher(@Valid @RequestBody Teacher teacher){

       Teacher teacher1=  teacherService.saveTeacher(teacher);

       Map<String,String> response= new HashMap<>();
       response.put("message","Teacher has been saved successfully ..");
       response.put("status","true");
       return  new ResponseEntity<>(response, HttpStatus.CREATED);//201
    }



}
