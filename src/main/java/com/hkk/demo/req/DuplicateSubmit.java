package com.hkk.demo.req;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 重复提交
 *
 * @author hukangkang
 * @since 2021/10/12
 */
@Getter
@Setter
public class DuplicateSubmit {

    @NotBlank
    private String data;
}
