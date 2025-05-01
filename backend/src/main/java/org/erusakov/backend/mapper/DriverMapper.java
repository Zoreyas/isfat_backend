package org.erusakov.backend.mapper;

import lombok.RequiredArgsConstructor;
import org.erusakov.backend.controller.request.user.CreateDriverRequest;
import  org.erusakov.backend.controller.response.user.DriverResponse;
import org.erusakov.backend.entities.user.DriverEntity;
import  org.erusakov.backend.entities.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverMapper {

    private final UserMapper userMapper;

    public DriverEntity toEntity(CreateDriverRequest createDriverRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(createDriverRequest.userId());
        return DriverEntity.builder()
                .user(userEntity)
                .phone(createDriverRequest.phone())
                .licenseNumber(createDriverRequest.licenseNumber())
                .build();
    }

    public DriverResponse toResponse(DriverEntity driverEntity) {
        return new DriverResponse(
                driverEntity.getUser().getId(),
                driverEntity.getPhone(),
                driverEntity.getLicenseNumber()
        );
    }

}
