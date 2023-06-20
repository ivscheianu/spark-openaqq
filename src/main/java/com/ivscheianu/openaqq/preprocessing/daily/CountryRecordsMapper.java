package com.ivscheianu.openaqq.preprocessing.daily;

import com.ivscheianu.openaqq.common.base.rowmapper.AbstractRowMapper;
import org.apache.spark.sql.Row;

import static com.ivscheianu.openaqq.common.ColumnEnum.COUNTRY;
import static com.ivscheianu.openaqq.common.ColumnEnum.RECORDS;

public class CountryRecordsMapper extends AbstractRowMapper<CountryRecords> {
    @Override
    protected CountryRecords mapRow(final Row row) {
        return CountryRecords
                .builder()
                .country(row.getAs(COUNTRY.getName()))
                .records(row.getAs(RECORDS.getName()))
                .build();
    }
}
