package in.hocg.mybatis.module.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.mybatis.basic.model.DefaultModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * [用户模块] 员工表
 * </p>
 *
 * @author hocgin
 * @since 2018-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("staff")
public class Staff extends DefaultModel<Staff> {
    private static final long serialVersionUID = 1L;
    
    /**
     * 昵称
     */
    @TableField(NICKNAME)
    private String nickname;
    /**
     * 用户名
     */
    @TableField(USERNAME)
    private String username;
    /**
     * 密码
     */
    @TableField(PASSWORD)
    private String password;
    /**
     * 头像
     */
    @TableField(AVATAR)
    private String avatar;
    /**
     * 性别 [女, 男]
     */
    @TableField(GENDER)
    private Integer gender;
    /**
     * 过期状态 [过期状态,正常状态]
     */
    @TableField(NON_EXPIRED)
    private Boolean nonExpired;
    /**
     * 锁定状态 [过期状态,正常状态]
     */
    @TableField(NON_LOCKED)
    private Boolean nonLocked;
    /**
     * 启用状态 [关闭状态,开启状态]
     */
    @TableField(ENABLED)
    private Boolean enabled;
    
    
    public static final String ACCOUNT = "account";
    
    public static final String NICKNAME = "nickname";
    
    public static final String USERNAME = "username";
    
    public static final String PASSWORD = "password";
    
    public static final String AVATAR = "avatar";
    
    public static final String GENDER = "gender";
    
    public static final String NON_EXPIRED = "non_expired";
    
    public static final String NON_LOCKED = "non_locked";
    
    public static final String ENABLED = "enabled";
    
}
