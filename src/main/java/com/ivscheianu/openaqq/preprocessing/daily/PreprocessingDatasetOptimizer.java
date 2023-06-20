package com.ivscheianu.openaqq.preprocessing.daily;

import com.ivscheianu.openaqq.common.base.dataset.DatasetOptimizer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import static com.ivscheianu.openaqq.common.ColumnEnum.*;
import static org.apache.spark.sql.types.DataTypes.IntegerType;
import static org.apache.spark.sql.functions.rand;

import java.util.List;
import java.util.Map;

import static com.ivscheianu.openaqq.common.UDFs.getRandomnessDistributor;
import static com.ivscheianu.openaqq.common.UDFs.materializeUDF;

public class PreprocessingDatasetOptimizer implements DatasetOptimizer {

    @Override
    public Dataset<Row> optimize(final Dataset<Row> dataset) {
        final List<CountryRecords> countryStats = new CountryRecordsGenerator(dataset).generateData().getResults();
        final Map<String, Integer> lookup = new RandomnessProvider().getRandomness(countryStats);
        return dataset
//                .drop()
                .withColumn(RANDOMNESS.getName(), materializeUDF(getRandomnessDistributor(lookup), IntegerType, COUNTRY))
                .withColumn(SALT.getName(), rand().multiply(RANDOMNESS.getColumn()).cast(IntegerType))
                .repartition(COUNTRY.getColumn(), SALT.getColumn())
                .drop(
                        RANDOMNESS.getName(),
                        RECORD.getName(),
                        SALT.getName()
                );

    }
}
