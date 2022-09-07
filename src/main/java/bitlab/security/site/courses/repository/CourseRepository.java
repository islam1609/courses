package bitlab.security.site.courses.repository;

import bitlab.security.site.courses.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CourseRepository extends JpaRepository<Course, Long> {
    public List<Course> findAllByOrderByIdAsc();
}
