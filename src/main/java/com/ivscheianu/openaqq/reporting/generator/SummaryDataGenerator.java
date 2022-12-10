package com.ivscheianu.openaqq.reporting.generator;

import com.ivscheianu.openaqq.common.base.generator.DataGenerator;
import com.ivscheianu.openaqq.common.base.generator.GeneratorResult;
import com.ivscheianu.openaqq.reporting.response.SummaryData;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

@RequiredArgsConstructor
public class SummaryDataGenerator implements DataGenerator<SummaryData> {

    protected final Dataset<Row> initialDataset;

    @Override
    public Dataset<Row> getInitialDataset() {
        return initialDataset;
    }

    @Override
    public GeneratorResult<SummaryData> generateData() {
        return new GeneratorResult<>(new SummaryData());
    }
}
