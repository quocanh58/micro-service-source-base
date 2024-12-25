package com.company.gojob.account_service.mapper;

import com.company.gojob.account_service.model.UserCredential;
import com.company.gojob.account_service.payload.request.UserCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserCredentialMapper {
    UserCredentialMapper INSTANCE = Mappers.getMapper(UserCredentialMapper.class);

    UserCreateRequest toDTO(UserCredential userCredential);

    UserCredential toEntity(UserCreateRequest userCreateRequest);
}
