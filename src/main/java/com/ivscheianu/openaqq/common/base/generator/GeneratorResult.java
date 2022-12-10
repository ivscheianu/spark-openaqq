package com.ivscheianu.openaqq.common.base.generator;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@ToString
@EqualsAndHashCode
public class GeneratorResult<E extends Serializable> {

    private final List<E> results;

    public GeneratorResult(final List<E> results) {
        this.results = results;
    }

    public GeneratorResult(final E result) {
        this.results = Collections.singletonList(result);
    }

    public List<E> getResults() {
        return results;
    }

    public E getResult() {
        return results.get(0);
    }
}
