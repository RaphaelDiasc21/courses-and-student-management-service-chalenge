package com.alura.chalenge.application.shared.interfaces;

import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;

public interface EntityService<T> {
    T create(T entity) throws EntityCreationException, EntityNotFoundException;
}
