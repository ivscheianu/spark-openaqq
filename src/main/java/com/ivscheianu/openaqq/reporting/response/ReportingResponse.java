package com.ivscheianu.openaqq.reporting.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportingResponse implements Serializable {
    private SummaryData summaryData;
    private List<TopPollutionData> topPollutionData;
    private List<TopPeakData> topPeakData;
    private List<FrequencyData> frequencyData;
}
