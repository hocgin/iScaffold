package in.hocg.mybatis.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.mybatis.basic.model.SuperModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * [权限模块] 角色-员工 关联表
 * </p>
 *
 * @author hocgin
 * @since 2018-10-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("role_staff")
public class RoleStaff extends SuperModel<RoleStaff> {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 角色 ID
     */
    @TableField(ROLE_ID)
    private String roleId;
    /**
     * 员工 ID
     */
    @TableField(STAFF_ID)
    private String staffId;
    
    
    public static final String ROLE_ID = "role_id";
    
    public static final String STAFF_ID = "staff_id";
    
}
