package com.shopme.admin.user;

import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService service;


    @GetMapping("/users")
    public String listAll(Model model) {
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        List<Role> listRoles = service.listRoles();
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle","Create New User");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes,
    @RequestParam("image")MultipartFile multipartFile) throws IOException {
        System.out.println(user);
        System.out.println(multipartFile.getOriginalFilename());
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String uploadDir="user-photos";

        FileUploadUtil.saveFile(uploadDir, fileName,multipartFile);

        service.save(user);
        redirectAttributes.addFlashAttribute("message", "User saved successfully");
        return "redirect:/users";
    }


    @GetMapping("users/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes,
                           Model model) {

        try {
            List<Role> listRoles = service.listRoles();
            User user = service.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle","Edit User(ID: " + id +")");
            model.addAttribute("listRoles", listRoles);
            return "user_form";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",  e.getMessage());
        }
        return "redirect:/users";

    }
    @GetMapping("users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "User id "+id+" deleted successfully");

        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",  e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("users/{id}/enabled/{status}")
    public String updateuserEnabledStatus(@PathVariable("id") Integer id,
                                          @PathVariable("status") boolean enabled,
                                          RedirectAttributes redirectAttributes) throws UserNotFoundException {
        service.updateUserEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = " The User id " + id + " has been " + status;
       redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users";
    }

}
