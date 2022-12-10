package com.ivscheianu.openaqq.reporting.generator;

import static com.ivscheianu.openaqq.common.ColumnEnum.CITY;
import static com.ivscheianu.openaqq.common.ColumnEnum.PARAMETER;
import static com.ivscheianu.openaqq.common.ColumnEnum.PEAK;
import static com.ivscheianu.openaqq.common.ColumnEnum.UNIT;
import static com.ivscheianu.openaqq.common.ColumnEnum.UTC_TIME;
import static com.ivscheianu.openaqq.common.ColumnEnum.VALUE;
import static org.apache.spark.sql.functions.max;
import static org.apache.spark.sql.functions.first;

import com.ivscheianu.openaqq.common.base.generator.AbstractDataGenerator;
import com.ivscheianu.openaqq.common.base.rowmapper.AbstractRowMapper;
import com.ivscheianu.openaqq.reporting.mapper.TopPeakDataMapper;
import com.ivscheianu.openaqq.reporting.response.TopPeakData;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;

public class TopPeakDataGenerator extends AbstractDataGenerator<TopPeakData> {

    public TopPeakDataGenerator(final Dataset<Row> initialDataset) {
        super(initialDataset);
    }

    @Override
    protected Dataset<Row> getPreparedDataset() {
        return initialDataset
            .groupBy(
                CITY.getColumn(),
                PARAMETER.getColumn()
            )
            .agg(
                max(VALUE.getColumn()).as(PEAK.getName()),
                first(UNIT.getColumn()).as(UNIT.getName()),
                first(UTC_TIME.getColumn()).as(UTC_TIME.getName())
            );
    }

    @Override
    protected AbstractRowMapper<TopPeakData> getMapper() {
        return new TopPeakDataMapper();
    }

    @Override
    protected Encoder<TopPeakData> getEncoder() {
        return Encoders.kryo(TopPeakData.class);
    }
}
