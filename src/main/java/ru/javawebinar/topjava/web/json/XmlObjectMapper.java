package ru.javawebinar.topjava.web.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class XmlObjectMapper extends XmlMapper {
    private static final ObjectMapper MAPPER = new XmlObjectMapper();

    /**
     * В конструкторе:
     *
     * регистрируем Hibernate5Module - модуль jackson-datatype-hibernate, который не делает сериализацию ленивых полей.
     * модуль для корректной сериализации LocalDateTime в поля XML - JavaTimeModule модуль библиотеки jackson-datatype-jsr310
     * запрещаем доступ ко всем полям и методам класса и потом разрешаем доступ только к полям
     * не сериализуем null-поля (setSerializationInclusion(JsonInclude.Include.NON_NULL))
     */
    private XmlObjectMapper() {
        registerModule(new Hibernate5Module());

        registerModule(new JavaTimeModule());
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }
}
