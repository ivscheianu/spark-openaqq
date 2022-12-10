package com.ivscheianu.openaqq.preprocessing.daily;

import static com.ivscheianu.openaqq.common.ColumnEnum.ATTRIBUTION;
import static com.ivscheianu.openaqq.common.ColumnEnum.AVERAGING_PERIOD;
import static com.ivscheianu.openaqq.common.ColumnEnum.AVERAGING_TIME;
import static com.ivscheianu.openaqq.common.ColumnEnum.AVERAGING_UNIT;
import static com.ivscheianu.openaqq.common.ColumnEnum.COORDINATES;
import static com.ivscheianu.openaqq.common.ColumnEnum.DATE;
import static com.ivscheianu.openaqq.common.ColumnEnum.LATITUDE;
import static com.ivscheianu.openaqq.common.ColumnEnum.LOCAL_TIME;
import static com.ivscheianu.openaqq.common.ColumnEnum.LONGITUDE;
import static com.ivscheianu.openaqq.common.ColumnEnum.PROVIDER;
import static com.ivscheianu.openaqq.common.ColumnEnum.SOURCE;
import static com.ivscheianu.openaqq.common.ColumnEnum.SOURCE_NAME;
import static com.ivscheianu.openaqq.common.ColumnEnum.SOURCE_TYPE;
import static com.ivscheianu.openaqq.common.ColumnEnum.UTC_TIME;
import static com.ivscheianu.openaqq.common.UDFs.materializeUDF;

import com.ivscheianu.openaqq.common.UDFEnum;
import com.ivscheianu.openaqq.common.base.dataset.DatasetProvider;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

@RequiredArgsConstructor
public class PreprocessingDatasetProvider implements DatasetProvider {

    private final SparkSession sparkSession;

    @Override
    public Dataset<Row> getDataset(final String... locations) {
        return sparkSession
            .read()
            .json(locations)
            .withColumn(UTC_TIME.getName(), materializeUDF(UDFEnum.EXTRACT_UTC_DATE, DATE))
            .withColumn(LOCAL_TIME.getName(), materializeUDF(UDFEnum.EXTRACT_LOCAL_DATE, DATE))
            .withColumn(LATITUDE.getName(), materializeUDF(UDFEnum.EXTRACT_LATITUDE, COORDINATES))
            .withColumn(LONGITUDE.getName(), materializeUDF(UDFEnum.EXTRACT_LONGITUDE, COORDINATES))
            .withColumn(AVERAGING_TIME.getName(), materializeUDF(UDFEnum.EXTRACT_AVERAGING_TIME, AVERAGING_PERIOD))
            .withColumn(AVERAGING_UNIT.getName(), materializeUDF(UDFEnum.EXTRACT_AVERAGING_TIME_UNIT, AVERAGING_PERIOD))
            .withColumnRenamed(SOURCE_NAME.getName(), SOURCE.getName())
            .withColumnRenamed(SOURCE_TYPE.getName(), PROVIDER.getName())
            .drop(
                DATE.getName(),
                COORDINATES.getName(),
                AVERAGING_PERIOD.getName(),
                ATTRIBUTION.getName()
            );
    }
}
