package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;

import java.io.Serial;

// объект AuthorizedUser будет хранится в сессии и для этого ему требуется сериализация средствами Java.
// Это наследование его и всех классов-полей от маркерного интерфейса Serializable и необязательный, но желательный serialVersionUID.
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    @Serial
    private static final long serialVersionUID = 1L;
//    Класс AuthorizedUser задает в нашем приложении аутентифицированного пользователя и мы будем хранить в нем UserTo - данные,
//    которых нет в стандартном org.springframework.security.core.userdetails.User, в частности id и caloriesPerDay.
    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UserUtil.asTo(user);
    }

    public int getId() {
        return userTo.id();
    }

    public void update(UserTo newTo) {
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