package com.ivscheianu.openaqq.common.base.io;

public interface ObjectRetriever<T> {
    <E> E retrieve(final Class<E> type, final T retrieveInfo);
}
