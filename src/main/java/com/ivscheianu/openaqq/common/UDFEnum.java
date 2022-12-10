package com.ivscheianu.openaqq.common;

import static com.ivscheianu.openaqq.common.UDFs.getExtractAveragingTime;
import static com.ivscheianu.openaqq.common.UDFs.getExtractAveragingTimeUnit;
import static com.ivscheianu.openaqq.common.UDFs.getExtractLatitude;
import static com.ivscheianu.openaqq.common.UDFs.getExtractLocalDate;
import static com.ivscheianu.openaqq.common.UDFs.getExtractLongitude;
import static com.ivscheianu.openaqq.common.UDFs.getExtractUTCDate;
import static org.apache.spark.sql.types.DataTypes.DoubleType;
import static org.apache.spark.sql.types.DataTypes.StringType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataType;

@Getter
@RequiredArgsConstructor
public enum UDFEnum {

    EXTRACT_UTC_DATE("extractUTCDate", getExtractUTCDate(), StringType),
    EXTRACT_LOCAL_DATE("extractLocalDate", getExtractLocalDate(), StringType),
    EXTRACT_LATITUDE("extractLongitude", getExtractLatitude(), DoubleType),
    EXTRACT_LONGITUDE("extractLatitude", getExtractLongitude(), DoubleType),
    EXTRACT_AVERAGING_TIME("extractAveragingTime", getExtractAveragingTime(), DoubleType),
    EXTRACT_AVERAGING_TIME_UNIT("extractAveragingTimeUnit", getExtractAveragingTimeUnit(), StringType);

    private final String name;
    private final UDF1<?, ?> udf;
    private final DataType returnType;
}
