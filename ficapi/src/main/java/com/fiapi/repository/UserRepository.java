package com.fiapi.repository;

import com.fiapi.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<UserModel, Long> {

    @Query("SELECT u FROM UserModel u WHERE u.email = :email")
    UserModel findByEmail(String email);

}
