package com.main;

public interface TypedUF<T> extends UF {
    boolean connected(T t1, T t2) throws UFException;

    void union(T t1, T t2) throws UFException;
}
