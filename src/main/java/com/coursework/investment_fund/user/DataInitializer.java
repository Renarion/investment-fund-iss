package com.coursework.investment_fund.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository repo;
    private final PasswordEncoder encoder;

    public DataInitializer(AppUserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        upsertUser("admin", "admin", Role.ADMIN);
        upsertUser("manager", "manager", Role.MANAGER);
    }

    private void upsertUser(String username, String rawPassword, Role role) {
        AppUser user = repo.findByUsername(username).orElseGet(AppUser::new);
        user.setUsername(username);
        user.setPasswordHash(encoder.encode(rawPassword));
        user.setRole(role);
        user.setEnabled(true);
        repo.save(user);
    }
}