package com.ivscheianu.openaqq.preprocessing.daily;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RandomnessProvider {

    private static final int RECORDS_THRESHOLD = 100_000;

    public Map<String, Integer> getRandomness(final List<CountryRecords> countryStats) {
        return countryStats
              .stream()
              .collect(
                  Collectors.toMap(
                     CountryRecords::getCountry,
                     countryRecords -> (int) (countryRecords.getRecords() / RECORDS_THRESHOLD)
              )
        );
    }
}