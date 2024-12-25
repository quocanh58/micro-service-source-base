//package com.company.gojob.account_service.controller;
//
//import com.company.gojob.account_service.repository.UserCredentialRepository;
//import com.company.gojob.account_service.service.UserCredentialService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.Mockito.*;
//
//class AuthControllerTest {
//    @Mock
//    UserCredentialRepository userCredentialRepository;
//
//    @InjectMocks
//    UserCredentialService userCredentialService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void whenGetInvalidOne_shouldThrowException(){
//        String invalidUserId = UUID.randomUUID().toString();
//        when(userCredentialRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> userCredentialService.getUserCredentialById(invalidUserId))
//                .isInstanceOf(RuntimeException.class); // Xử lý exception hợp lý
//
//        verify(userCredentialRepository).findById(any(UUID.class));
//    }
//}
