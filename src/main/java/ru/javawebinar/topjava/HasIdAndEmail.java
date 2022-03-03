package ru.javawebinar.topjava;

/**
 * Для универсальной обработки User и UserTo создадим над ними интерфейс HasIdAndEmail extends HasId - валидатор
 * будет обрабатывать все реализующие его классы.
 */
public interface HasIdAndEmail extends HasId {
    String getEmail();
}