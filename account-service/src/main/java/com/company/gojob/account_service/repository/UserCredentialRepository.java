package com.company.gojob.account_service.repository;

import com.company.gojob.account_service.model.UserCredential;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM user_credential")
    List<UserCredential> findAllUserCredentials();

    @Query(nativeQuery = true, value = "SELECT * FROM user_credential")
    Page<UserCredential> findAllUserCredentials(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM user_credential AS u WHERE u.id = :id LIMIT 1 ")
    UserCredential findUserCredentialById(@Param("id") String id);

    @Query(value =
                    "SELECT * FROM user_credential AS a " +
                    "WHERE (:typeSearch = 'email' AND a.email LIKE %:valueSearch%) " +
                    "OR (:typeSearch = 'address' AND a.address LIKE %:valueSearch%) ",
            nativeQuery = true)
    Page<UserCredential> getAllUserCredentialSearch(Pageable pageable, String typeSearch, String valueSearch);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user_credential AS a SET a.email = :email, a.username = :username, a.updated_at = NOW() WHERE a.id = :id", nativeQuery = true)
    int updateUserCredentialById(@Param("id") String id, @Param("email") String email, @Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user_credential AS a SET a.deleted_at, a.deleted_by WHERE a.id = :id", nativeQuery = true)
    int deleteUserCredentialById(@Param("id") String id);

    UserCredential findUserCredentialByUsername(String username);

    UserCredential findUserCredentialByUsernameOrEmail(String username, String email);

    UserCredential findUserCredentialByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);
}
