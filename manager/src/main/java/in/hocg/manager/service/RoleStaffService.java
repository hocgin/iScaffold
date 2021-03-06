package in.hocg.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import in.hocg.mybatis.module.system.entity.Role;
import in.hocg.mybatis.module.system.entity.RoleStaff;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 * [权限模块] 角色-员工 关联表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2018-10-21
 */
public interface RoleStaffService extends IService<RoleStaff> {
    
    /**
     * 查找 该员工 分配到的所有角色
     * @param id
     * @return
     */
    Collection<Role> findByAllRoleUseStaffId(String id);
    
    /**
     * 移除该员工所有角色关联
     * @param id
     */
    int deleteAllWithStaffId(Serializable id);
    
    /**
     * 对该员工新增角色
     * @param staffId
     * @param rolesId
     */
    void insertRoles(String staffId, String[] rolesId);
}
