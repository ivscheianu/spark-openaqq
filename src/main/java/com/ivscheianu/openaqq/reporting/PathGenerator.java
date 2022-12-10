package com.ivscheianu.openaqq.reporting;

import static com.ivscheianu.openaqq.common.Constants.SLASH;
import static com.ivscheianu.openaqq.common.Constants.ZERO;

import com.ivscheianu.openaqq.reporting.request.ReportingRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
class PathGenerator {

    private final String baseLocation;

    private static final String COUNTRY = "/country=";
    private static final DateTimeFormatter UTC_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneId.of("UTC"));
    private static final DateTimeFormatter DATE_FORMAT_FOR_FOLDER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    String[] getDataSourceInputPathsLocations(final ReportingRequest request) {
        final ZonedDateTime zonedStartDate = ZonedDateTime.parse(request.getStartDate(), UTC_FORMAT);
        final ZonedDateTime zonedEndDate = ZonedDateTime.parse(request.getEndDate(), UTC_FORMAT);
        final List<LocalDate> datesBetweenInputDates = getDatesBetweenInputDates(zonedStartDate, zonedEndDate);
        return datesBetweenInputDates
            .stream()
            .map(localDate -> createHdfsPath(request, localDate))
            .toArray(String[]::new);
    }

    private List<LocalDate> getDatesBetweenInputDates(final ZonedDateTime startDate, final ZonedDateTime endDate) {
        return IntStream
            .rangeClosed(ZERO, getDaysBetween(startDate, endDate))
            .mapToObj(index -> startDate.toLocalDate().plusDays(index))
            .collect(Collectors.toList());
    }

    private int getDaysBetween(final ZonedDateTime startDate, final ZonedDateTime endDate) {
        return (int) ChronoUnit.DAYS.between(
            startDate.toLocalDate().atStartOfDay(),
            endDate.toLocalDate().atStartOfDay()
        );
    }

    private String createHdfsPath(final ReportingRequest request, final LocalDate localDate) {
        return baseLocation +
               localDate.format(DATE_FORMAT_FOR_FOLDER) +
               COUNTRY +
               request.getCountryISO2() +
               SLASH;
    }
}
