package com.shopme.admin.user;

import com.shopme.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

      User findByEmail(String email);


       Long countById(Integer id);

    Page<User> findAll(Pageable pageable);
}
