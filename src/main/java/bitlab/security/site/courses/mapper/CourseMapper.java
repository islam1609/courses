package bitlab.security.site.courses.mapper;

import bitlab.security.site.courses.DTO.CourseDTO;
import bitlab.security.site.courses.model.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO toDto(Course course);
    Course toEntity(CourseDTO courseDTO);
    List<CourseDTO> toDtoList(List<Course> courseList);
}
