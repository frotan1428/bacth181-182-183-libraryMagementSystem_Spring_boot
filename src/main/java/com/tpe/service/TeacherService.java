package com.tpe.service;

import com.tpe.domain.Teacher;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Teacher> findAllTeachers() {

      List<Teacher> teacherList = teacherRepository.findAll();
      if (teacherList.isEmpty()){
          throw new ResourceNotFoundException("Teacher List is Empty....");
      }
      return teacherList;
    }

    public Teacher getTeacherByid(Long id) {
        return teacherRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Teacher is not Fund with Id : "+id));
    }

    public void deleteTeacherById(Long id) {
       Teacher teacher = getTeacherByid(id);
       teacherRepository.delete(teacher);
    }


    public List<Teacher> getTeacherByLastName(String lastName) {
        return teacherRepository.findByLastName(lastName);
    }


    public void updateTeacherById(Long id, Teacher teacher) {
        // we need to check the teacher is exist or not
        // check email

        //step 1 : find teacher by id
         Teacher exisTeacher =   getTeacherByid(id);


         //step 2 check email
         //nikzad2@gmail.com              nikzad1@gmail.com
         if (!exisTeacher.getEmail().equals(teacher.getEmail())){
            Teacher teacherWithUpdateEmail = teacherRepository.findByEmail(teacher.getEmail());

            if (teacherWithUpdateEmail!=null){
                throw  new ConflictException("Email is exist for another Teacher ");
            }
         }
         //step update teacher

         exisTeacher.setName(teacher.getName());
         exisTeacher.setLastName(teacher.getLastName());
         exisTeacher.setEmail(teacher.getEmail());
         exisTeacher.setPhoneNumber(teacher.getPhoneNumber());
         exisTeacher.setBooks(teacher.getBooks());
         teacherRepository.save(exisTeacher);
    }


}
