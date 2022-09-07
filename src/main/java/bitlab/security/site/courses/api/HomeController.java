package bitlab.security.site.courses.api;

import bitlab.security.site.courses.model.Course;
import bitlab.security.site.courses.model.CourseCategory;
import bitlab.security.site.courses.model.Role;
import bitlab.security.site.courses.model.User;
import bitlab.security.site.courses.repository.RoleRepository;
import bitlab.security.site.courses.repository.UserRepository;
import bitlab.security.site.courses.service.CourseService;
import bitlab.security.site.courses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/")
    public String indexPage(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "index";
    }

    @GetMapping(value = "/enter")
    public String vhodPage(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "login";
    }

    @GetMapping(value = "/register")
    public String register() {
        return "register";
    }

    @PostMapping(value = "/toregister")
    public String register(Model model,
                           @RequestParam(name = "user_email") String email,
                           @RequestParam(name = "user_full_name") String fullName,
                           @RequestParam(name = "user_password") String password,
                           @RequestParam(name = "user_re_password") String repassword){
        if(repassword.equals(password)){
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFullName(fullName);
            newUser.setPassword(password);
            userService.registerUser(newUser);
            if(newUser!=null)
                return "redirect:register?succes";
        }
        return "redirect:register?failed";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/updatePassword")
    public String updatePassword(@RequestParam(name = "old_password") String oldPassword,
                                 @RequestParam(name = "new_password") String newPassword,
                                 @RequestParam(name = "re_new_password") String repeatNewPassword){
        if(newPassword.equals(repeatNewPassword)){
            User updatedPassword = userService.updatePassword(getCurrentUser(), oldPassword, newPassword);
            if(updatedPassword!=null){
                return "redirect:/profile?success";
            }
        }
        return "redirect:/profile?error";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/profile")
    public String profilePage(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "profile";
    }

    @GetMapping(value = "/forbidden")
    public String forbiddenPage(Model model){
        model.addAttribute("currentUser", getCurrentUser());
        return "403";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/adminPanel")
    public String adminPanel(Model model){
        model.addAttribute("currentUser", getCurrentUser());
        return "admin";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/courses")
    public String editCoursePage(Model model){
        List<Course> allCourses = courseService.getAllCourses();
        List<CourseCategory> courseCategories = courseService.getAllCategories();
        model.addAttribute("allCategories",courseCategories);
        model.addAttribute("allCourses",allCourses);
        return "courses";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/addCourse")
    public String addCourse(@RequestParam(name = "name") String name,
                            @RequestParam(name = "description") String description,
                            @RequestParam(name = "price") int price,
                            @RequestParam(name = "rating") double rating,
                            @RequestParam(name = "categoryId") Long id){
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setPrice(price);
        course.setRating(rating);
        course.setUser(getCurrentUser());
        course.setCategory(courseService.getCategory(id));
        courseService.addCourse(course);
        return "redirect:/courses";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/details/{id}")
    public String details(@PathVariable(name = "id") Long id,
                          Model model){
        System.out.println("details");
        List<CourseCategory> courseCategories = courseService.getAllCategories();
        model.addAttribute("allCategories",courseCategories);
        Course course = courseService.getCourse(id);
        model.addAttribute("course",course);
        return "details";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/editCourse")
    public String editCourse(@RequestParam(name = "name") String name,
                            @RequestParam(name = "description") String description,
                            @RequestParam(name = "price") int price,
                            @RequestParam(name = "rating") double rating,
                            @RequestParam(name = "categoryId") Long id,
                             @RequestParam(name = "courseId") Long courseId){
        Course course = new Course();
        course.setId(courseId);
        course.setName(name);
        course.setDescription(description);
        course.setPrice(price);
        course.setRating(rating);
        course.setUser(getCurrentUser());
        course.setCategory(courseService.getCategory(id));
        courseService.addCourse(course);
        return "redirect:/courses";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/deleteCourse")
    public String delete(@RequestParam(name = "courseId") Long id){
        courseService.deleteCourse(id);
        System.out.println(id);
        return "redirect:/courses";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/news")
    public String news(Model model){
        model.addAttribute("user",getCurrentUser());
        return "news";
    }


    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            bitlab.security.site.courses.model.User user = (bitlab.security.site.courses.model.User) authentication.getPrincipal();
            return user;
        }
        return null;
    }
}
