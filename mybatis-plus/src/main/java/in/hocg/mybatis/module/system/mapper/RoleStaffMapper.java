package in.hocg.mybatis.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.mybatis.module.system.entity.Role;
import in.hocg.mybatis.module.system.entity.RoleStaff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * <p>
 * [权限模块] 角色-员工 关联表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2018-10-21
 */
@Mapper
public interface RoleStaffMapper extends BaseMapper<RoleStaff> {
    
    /**
     * 使用员工ID来查找其关联的角色
     *
     * @param id
     * @return
     */
    Collection<Role> selectMultiByStaffId(@Param("id") String id);
}
