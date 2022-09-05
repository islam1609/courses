package bitlab.security.site.courses.service;

import bitlab.security.site.courses.model.Role;
import bitlab.security.site.courses.model.User;
import bitlab.security.site.courses.repository.RoleRepository;
import bitlab.security.site.courses.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findAllByEmail(email);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("USE NOT FOUND");
        }
    }

    public User registerUser(User user){
        User userCheck = userRepository.findAllByEmail(user.getEmail());
        if(userCheck==null){
            Role userRole = roleRepository.findAllByRole("ROLE_USER");
            ArrayList<Role> roles = new ArrayList<>();
            roles.add(userRole);
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        return null;
    }

    public User updatePassword(User user, String oldPass, String newPass){
        User currentUser = userRepository.findById(user.getId()).orElse(null);
        if(currentUser!=null){
            if(passwordEncoder.matches(oldPass, currentUser.getPassword()))
            currentUser.setPassword(passwordEncoder.encode(newPass));
            return userRepository.save(currentUser);
            }
        return null;
        }
    }


