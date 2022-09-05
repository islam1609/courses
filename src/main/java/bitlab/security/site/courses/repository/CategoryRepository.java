package bitlab.security.site.courses.repository;

import bitlab.security.site.courses.model.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<CourseCategory,Long> {
}
