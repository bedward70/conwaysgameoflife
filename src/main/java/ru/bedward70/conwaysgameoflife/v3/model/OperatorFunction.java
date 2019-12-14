package ru.bedward70.conwaysgameoflife.v3.model;

@FunctionalInterface
public interface OperatorFunction<S, T, U, R> {

    R apply(S s, T t, U u);
}
