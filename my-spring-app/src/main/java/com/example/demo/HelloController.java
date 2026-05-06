package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String sayHello() {
        return "<h1>??????? ?????! ????? ?????? (Talknik) ????????? H2 ????????? ???? ???? ???!</h1>";
    }

    @GetMapping("/add")
    public String addUser(@RequestParam String name, @RequestParam String email) {
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        userRepository.save(u);
        return "???? " + name + " ????????? H2 ???????????? ????? ????!";
    }

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
