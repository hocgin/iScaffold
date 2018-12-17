package in.hocg.manager.controller;

/**
 * Created by hocgin on 2018/12/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.manager.service.ResourceService;
import in.hocg.manager.service.StaffService;
import in.hocg.manager.support.security.body.JwtToken;
import in.hocg.mybatis.basic.condition.GetCondition;
import in.hocg.mybatis.module.system.entity.Resource;
import in.hocg.mybatis.module.system.entity.Staff;
import in.hocg.scaffold.support.basis.BaseController;
import in.hocg.scaffold.support.http.Result;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.visola.spring.security.tokenfilter.TokenService;

import java.security.Principal;
import java.util.Arrays;

@RestController
@RequestMapping("/staff")
@AllArgsConstructor
public class StaffController extends BaseController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final StaffService staffService;
    private final ResourceService resourceService;
    
    /**
     * POST /staff/token
     * 登陆
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<Result> postToken(@RequestParam("username") String username,
                                            @RequestParam("password") String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                password
        );
        
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        String token = tokenService.generateToken(authentication);
        return Result.success(new JwtToken(token)).asResponseEntity();
    }
    
    /**
     * GET /staff/menu
     * 查找指定员工的菜单列表
     *
     * @return
     */
    @GetMapping("/menu")
    public ResponseEntity<Result> getMenu(Principal principal) {
        String username = principal.getName();
        Resource tree = resourceService.findResourceTreeByUsername(username);
        return Result.success(tree).asResponseEntity();
    }
    
    /**
     * GET /staff
     * 查找所有员工列表
     *
     * @param condition
     * @return
     */
    @GetMapping
    public ResponseEntity<Object> get(GetCondition condition) {
        IPage<Staff> all = staffService.page(condition);
        return Result.success(all).asResponseEntity();
    }
    
    /**
     * GET /staff/{id}
     * 查找员工详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> detail(@PathVariable("id") String id) {
        Staff result = staffService.getById(id);
        return Result.success(result).asResponseEntity();
    }
    
    
    /**
     * DELETE /staff
     * 批量删除
     *
     * @param id
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Object> delete(@Param("id") String... id) {
        boolean result = staffService.removeByIds(Arrays.asList(id));
        return Result.result(result).asResponseEntity();
    }
    
    /**
     * PUT /staff
     * 新增
     *
     * @return
     */
    @PutMapping
    public ResponseEntity<Object> put(Staff staff) {
        boolean result = staffService.save(staff);
        return Result.result(result).asResponseEntity();
    }
    
}
