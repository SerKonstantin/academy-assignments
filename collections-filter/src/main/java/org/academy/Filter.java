package org.academy;

public interface Filter<T> {
    T apply(T o);
}
