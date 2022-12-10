package com.ivscheianu.openaqq.common.base.rowmapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Row;
import scala.Function1;

import java.io.Serializable;

@Slf4j
public abstract class AbstractRowMapper<E extends Serializable> implements Function1<Row, E>, Serializable {

    @Override
    public E apply(final Row row) {
        try {
            return mapRow(row);
        } catch (final Exception exception) {
            log.error("Failed to parse row = {}", row);
            return getDefault();
        }
    }

    protected abstract E mapRow(final Row row);

    protected E getDefault() {
        return null;
    }
}
