package com.ivscheianu.openaqq.common;

import lombok.Getter;
import org.apache.spark.sql.Column;
import static org.apache.spark.sql.functions.col;

@Getter
public enum ColumnEnum {

    AVERAGING_PERIOD(1, "averagingPeriod"),
    CITY(2, "city"),
    COORDINATES(3, "coordinates"),
    COUNTRY(4, "country"),
    DATE(5, "date"),
    LOCATION(6, "location"),
    MOBILE(7, "mobile"),
    PARAMETER(8, "parameter"),
    SOURCE_NAME(9, "sourceName"),
    SOURCE_TYPE(10, "sourceType"),
    UNIT(11, "unit"),
    VALUE(12, "value"),
    UTC_TIME(13, "utc_time"),
    LOCAL_TIME(14, "local_time"),
    LONGITUDE(15, "longitude"),
    LATITUDE(16, "latitude"),
    SOURCE(17, "source"),
    PROVIDER(18, "provider"),
    AVERAGING_TIME(19, "averaging_time"),
    AVERAGING_UNIT(20, "averaging_unit"),
    ATTRIBUTION(21, "attribution"),
    PEAK(22, "peak"),
    RECORD(23, "record"),
    RECORDS(24, "records"),
    RANDOMNESS(25, "randomness"),
    SALT(26, "salt"),
    SEED(27, "seed");

    private final int id;
    private final String name;
    private final Column column;

    ColumnEnum(final int id, final String name) {
        this.id = id;
        this.name = name;
        column = col(name);
    }
}
