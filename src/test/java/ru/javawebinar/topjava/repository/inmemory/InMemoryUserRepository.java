package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;

import java.util.stream.Collectors;

import static ru.javawebinar.topjava.UserTestData.admin;
import static ru.javawebinar.topjava.UserTestData.user;


@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {
    public void init() {
        map.clear();
        put(user);
        put(admin);
        counter.getAndSet(UserTestData.ADMIN_ID + 1);
    }

    @Override
    public List<User> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .toList();
    }

    @Override
    public User getByEmail(String email) {
        Objects.requireNonNull(email, "email must not be null");
        if (email == null)
            throw new IllegalArgumentException("Email == null");
        return getCollection().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }
}
