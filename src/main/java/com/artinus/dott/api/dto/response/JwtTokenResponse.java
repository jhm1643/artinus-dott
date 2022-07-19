package com.artinus.dott.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel("jwt 토큰")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenResponse {

    @ApiModelProperty("jwt access token")
    private String accessToken;

    @ApiModelProperty("jwt refresh token")
    private String refreshToken;
}
