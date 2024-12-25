package com.company.gojob.account_service;

import com.company.gojob.account_service.model.UserCredential;
import com.company.gojob.account_service.repository.UserCredentialRepository;
import com.company.gojob.common_service.payload.response.BaseResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountServiceApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    private UserCredentialRepository userCredentialRepository; // Mock repository

//    @Test
//    public void getUserByIdWithStatus200() {
//        // Giả lập dữ liệu trả về từ repository (nếu cần)
//        String userId = "6f5b8462-5fb8-476e-8764-895538d40157";
//        UserCredential mockUserCredential = new UserCredential();
//        mockUserCredential.setId(userId);
//        mockUserCredential.setUsername("testuser");
//
//        // Giả lập repository trả về đối tượng mock
//        when(userCredentialRepository.findUserCredentialById(userId)).thenReturn(mockUserCredential);
//
//        // Gọi API bằng TestRestTemplate
//        ResponseEntity<?> response = restTemplate.getForEntity("/api/v1/user/" + userId, ResponseEntity.class);
//
//        var responses =  BaseResponse.success("", response, null);
//
//        // Kiểm tra mã trạng thái và nội dung trả về
//        assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responses).isEqualTo("Get Success");
//
//        // Kiểm tra status code trong BaseResponse
//        assertThat(responses.getStatusCode()).isEqualTo(200);
//    }

    @Test
    void contextLoads() {
    }

}
