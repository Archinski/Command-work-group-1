package com.example.bank.repository;



import com.example.bank.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserReposit extends JpaRepository<User, Long> {
   List<User> findByFirstNameAndLastName(String firstName, String lastName);
    User findByUsername(String username);
    User findByFirstName(String firstName);
}
