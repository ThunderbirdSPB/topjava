package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;
import static ru.javawebinar.topjava.UserTestData.*;


class InMemoryAdminRestControllerTest {
    private static final Logger log = LoggerFactory.getLogger(InMemoryAdminRestControllerTest.class);

    private static ConfigurableApplicationContext appCtx;
    private static AdminRestController controller;
    private static InMemoryUserRepository repository;


    @BeforeAll
    static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/inmemory.xml");
        log.info("\n{}\n", Arrays.toString(appCtx.getBeanDefinitionNames()));
        controller = appCtx.getBean(AdminRestController.class);
        repository = appCtx.getBean(InMemoryUserRepository.class);
    }

    @AfterAll
    static void afterClass() {
//        May cause during JUnit "Cache is not alive (STATUS_SHUTDOWN)" as JUnit share Spring context for speed
//        http://stackoverflow.com/questions/16281802/ehcache-shutdown-causing-an-exception-while-running-test-suite
//        appCtx.close();
    }

    @BeforeEach
    public void setup() {
        // re-initialize
        repository.init();
    }

    @Test
    void deleteExistingUser() {
        controller.delete(USER_ID);
        Assertions.assertNull(repository.get(USER_ID));
    }

    @Test
    void deleteNotExistingUser() {
        assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
    }

    @Test
    public void getAll() {
        assertNotNull(controller.getAll());
    }

    @Test
    public void getAdminsId() {
        User admin = controller.get(ADMIN_ID);
        assertNotNull(admin, "user by id is null");
        assertEquals(ADMIN_ID, admin.getId().intValue());
    }

    @Test
    @Disabled
    //Пока нет возможности проверить наличие нововсозданного пользователя
    public void createNew() {
        String email = "testuser@yandex.ru";
        User userTest = new User(null, "UserTest", email, "password",2000, Role.USER);
        controller.create(userTest);
        assertEquals(userTest, controller.getByMail(email));
    }

    @Test
    public void createWithPredefinedId() {
        int userTestId = Integer.MAX_VALUE;
        User userTest = new User(userTestId, "UserTest", "testuser@yandex.ru", "password",2000, Role.USER);
        assertThrows(IllegalArgumentException.class, () -> controller.create(userTest));
    }

    @Test
    public void update() {
        String newEmail = "Testing.email";
        String newPassword = "Testting.password";
        boolean newEnabled = false;
        Date newRegistered = new Date();
        int newCaloriesPerDay = 10000;

        User user = controller.get(USER_ID);
        user.setEmail(newEmail);
        user.setEnabled(newEnabled);
        user.setPassword(newPassword);
        user.setRegistered(newRegistered);
        user.setCaloriesPerDay(newCaloriesPerDay);

        controller.update(user, USER_ID);
        assertEquals(user, controller.get(USER_ID));
    }

    @Test
    public void getByExistingMail() {
        User admin = controller.get(ADMIN_ID);
        assertEquals(admin, controller.getByMail(admin.getEmail()));
    }

    @Test
    public void getByNotExistingMail() {
        assertThrows(NotFoundException.class, () -> controller.getByMail("NOT_EXISTING_EMAIL"));
    }

    @Test
    public void getByNullMail() {
        assertThrows(IllegalArgumentException.class, () -> controller.getByMail(null));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
    }
}