package com.zjgsu.hx.user_service.repository;

import com.zjgsu.hx.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.isDeleted = 0")
    List<User> findAll();

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.isDeleted = 0")
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.isDeleted = 0")
    Optional<User> findById(Long id);

    @Query("SELECT u FROM User u WHERE u.username LIKE CONCAT('%', :keyword, '%') AND u.isDeleted = 0")
    List<User> findByUserNameKeyword(@Param("keyword") String keyword);

    boolean existsByPhone(String phone);

    boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password AND u.isDeleted = 0")
    User findUserByUsernameAndPassword(String username, String password);

}
