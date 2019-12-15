package ru.bedward70.conwaysgameoflife.v3.action;

@FunctionalInterface
public interface ActionFunction<Q, S, T, U, R> {

    R apply(Q q, S s, T t, U u);
}
