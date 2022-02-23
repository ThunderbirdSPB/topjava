package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Assumptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.Profiles;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
//@WebAppConfiguration
//@ExtendWith(SpringExtension.class)
/**
 * Для того, чтобы в тестах контроллеров не популировать базу перед каждым тестом, пометим этот базовый тестовый класс аннотацией @Transactional.
 * Теперь каждый тестовый метод будет выполняться в транзакции, которая будет откатываться после окончания метода и возвращать базу данных
 * в исходное состояние. Однако теперь в работе тестов могут возникнуть нюансы, связанные с пропагацией транзакций: все транзакции репозиториев
 * станут вложенными во внешнюю транзакцию теста. При этом, например, кэш первого уровня станет работать не так, как ожидается.
 * Т.е при таком подходе нужно быть готовыми к ошибкам
 */
@Transactional
@ActiveProfiles(resolver = ActiveDbProfileResolver.class, profiles = Profiles.REPOSITORY_IMPLEMENTATION)
public abstract class AbstractControllerTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    @Autowired
    public Environment env;

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    public void assumeDataJpa() {
        Assumptions.assumeTrue(env.acceptsProfiles(org.springframework.core.env.Profiles.of(Profiles.DATAJPA)), "DATA-JPA only");
    }

    @PostConstruct
    // Для тестирования контроллеров, к запросам которого требуется аутентификация, будем использовать библиотеку spring-security-test.
    // добавим аналог цепочки security-фильтров: .apply(springSecurity()).
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }
}
