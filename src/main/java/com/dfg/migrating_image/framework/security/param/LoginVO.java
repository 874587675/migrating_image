package com.dfg.migrating_image.framework.security.param;

import com.dfg.migrating_image.business.param.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginVO {
    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;
    
    /**
     * 用户信息
     */
    private UserVO userVO;


}
