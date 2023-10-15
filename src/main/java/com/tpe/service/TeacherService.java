package com.tpe.service;

import com.tpe.domain.Teacher;
import com.tpe.exception.ConflictException;
import com.tpe.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher saveTeacher(Teacher teacher) {

       Teacher existTeacher= teacherRepository.findByEmail(teacher.getEmail());

       if (existTeacher!=null){
           throw  new ConflictException("Teacher with the same  " +teacher.getEmail()+" already exist");
       }
       return teacherRepository.save(teacher);
    }

}
