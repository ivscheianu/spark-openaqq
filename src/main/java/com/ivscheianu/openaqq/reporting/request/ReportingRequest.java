package com.ivscheianu.openaqq.reporting.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportingRequest {
    private String requestId;
    private String startDate;
    private String endDate;
    private String countryISO2;
    private List<String> cities;
    private Double longitude;
    private Double latitude;
}
