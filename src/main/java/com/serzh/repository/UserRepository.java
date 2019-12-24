package com.serzh.repository;

import com.serzh.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    String USERS_BY_NAME_CACHE = "usersByName";
    String USERS_BY_EMAIL_CACHE = "usersByEmail";
    String USERS_CACHE = "users";

    @Query(value = "SELECT * FROM user WHERE state = 'DELETED'", nativeQuery = true)
    List<User> findAllStatusDeletedNativeQuery();

    @Query("SELECT u FROM User u WHERE u.state = 'DELETED'")
    List<User> findAllStatusDeletedQuery();

  /*  @Query(value = "SELECT * FROM USER WHERE LASTNAME = ?1",
            countQuery = "SELECT count(*) FROM USER WHERE LASTNAME = ?1",
            nativeQuery = true)
    Page<User> findByLastname(String lastname, Pageable pageable);*/

/*    @Query(value = "SELECT * FROM Users ORDER BY id \n-- #pageable\n",
            countQuery = "SELECT count(*) FROM Users",
            nativeQuery = true)
    Page<User> findAllUsersWithPagination(Pageable pageable);*/


    /*@Query("SELECT u FROM User u WHERE u.status = :status and u.name = :name")
User findUserByUserStatusAndUserName(@Param("status") Integer userStatus,
  @Param("name") String userName);*/


}
