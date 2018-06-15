package orca.fun.springbootrestful.service;

import lombok.extern.slf4j.Slf4j;
import orca.fun.springbootrestful.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author orca
 * @date 2018/6/14
 */
@Slf4j
@Service
public class StudentService {
    private static final List<Student> STUDENTS;

    static {
        Student student1 = new Student();
        student1.setId(1);
        student1.setLevel(1);
        student1.setName("jack");
        student1.setAge(12);
        Student student2 = new Student();
        student2.setLevel(2);
        student2.setId(2);
        student2.setName("james");
        student2.setAge(14);
        Student student3 = new Student();
        student3.setLevel(3);
        student3.setId(3);
        student3.setName("tom");
        student3.setAge(18);
        STUDENTS = Stream.of(student1, student2, student3).collect(Collectors.toList());
    }

    public List<Student> getAllStudents() {
        return STUDENTS;
    }

    public Optional<Student> getStudentById(int id) {
        return STUDENTS.stream().filter(student -> student.getId() == id).findFirst();
    }


    public Student addStudent(Student studentFromRequest) {
        STUDENTS.add(studentFromRequest);
        return studentFromRequest;
    }

    public void deleteStudentById(int id) {
        STUDENTS.removeIf(student -> student.getId() == id);
    }
}
