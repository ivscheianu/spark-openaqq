package com.ivscheianu.openaqq.common;

import static org.apache.spark.sql.functions.udf;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.IntegerType;

import java.util.Arrays;
import java.util.Map;

@UtilityClass
public class UDFs {

    private final UDF1<GenericRowWithSchema, String> extractUTCDate = row -> extractFieldFromGenericRow(row, "utc");
    private final UDF1<GenericRowWithSchema, String> extractLocalDate = row -> extractFieldFromGenericRow(row, "local");
    private final UDF1<GenericRowWithSchema, Double> extractLongitude = row -> extractFieldFromGenericRow(row, "longitude");
    private final UDF1<GenericRowWithSchema, Double> extractLatitude = row -> extractFieldFromGenericRow(row, "latitude");
    private final UDF1<GenericRowWithSchema, Double> extractAveragingTime = row -> extractFieldFromGenericRow(row, "value");
    private final UDF1<GenericRowWithSchema, String> extractAveragingTimeUnit = row -> extractFieldFromGenericRow(row, "unit");

    @RequiredArgsConstructor
    private static class RandomnessDistributor implements UDF1<String, Integer> {

        private final Map<String, Integer> lookup;

        @Override
        public Integer call(final String country) {
            return lookup.getOrDefault(country, 0);
        }
    }


    public static UDF1<GenericRowWithSchema, String> getExtractUTCDate() {
        return extractUTCDate;
    }

    public static UDF1<GenericRowWithSchema, String> getExtractLocalDate() {
        return extractLocalDate;
    }

    public static UDF1<GenericRowWithSchema, Double> getExtractLongitude() {
        return extractLongitude;
    }

    public static UDF1<GenericRowWithSchema, Double> getExtractLatitude() {
        return extractLatitude;
    }

    public static UDF1<GenericRowWithSchema, Double> getExtractAveragingTime() {
        return extractAveragingTime;
    }

    public static UDF1<GenericRowWithSchema, String> getExtractAveragingTimeUnit() {
        return extractAveragingTimeUnit;
    }

    public static UDF1<String, Integer> getRandomnessDistributor(final Map<String, Integer> lookup) {
        return new RandomnessDistributor(lookup);
    }

    //compile-time UDF
    public static Column materializeUDF(final UDFEnum udfEnum, final ColumnEnum... params) {
        return materializeUDF(udfEnum.getUdf(), udfEnum.getReturnType(), params);
    }

    //runtime UDF
    public static Column materializeUDF(final UDF1<?, ?> udf1, final DataType returnType, final ColumnEnum... params) {
        final Column[] columnParams = Arrays.stream(params).map(ColumnEnum::getColumn).toArray(Column[]::new);
        return udf(udf1, returnType).apply(columnParams);
    }

    private static <T> T extractFieldFromGenericRow(final GenericRowWithSchema genericRowWithSchema, final String fieldName) {
        try {
            return genericRowWithSchema.getAs(fieldName);
        } catch (final Exception exception) {
            return null;
        }
    }
}
