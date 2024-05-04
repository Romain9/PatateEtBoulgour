package com.example.PatateEtBoulgour.services;
import com.example.PatateEtBoulgour.entities.Activity;

import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    public ModelAndView getUserListModelAndView() {
        List<User> users = userRepository.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin/userlist");
        return modelAndView;
    }

    public ModelAndView getNewUserFormModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/addNewUser");
        return modelAndView;
    }

    public void removeActivity(User user, Activity activity) {
       user.removeActivity(activity);
       userRepository.saveAndFlush(user);
    }

    public void addActivity(User user, Activity activity) {
        user.addActivity(activity);
        userRepository.saveAndFlush(user);
    }

    public void createUser(User user) {
        userRepository.saveAndFlush(user);
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && PasswordService.matches(password, user.getPassword()))
            return user;

        return null;
    }

    /**
     * Permet de récupérer l'utilisateur connecté.
     * @return L'utilisateur s'il est connecté, null sinon.
     */
    public User getCurrentUser() {
        if(session == null)
            return null;
        Long userId = (Long) session.getAttribute("userId");
        if(userId == null)
            return null;
        return userRepository.findById(userId).orElse(null);
    }

    public boolean hasRole(String role) {
        return getCurrentUser().getRole().isHigherOrEqualThan(role);
    }

    public boolean isLoggedIn() {
        return getCurrentUser() != null;
    }
}
