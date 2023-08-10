package com.sir.todorestservicewithdynamodb.repository;

import jakarta.annotation.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

public interface GenericRepository<T> {
    Mono<T> findById(final String primaryKey);

    Flux<T> findAllById(final String primaryKey);

    Mono<T> findByIdAndSk(final String primaryKey, final String secondaryKey);

    Mono<Page<T>> findAll(@Nullable final Map<String, AttributeValue> keys, final Integer pageSize);

    Mono<T> save(final T entity);

    Mono<T> update(final T entity);

    Mono<T> deleteById(final String primaryKey);

    Mono<T> deleteByIdAndSortKey(String primaryKey, String secondaryKey);

    Mono<T> findByIdFromGSI(final String primaryKey);

    Mono<Page<T>> findAllFromGSI(final String primaryKey, @Nullable final Map<String, AttributeValue> keys,
                                 final Integer pageSize);
}
