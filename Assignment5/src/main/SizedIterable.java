package main;

public interface SizedIterable<T> extends Iterable<T> {
    int size();
}
