package bitlab.security.site.courses.rest;

import bitlab.security.site.courses.DTO.CourseDTO;
import bitlab.security.site.courses.model.Course;
import bitlab.security.site.courses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/courses")
@CrossOrigin
public class CoursesController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses(){
        return new ResponseEntity<>(courseService.getAllCoursesDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(courseService.getCourseDTO(id),HttpStatus.OK);
    }

}
