package com.project.be_java_hisp_w29_g10.dto.request.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowerDto {
    private Long user_id;
    private String user_name;
}
