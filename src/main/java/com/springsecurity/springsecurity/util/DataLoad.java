package com.springsecurity.springsecurity.util;

import com.springsecurity.springsecurity.model.Role;
import com.springsecurity.springsecurity.model.User;
import com.springsecurity.springsecurity.repository.RoleRepository;
import com.springsecurity.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoad implements ApplicationRunner {


    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public DataLoad(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Role roleAdmin = Role.builder().id(1L).role_name("ROLE_ADMIN").build();
        Role roleUser = Role.builder().id(2L).role_name("ROLE_USER").build();

        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);

        List<Role> adminRoleList = new ArrayList<>();
        adminRoleList.add(roleAdmin);

        User admin = User.builder().id(1L).name("huseyin").password("{noop}1234").roles(adminRoleList).build();
        userRepository.save(admin);

        List<Role> userRoleList = new ArrayList<>();
        userRoleList.add(roleUser);

        User user = User.builder().id(2L).name("ali").password("{noop}1234").roles(userRoleList).build();
        userRepository.save(user);

        System.out.println("--  Roles  --");
        roleRepository.findAll().forEach(role -> System.out.println(role));
        System.out.println("--  Users  --");
        userRepository.findAll().forEach(System.out::println);
    }
}
