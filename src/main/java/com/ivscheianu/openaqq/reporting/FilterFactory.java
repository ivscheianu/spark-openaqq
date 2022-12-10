package com.ivscheianu.openaqq.reporting;

import static com.ivscheianu.openaqq.common.ColumnEnum.CITY;
import static com.ivscheianu.openaqq.common.ColumnEnum.LATITUDE;
import static com.ivscheianu.openaqq.common.ColumnEnum.LONGITUDE;
import static com.ivscheianu.openaqq.common.ColumnEnum.UTC_TIME;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.spark.sql.functions.lit;

import com.ivscheianu.openaqq.reporting.request.ReportingRequest;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Column;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class FilterFactory {

    private final ReportingRequest request;

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    Column getFilters() {
        return applyAnd(
            Optional.of(UTC_TIME.getColumn().geq(lit(request.getStartDate()))),
            Optional.of(UTC_TIME.getColumn().lt(lit(request.getEndDate()))),
            getGeoLocationFilter()
        ).get();
    }

    private Optional<Column> getGeoLocationFilter() {
        return applyOr(
            getCityFilter(),
            getCoordinatesFilter()
        );
    }

    private Optional<Column> getCityFilter() {
        if (isNotEmpty(request.getCities())) {
            return Optional.of(
                CITY.getColumn().isInCollection(request.getCities())
            );
        }
        return Optional.empty();
    }

    private Optional<Column> getCoordinatesFilter() {
        final List<Column> locationFilters = new ArrayList<>(2);
        if (nonNull(request.getLatitude())) {
            locationFilters.add(LATITUDE.getColumn().equalTo(request.getLatitude()));
        }
        if (nonNull(request.getLongitude())) {
            locationFilters.add(LONGITUDE.getColumn().equalTo(request.getLongitude()));
        }
        return applyAnd(locationFilters);
    }

    @SafeVarargs
    private final Optional<Column> applyOr(final Optional<Column>... optionals) {
        final List<Optional<Column>> list = Arrays.asList(optionals);
        return applyOr(unwrap(list));
    }

    @SafeVarargs
    private final Optional<Column> applyAnd(final Optional<Column>... optionals) {
        final List<Optional<Column>> list = Arrays.asList(optionals);
        return applyAnd(unwrap(list));
    }

    private <E> List<E> unwrap(final Collection<Optional<E>> optionals) {
        return emptyIfNull(optionals)
            .stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .distinct()
            .collect(Collectors.toList());
    }

    private Optional<Column> applyAnd(final Collection<Column> filters) {
        return emptyIfNull(filters).stream().distinct().reduce(Column::and);
    }

    private Optional<Column> applyOr(final Collection<Column> filters) {
        return emptyIfNull(filters).stream().distinct().reduce(Column::or);
    }
}
