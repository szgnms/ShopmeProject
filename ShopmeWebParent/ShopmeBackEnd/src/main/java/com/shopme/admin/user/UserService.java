package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return (List<User>) userRepo.findAll();
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepo.findAll();

    }
    public void save(User user) {
        boolean isUpdatinUser= (user.getId()!=null);
        if(isUpdatinUser) {
          User existinguser=userRepo.findById(user.getId()).get();
          if (user.getPassword().isEmpty()) {
              user.setPassword(existinguser.getPassword());
          }else{
              encodePassword(user);
          }
        }else {
            encodePassword(user);
        }

        userRepo.save(user);
    }
    private void encodePassword(User user) {
        String encodedPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

    }
    public boolean isEmailUnique(Integer id,String email) {
        User userByEmail=userRepo.findByEmail(email);
        if(userByEmail==null) return true ;

        boolean isCreatingNew = (id == null);
        if(isCreatingNew) {
           if (userByEmail!=null)  return false;
        }else {
               if ( userByEmail.getId()!= id){
                   return false;
               }
           }

        return true;
    }

    public User get(Integer id) throws UserNotFoundException {
        try {
            return userRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long countById=userRepo.countById(id);
        if(countById==null||countById==0) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
        userRepo.deleteById(id);
    }
    public void updateUserEnabledStatus(Integer id,boolean enabled) throws UserNotFoundException {
        Long countById=userRepo.countById(id);
        if(countById==null||countById==0) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
        User existinguser=userRepo.findById(id).get();
        existinguser.setEnabled(enabled);

    }


}