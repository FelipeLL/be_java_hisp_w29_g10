package com.project.be_java_hisp_w29_g10.repository;

import com.project.be_java_hisp_w29_g10.dto.ResponseMessageDto;
import com.project.be_java_hisp_w29_g10.entity.Follow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface IFollowRepository {
    Follow saveFollow(Long userId, Long userIdToFollow);
    Follow removeFollow(Follow followRelation);
    Optional<Follow> getFollowRelation(Long userId, Long userIdToFollow);
}
