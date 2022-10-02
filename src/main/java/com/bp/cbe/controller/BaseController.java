package com.bp.cbe.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseController<T, I> {
    ResponseEntity<T> getById(I id);

    ResponseEntity<List<T>> getAll();

    ResponseEntity<T> update(I id, T data);

    ResponseEntity<Void> deleteById(I id);
}
