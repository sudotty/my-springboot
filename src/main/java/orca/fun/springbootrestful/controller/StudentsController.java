package orca.fun.springbootrestful.controller;

import lombok.val;
import orca.fun.springbootrestful.entity.Student;
import orca.fun.springbootrestful.exception.AllReadyExistsStudentException;
import orca.fun.springbootrestful.exception.NoSuchStudentException;
import orca.fun.springbootrestful.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author orca
 * @date 2018/6/14
 */
@RestController
@RequestMapping(value = "/v1/students", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StudentsController {
    @Resource
    StudentService studentService;

    /**
     * 查询所有学生
     *
     * @return 所有学生
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    /**
     * 根据Id查询学生
     *
     * @param id 忽略大小写
     * @return 学生信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentByName(@PathVariable int id) {
        return studentService.getStudentById(id).map(ResponseEntity::ok).orElseThrow(() -> new NoSuchStudentException(id));
    }

    /**
     * 新增学生
     *
     * @param studentFromRequest 学生对象
     * @return 新增的学生的信息
     */
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student studentFromRequest) {
        if (studentService.getStudentById(studentFromRequest.getId()).isPresent()) {
            throw new AllReadyExistsStudentException(studentFromRequest.getId());
        }
        return new ResponseEntity<>(studentService.addStudent(studentFromRequest), HttpStatus.CREATED);
    }

    /**
     * 修改学生信息
     *
     * @param studentFromRequest 学生对象
     * @return 修改的学生的信息
     */
    @PutMapping
    public ResponseEntity<Student> modifyStudent(@RequestBody Student studentFromRequest) {
        if (studentService.getStudentById(studentFromRequest.getId()).isPresent()) {
            throw new AllReadyExistsStudentException(studentFromRequest.getId());
        }
        val student = studentService.addStudent(studentFromRequest);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }


    /**
     * 根据Id删除学生
     *
     * @param id 学生姓名 忽略大小写
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable int id) {
        return studentService.getStudentById(id).map(p -> {
            studentService.deleteStudentById(id);
            return ResponseEntity.noContent().build();
        }).orElseThrow(() -> new NoSuchStudentException(id));
    }

    @GetMapping("/async")
    public Callable<String> helloWorldAsync() {
        return () -> "async: Hello World";
    }

}
