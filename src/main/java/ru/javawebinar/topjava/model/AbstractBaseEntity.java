package ru.javawebinar.topjava.model;

import org.hibernate.Hibernate;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.HasId;

import javax.persistence.*;

@MappedSuperclass

// http://stackoverflow.com/questions/594597/hibernate-annotations-which-is-better-field-or-property-access
@Access(AccessType.FIELD)

// при получении JSON объектов мы можем увидеть, что Jackson сериализовал объект через геттеры (например в ответе есть поле new от метода
// Persistable.isNew()). Чтобы учитывались только поля объектов:
// fieldVisibility = ANY, // jackson видит все поля
// getterVisibility = NONE, // ... но не видит геттеров
// isGetterVisibility = NONE, //... не видит геттеров boolean полей
// setterVisibility = NONE // ... не видит сеттеров
//@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
//UPDATED:
//изменим сериализацию/десериализацию полей объектов в JSON: не через аннотацию @JsonAutoDetect, а в классе JacksonObjectMapper,
// который унаследуем от ObjectMapper (стандартный Mapper, который использует Jackson) и сделаем в нем другие настройки.
public abstract class AbstractBaseEntity implements HasId {
    public static final int START_SEQ = 100000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    //    @Column(name = "id", unique = true, nullable = false, columnDefinition = "integer default nextval('global_seq')")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
//  See https://hibernate.atlassian.net/browse/HHH-3718 and https://hibernate.atlassian.net/browse/HHH-12034
//  Proxy initialization when accessing its identifier managed now by JPA_PROXY_COMPLIANCE setting
    protected Integer id;

    protected AbstractBaseEntity() {
    }

    protected AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        AbstractBaseEntity that = (AbstractBaseEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }
}