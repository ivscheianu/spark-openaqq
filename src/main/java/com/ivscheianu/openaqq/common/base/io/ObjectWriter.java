package com.ivscheianu.openaqq.common.base.io;

public interface ObjectWriter <E> {
    <T> void write(final T object, final E saveInfo);
}
