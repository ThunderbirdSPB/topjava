package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;

import java.io.Serial;

// Объект AuthorizedUser будет хранится в сессии и для этого ему требуется сериализация средствами Java.
// При некоторых условиях Tomcat сохраняет данные сессии и ему требуется возможность их сериализации, поэтому объекты в сессии
// (и объекты, которые в них содержатся) обязательно должны имплементировать интерфейс Serializable.
// Например при shutdown'e Tomcat может дессериализовать данные из сесси
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    @Serial
    private static final long serialVersionUID = 1L;
//    Класс AuthorizedUser задает в нашем приложении аутентифицированного пользователя и мы будем хранить в нем UserTo - данные,
//    которых нет в стандартном org.springframework.security.core.userdetails.User, в частности id и caloriesPerDay.
    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        setTo(UserUtil.asTo(user));
    }

    public int getId() {
        return userTo.id();
    }

    public void setTo(UserTo newTo) {
        newTo.setPassword(null);
        userTo = newTo;
    }

    public UserTo getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}