package com.project.be_java_hisp_w29_g10.service;

import com.project.be_java_hisp_w29_g10.dto.ResponseMessageDto;

public interface IUserService {
    ResponseMessageDto followSeller(Long userId, Long userIdToFollow);
}
