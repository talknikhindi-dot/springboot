package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/login-check")
    public String check() { return "talknik server is working fine!"; }

    @GetMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        String encoded = Base64.getEncoder().encodeToString(password.getBytes());
        jdbcTemplate.update("insert into users (name, email, password) values (?, ?, ?)", name, email.toLowerCase(), encoded);
        return "user " + name.toLowerCase() + " registered successfully!";
    }

    @GetMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        String encoded = Base64.getEncoder().encodeToString(password.getBytes());
        List<Map<String, Object>> users = jdbcTemplate.queryForList("select * from users where email = ? and password = ?", email.toLowerCase(), encoded);
        return !users.isEmpty() ? "login success!" : "login failed!";
    }

    @GetMapping("/users")
    public List<Map<String, Object>> getall(@RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return jdbcTemplate.queryForList("select id, name, email from users where name like ? or email like ?", "%" + search + "%", "%" + search + "%");
        }
        return jdbcTemplate.queryForList("select id, name, email from users");
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        jdbcTemplate.update("delete from users where id = ?", id);
        return "user deleted!";
    }
}
