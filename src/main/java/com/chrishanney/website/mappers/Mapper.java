package com.chrishanney.website.mappers;

// Could have multiple different mapping scenarios so using generics with the interface
public interface Mapper<A, B> {
    B mapTo(A a);

    A mapFrom(B b);
}
