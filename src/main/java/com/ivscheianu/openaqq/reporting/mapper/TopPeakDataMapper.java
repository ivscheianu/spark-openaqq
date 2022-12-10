package com.ivscheianu.openaqq.reporting.mapper;

import static com.ivscheianu.openaqq.common.ColumnEnum.CITY;
import static com.ivscheianu.openaqq.common.ColumnEnum.PARAMETER;
import static com.ivscheianu.openaqq.common.ColumnEnum.PEAK;
import static com.ivscheianu.openaqq.common.ColumnEnum.UNIT;
import static com.ivscheianu.openaqq.common.ColumnEnum.UTC_TIME;

import com.ivscheianu.openaqq.common.base.rowmapper.AbstractRowMapper;
import com.ivscheianu.openaqq.reporting.response.TopPeakData;
import org.apache.spark.sql.Row;

public class TopPeakDataMapper extends AbstractRowMapper<TopPeakData> {
    @Override
    protected TopPeakData mapRow(final Row row) {
        final Double peakValue = row.getAs(PEAK.getName());
        final String parameter = row.getAs(PARAMETER.getName());
        final String unit = row.getAs(UNIT.getName());
        final String city = row.getAs(CITY.getName());
        final String utcTime = row.getAs(UTC_TIME.getName());
        return TopPeakData
            .builder()
            .city(city)
            .unit(unit)
            .value(peakValue)
            .parameter(parameter)
            .utcTime(utcTime)
            .build();
    }
}
