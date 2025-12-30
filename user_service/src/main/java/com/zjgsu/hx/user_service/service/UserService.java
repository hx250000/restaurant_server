package com.zjgsu.hx.user_service.service;

import com.zjgsu.hx.user_service.exception.ResourceConflictException;
import com.zjgsu.hx.user_service.exception.ResourceNotFoundException;
import com.zjgsu.hx.user_service.exception.UnauthorizedException;
import com.zjgsu.hx.user_service.model.User;
import com.zjgsu.hx.user_service.model.UserRole;
import com.zjgsu.hx.user_service.model.frontend.UserLogin;
import com.zjgsu.hx.user_service.model.frontend.UserRegister;
import com.zjgsu.hx.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    // 根据id，查不到用户时抛出异常
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("用户不存在或已删除！"));
    }

    // 根据username，查不到用户时返回 null（前端显示为空但不报错）
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
                //.orElseThrow(() -> new ResourceNotFoundException("用户未找到！"));
    }

    public List<User> findByUserNameKeyword(String keyword) {
        return userRepository.findByUserNameKeyword(keyword);
    }

    @Transactional
    public User register(UserRegister userReg) {

        checkInformation(userReg);

        Optional<User> user= userRepository.findByUsername(userReg.getUsername());
        User newUser = new User();
        // 1. 唯一性校验

        if (user.isPresent()) {// 用户名已存在
            User existingUser = user.get();
            Integer isdeleted = existingUser.getIsDeleted();
            if (isdeleted == 1) {// 逻辑删除状态，允许重新注册
                if (userRepository.existsByPhoneAndIsDeleted(userReg.getPhone(),0)) {
                    throw new ResourceConflictException("手机号已被注册！");
                }
                existingUser.setIsDeleted(0);
                existingUser.setPhone(userReg.getPhone());
                existingUser.setAddress(userReg.getAddress());
                existingUser.setPassword(encryptPassword(userReg.getPassword()));
                existingUser.setRole(userReg.getUserrole());
                existingUser.setUpdateTime(LocalDateTime.now());
                return userRepository.save(existingUser);
            }
            else{
                throw new ResourceConflictException("用户名已存在！");
            }
        }
        if (userRepository.existsByPhoneAndIsDeleted(userReg.getPhone(),0)) {
            throw new ResourceConflictException("手机号已被注册！");
        }
        // 2. 创建新用户
        newUser.setUsername(userReg.getUsername());
        newUser.setPhone(userReg.getPhone());
        newUser.setAddress(userReg.getAddress());
        newUser.setPassword(encryptPassword(userReg.getPassword()));
        newUser.setRole(userReg.getUserrole());
        newUser.setIsDeleted(0);
        newUser.setUpdateTime(LocalDateTime.now());

        return userRepository.save(newUser);
    }

    @Transactional
    public User updateUser(Long id, UserRegister updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("用户不存在或已删除！"));

        checkInformation(updatedUser);

        if (!existingUser.getUsername().equals(updatedUser.getUsername())) {
            // 检查新的用户名是否已存在
            if (userRepository.existsByUsernameAndIsDeleted(updatedUser.getUsername(),0)) {
                throw new ResourceConflictException("用户名已存在！");
            }
        }
        if (!existingUser.getPhone().equals(updatedUser.getPhone())) {
            // 检查新的手机号是否已存在
            if (userRepository.existsByPhoneAndIsDeleted(updatedUser.getPhone(),0)) {
                throw new ResourceConflictException("手机号已被注册！");
            }
        }
        // 更新字段
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(encryptPassword(updatedUser.getPassword()));
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setRole(updatedUser.getUserrole());
        existingUser.setUpdateTime(LocalDateTime.now());

        return userRepository.save(existingUser);
    }

    @Transactional
    public User deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("用户不存在或已删除！"));
        user.setIsDeleted(1);
        user.setUpdateTime(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User authenticateUser(UserLogin userLogin) {
        String username = userLogin.getUsername();
        String password = userLogin.getPassword();
        String encryptedPassword = encryptPassword(password);
        User user = userRepository.findUserByUsernameAndPassword(username, encryptedPassword);
        if (user == null) {
            throw new UnauthorizedException("用户名或密码错误，登录失败！");
        }
        return user;
    }

    public User authenticateAdmin(UserLogin userLogin) {
        String username = userLogin.getUsername();
        String password = userLogin.getPassword();
        String encryptedPassword = encryptPassword(password);
        User user = userRepository.findUserByUsernameAndPassword(username, encryptedPassword);
        if (user == null) {
            throw new UnauthorizedException("用户名或密码错误，登录失败！");
        }
        if (user.getRole()!= UserRole.ADMIN){
            throw new UnauthorizedException("非管理员角色不得登入管理端！");
        }
        return user;
    }

    public String encryptPassword(String password) {
        // 使用MD5进行加密（注意：MD5不适合用于生产环境中的密码存储）
        return org.springframework.util.DigestUtils.md5DigestAsHex(password.getBytes());
    }

    public void checkInformation(UserRegister userRegister){
        if (userRegister == null) {
            throw new ResourceConflictException("用户信息不能为空！");
        }

        log.info(userRegister.toString());

        // 1. 用户名校验
        String username = userRegister.getUsername();
        if (username == null || username.trim().isEmpty()) {
            throw new ResourceConflictException("用户名不能为空！");
        }

        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            throw new ResourceConflictException("用户名只能包含字母、数字和下划线！");
        }

        // 2. 密码校验
        String password = userRegister.getPassword();
        if (password == null || password.trim().isEmpty()) {
            throw new ResourceConflictException("密码不能为空！");
        }
        if (password.length() < 6) {
            throw new ResourceConflictException("密码长度不能少于 6 位！");
        }

        // 3. 手机号校验（中国大陆）
        String phone = userRegister.getPhone();
        if (phone == null || phone.trim().isEmpty()) {
            throw new ResourceConflictException("手机号不能为空！");
        }
        if (!phone.matches("^1[3-9]\\d{9}$")) {
            throw new ResourceConflictException("手机号格式不正确！");
        }

        // 4. 地址校验
        String address = userRegister.getAddress();
        if (address == null || address.trim().isEmpty()) {
            throw new ResourceConflictException("地址不能为空！");
        }

        // 5. 角色校验
        if (userRegister.getUserrole() == null) {
            throw new ResourceConflictException("用户角色不能为空！");
        }
    }

}
