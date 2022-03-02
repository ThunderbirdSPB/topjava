package ru.javawebinar.topjava.util.exception;

/**
 * Для REST запросов в случае возникновения исключения будем возвращать информацию о нем в формате json.
 * Для этого создадим класс ErrorInfo - он будет представлять информацию об исключении в ответе клиенту.
 */
public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String detail;

    public ErrorInfo(CharSequence url, ErrorType type, String detail) {
        this.url = url.toString();
        this.type = type;
        this.detail = detail;
    }
}