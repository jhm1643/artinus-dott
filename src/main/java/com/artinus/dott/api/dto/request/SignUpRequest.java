package com.artinus.dott.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@ApiModel(value = "사용자 등록 요청")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SignUpRequest {

    @ApiModelProperty(name = "이메일", required = true)
    @NotBlank(message = "이메일은 필수 값입니다.")
    @Email(message = "이메일 형식이어야 합니다.")
    private String email;

    @ApiModelProperty(name = "패스워드", required = true)
    @NotBlank(message = "패스워드는 필수 값입니다.")
    private String password;
}
