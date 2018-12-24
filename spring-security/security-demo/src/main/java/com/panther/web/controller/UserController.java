package com.panther.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.panther.dto.User;
import com.panther.dto.UserQueryCondition;
import com.panther.exception.UserNotExistException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    /***
     * 获取登录用户的信息
     *
     * 参数放Authentication authentication返回
     * 返回的是存储的登录用户所有信息，权限，IP等
     * 参数放 @AuthenticationPrincipal UserDetails user 只有用户的信息
     * @param
     * @return
     */
    @GetMapping("/me")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails user) {
        return user;
    }


    @JsonView(User.UserSimpleView.class)
    @GetMapping
    @ApiOperation("用户查询服务")
    public List<User> selectAll(UserQueryCondition condition,@PageableDefault(page = 0, size = 10) Pageable pageable) {

        // toSting 反射工具
        System.out.println(ReflectionToStringBuilder.toString(condition,ToStringStyle.MULTI_LINE_STYLE));

        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
        userList.add(new User());
        return userList;
    }

    @JsonView(User.UserDetailView.class)
    @GetMapping("/{id:\\d+}") // 限制 id 只能是数字
    public User getInfo(@ApiParam("用户ID") @PathVariable String id) {
        //throw new UserNotExistException(id);
        System.out.println("进入getinfo服务");
        User user = new User();
        user.setUsername("tom");
        return user;
    }

    @PostMapping
    public User create(@Validated @RequestBody User user, BindingResult errors) {

        // Validated 拦截错误信息。
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        }

        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthDay());
        user.setId("1");
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public User update(@Validated @RequestBody User user, BindingResult errors) {

        // Validated 拦截错误信息。
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                String message = fieldError.getField()+" "+ error.getDefaultMessage();
                System.out.println(message);
            }
            );
        }
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthDay());
        user.setId("1");
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id){
        System.out.println(id);
    }
}









