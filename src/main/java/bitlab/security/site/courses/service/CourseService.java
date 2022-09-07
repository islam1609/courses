package bitlab.security.site.courses.service;

import bitlab.security.site.courses.DTO.CourseDTO;
import bitlab.security.site.courses.mapper.CourseMapper;
import bitlab.security.site.courses.mapper.UserMapper;
import bitlab.security.site.courses.model.Course;
import bitlab.security.site.courses.model.CourseCategory;
import bitlab.security.site.courses.repository.CategoryRepository;
import bitlab.security.site.courses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private  CategoryRepository categoryRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserMapper userMapper;

    public List<Course> getAllCourses(){
        return courseRepository.findAllByOrderByIdAsc();
    }

    public List<CourseDTO> getAllCoursesDTO(){
        return courseMapper.toDtoList(courseRepository.findAll());
    }

    public List<CourseCategory> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Course addCourse(Course course){
        return courseRepository.save(course);
    }

    public CourseCategory getCategory(Long id){
        return categoryRepository.getReferenceById(id);
    }

    public Course getCourse(Long id){
        return courseRepository.getReferenceById(id);
    }

    public CourseDTO getCourseDTO(Long id){
        return courseMapper.toDto(courseRepository.getReferenceById(id));
    }

    public void deleteCourse(Long id){
         courseRepository.deleteById(id);
    }

}
