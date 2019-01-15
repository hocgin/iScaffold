package in.hocg.mybatis.module.system.mapper;

import in.hocg.mybatis.basic.NodeMapper;
import in.hocg.mybatis.module.system.entity.Resource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * [权限模块] 资源表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2018-10-21
 */
@Mapper
public interface ResourceMapper extends NodeMapper<Resource> {
    /**
     * 查找对应用户的资源
     *
     * @param username
     * @return
     */
    List<Resource> selectMultiByUsername(String username);
    
}
