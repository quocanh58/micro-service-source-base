package com.company.gojob.account_service.service;

import com.company.gojob.account_service.model.UserCredential;
import com.company.gojob.account_service.repository.UserCredentialRepository;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.UUID;

@WebMvcTest
public class UserServiceTest {

    @Mock
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private UserCredentialService userCredentialService;


    private UserCredential userCreateRequest() {
        String id = UUID.randomUUID().toString();
        UserCredential entity = new UserCredential();
        entity.setId(id);
        entity.setUsername("huukhuong4");
        entity.setPassword("password");
        entity.setEmail("test@test.com");

        return entity;
    }

    private UserCredential mockUserCreate() {
        String id = UUID.randomUUID().toString();
        UserCredential entity = new UserCredential();
        entity.setId(id);
        entity.setUsername("username");
        entity.setPassword("password");
        entity.setEmail("test@test.com");

        return entity;
    }

    @Test
    public void testCreateUser_WhenUserAlreadyExist() {

        UserCredential userCredential = userCreateRequest();

        Mockito.when(userCredentialRepository.findUserCredentialByUsername(userCredential.getUsername())).thenReturn(userCredential);

        Assertions.assertThrows(BadRequestException.class, () -> userCredentialService.saveEntity(userCredential));
    }


    @Test
    public void testCreateUser() {

    }

}
