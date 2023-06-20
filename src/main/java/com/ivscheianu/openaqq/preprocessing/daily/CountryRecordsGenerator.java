package com.ivscheianu.openaqq.preprocessing.daily;

import com.ivscheianu.openaqq.common.base.generator.AbstractDataGenerator;
import com.ivscheianu.openaqq.common.base.rowmapper.AbstractRowMapper;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;

import static com.ivscheianu.openaqq.common.ColumnEnum.COUNTRY;
import static com.ivscheianu.openaqq.common.ColumnEnum.RECORD;
import static org.apache.spark.sql.functions.sum;

public class CountryRecordsGenerator extends AbstractDataGenerator<CountryRecords> {


    public CountryRecordsGenerator(final Dataset<Row> initialDataset) {
        super(initialDataset);
    }

    @Override
    protected Dataset<Row> getPreparedDataset() {
        return initialDataset
                .groupBy(COUNTRY.getColumn())
                .agg(sum(RECORD.getColumn()));
    }

    @Override
    protected AbstractRowMapper<CountryRecords> getMapper() {
        return new CountryRecordsMapper();
    }

    @Override
    protected Encoder<CountryRecords> getEncoder() {
        return Encoders.kryo(CountryRecords.class);
    }
}
