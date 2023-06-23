package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userSzgn = new User("szgnmhmmd@gmail.com", "szgn2023", "Muhammed", "Sezgin");
        userSzgn.addRole(roleAdmin);
        User savedUser=repo.save(userSzgn);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRole() {
        User userBelma = new User("szgnblm@gmail.com","szgn2023","Belma","Sezgin");
        Role roleEditor=new Role(3);
        Role roleAssistant=new Role(5);
        userBelma.addRole(new Role(roleEditor.getId()));
        userBelma.addRole(new Role(roleAssistant.getId()));

        User savedUser=repo.save(userBelma);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(System.out::println);

    }

    @Test
    public void testGetUserById() {
        User userSzgn=repo.findById(1).get();
        System.out.println(userSzgn);
        assertThat(userSzgn).isNotNull();

    }

    @Test
    public void testUpdateUserDetails() {
        User userSzgn=repo.findById(1).get();
        userSzgn.setEnabled(true);
        User savedUser=repo.save(userSzgn);
    }

    @Test
    public void testUpdateUserRoles() {
        User userSzgn=repo.findById(2).get();
        Role roleEditor=new Role(3);
        Role roleSalesPerson=new Role(2);
        userSzgn.getRoles().remove(roleEditor);
        userSzgn.addRole(roleSalesPerson);
        repo.save(userSzgn);
    }
    @Test
    public void testDeleteUser() {
        Integer userId=2;
        repo.deleteById(userId);

    }

    @Test
    public void testFindUserByEmail() {
        User user =repo.findByEmail("szgnmhmmd@gmail.com");
        System.out.println(user );
        assertThat(user ).isNotNull();
    }

    @Test
    public void testCountById() {
        Integer id =1;
        Long countById=repo.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }
    @Test
    public void testDisableUser() {

        User user =repo.findById(39).get();

       user.setEnabled(false);

    }


    @Test
    public void testEnableUser() {

        User user =repo.findById(39).get();

        user.setEnabled(true);

    }


}

