package ru.javawebinar.topjava.util.exception;

/**
 * Для REST запросов в случае возникновения исключения будем возвращать информацию о нем в формате json.
 * Для этого создадим класс ErrorInfo - он будет представлять информацию об исключении в ответе клиенту.
 */
public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String[] details;

    public ErrorInfo(CharSequence url, ErrorType type, String... details) {
        this.url = url.toString();
        this.type = type;
        this.details = details;
    }
}