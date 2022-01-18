package ru.javawebinar.topjava.service.jpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.service.UserService;

import javax.persistence.PersistenceException;

import static org.junit.Assert.assertThrows;

@ActiveProfiles("jpa")
public class JpaUserServiceTest extends AbstractUserServiceTest {
    @Autowired
    private UserService service;

    @Transactional
    @Test
    @Override
    public void duplicateMailCreate() {
        assertThrows(PersistenceException.class, () ->
                service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.USER)));
    }
}
