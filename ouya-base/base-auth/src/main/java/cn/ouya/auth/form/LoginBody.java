package cn.ouya.auth.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录对象
 *
 * @author Lion Li
 */
@Data
@NoArgsConstructor
public class LoginBody {

    /**
     * 用户名
     */
    @NotBlank(message = "{user.username.not.blank}")
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "{user.password.not.blank}")
    private String password;

}
