package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.UserTestData.*;

public abstract class AbstractUserServiceTest extends AbstractServiceTest{
    @Autowired
    protected UserService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setup() {
        cacheManager.getCache("users").clear();
    }

    @Transactional
    @Test
    public void create() {
        User created = service.create(getNewUser());
        int newId = created.id();
        User newUser = getNewUser();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Transactional
    @Test
    public void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.USER)));
    }

    @Transactional
    @Test
    public void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Transactional
    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    public void get() {
        User user = service.get(USER_ID);
        USER_MATCHER.assertMatch(user, UserTestData.user);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void getByEmail() {
        User user = service.getByEmail("admin@gmail.com");
        USER_MATCHER.assertMatch(user, admin);
    }

    @Transactional
    @Test
    public void update() {
        User updated = getUpdatedUser();
        service.update(updated);
        USER_MATCHER.assertMatch(service.get(USER_ID), getUpdatedUser());
    }

    @Test
    public void getAll() {
        List<User> all = service.getAll();
        USER_MATCHER.assertMatch(all, admin, user);
    }
}
