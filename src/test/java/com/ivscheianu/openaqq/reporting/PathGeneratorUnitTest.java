package com.ivscheianu.openaqq.reporting;

import static org.junit.jupiter.api.Assertions.*;

import com.ivscheianu.openaqq.TestUtils;
import com.ivscheianu.openaqq.reporting.request.ReportingRequest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class PathGeneratorUnitTest {

    @Test
    void getDataSourceInputPathsLocations() {
        //given
        final ReportingRequest request = TestUtils.getRequestFromFile("request/de_2022-12-01-03.json");

        //when
        final String[] result = new PathGenerator("s3a://openaqq/data/daily/").getDataSourceInputPathsLocations(request);

        System.out.println(Arrays.toString(result));
        //then
        assertEquals(new String[]{"s3a://openaqq/data/daily/2022-12-01/country=DE/", "s3a:/openaqq/data/daily/2022-12-02//country=DE/", "s3a://openaqq/data/daily/2022-12-03/country=DE/"}, result);
    }
}