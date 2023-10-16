package com.tpe.controller;

import com.tpe.domain.Teacher;
import com.tpe.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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


    @GetMapping//http://localhost:8080/teachers

    public ResponseEntity<List<Teacher>> getAllTeachers(){
      List<Teacher> teacherList =  teacherService.findAllTeachers();
      return new ResponseEntity<>(teacherList,HttpStatus.OK);
    }


    @GetMapping("/{id}")//http://localhost:8080/teachers/1
    public ResponseEntity<Teacher> findTeacherByIdUsingPathVariable(@PathVariable Long id){
       Teacher teacher= teacherService.getTeacherByid(id);
       return  new ResponseEntity<>(teacher,HttpStatus.OK);

    }

    @DeleteMapping("/{id}")//http://localhost:8080/teachers/1
    public ResponseEntity<String> deleteTeacher(@PathVariable("id") Long id){
        teacherService.deleteTeacherById(id);
        String message="Teacher with id  "+ id +" has been deleted ..";
        return  new ResponseEntity<>(message,HttpStatus.OK);
    }


    @GetMapping("/query")//http://localhost:8080/teachers/query?id=1
    public ResponseEntity<Teacher> findTeacherByIdUsingRequestParam(@RequestParam Long id){

      Teacher teacher = teacherService.getTeacherByid(id);
      return  new ResponseEntity<>(teacher,HttpStatus.OK);
    }

    // find the teacher by lastName

    @GetMapping("/lastName")//http://localhost:8080/teachers/lastName?lastName="turan"
    public ResponseEntity<List<Teacher>> findTeacherByLastName(@RequestParam String lastName){

        List<Teacher> teacher = teacherService.getTeacherByLastName(lastName);
        return  new ResponseEntity<>(teacher,HttpStatus.OK);
    }

    //update teacher by id

    @PutMapping("/{id}")///http://localhost:8080/teachers

    public ResponseEntity<Map<String,String>> updateTeacher(@Valid @PathVariable Long id ,@RequestBody Teacher teacher){

        teacherService.updateTeacherById(id,teacher);
        Map<String,String> response= new HashMap<>();
        response.put("message","Teacher  with id  " +id+ " has been updated successfully ..");
        response.put("status","true");
        return ResponseEntity.ok(response);

    }


}
