package com.ivscheianu.openaqq.reporting;

import com.ivscheianu.openaqq.common.base.dataset.DatasetProvider;
import com.ivscheianu.openaqq.reporting.request.ReportingRequest;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

@RequiredArgsConstructor
public class ReportingDatasetProvider implements DatasetProvider {

    private final SparkSession sparkSession;
    private final ReportingRequest request;

    @Override
    public Dataset<Row> getDataset(final String... locations) {
        return sparkSession
            .read()
            .json(locations)
            .filter(new FilterFactory(request).getFilters())
            .cache();
    }
}
