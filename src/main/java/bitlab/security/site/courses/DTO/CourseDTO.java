package bitlab.security.site.courses.DTO;

import bitlab.security.site.courses.model.CourseCategory;
import lombok.Data;

@Data
public class CourseDTO {
    private Long id;
    private String name;
    private String description;
    private int price;
    private double rating;
    private UserDTO user;
    private CourseCategory category;
}
