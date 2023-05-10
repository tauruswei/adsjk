package org.cos.common.entity.base;

public interface ResultEnumType<T, P> {
    T getReturnCode();
    P getMessage();
}
