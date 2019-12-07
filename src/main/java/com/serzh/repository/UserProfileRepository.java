package com.serzh.repository;

import com.serzh.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
//public interface UserProfileRepository {

//    UserProfile getById(Integer id);
//    UserProfile update(Integer id);

//    @Modifying
//    @Query("update User u set u.active = false where u.lastLoginDate < :date")
//    void deactivateUsersNotLoggedInSince(@Param("date") LocalDate date);

//    @Modifying
//    @Query("update User u set u.firstname = ?1 where u.lastname = ?2")
//    int setFixedFirstnameFor(String firstname, String lastname);

    /*@Modifying
//    @Query("update User u set u.firstname = ?1 where u.lastname = ?2")
    @Query("INSERT INTO user_profile (user_id, info) \n" +
            "VALUES (1, 'A', 'X')\n" +
            "ON CONFLICT (user_id) DO UPDATE \n" +
            "  SET column_1 = excluded.column_1, \n" +
            "      column_2 = excluded.column_2;")
    int update(String firstname, String lastname);*/

}
