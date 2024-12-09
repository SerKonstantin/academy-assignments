package org.academy;

/*
Задача была использовать фильтр Object apply(Object o), но с дженериками можно использовать и другие типы.
Фактически реализовали функциональный интерфейс UnaryOperator, с другим названием метода.
*/

public class CustomFilter implements Filter<Object> {
    public Object apply(Object o) {
        // Do smth with object and then:
        return o;
    }
}
