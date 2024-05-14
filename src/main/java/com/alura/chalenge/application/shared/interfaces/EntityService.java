package com.alura.chalenge.application.shared.interfaces;

import com.alura.chalenge.application.shared.exceptions.EntityCreationException;

public interface EntityService<T> {
    T create(T entity) throws EntityCreationException;
}
