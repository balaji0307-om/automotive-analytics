package com.automotive.service;

import com.automotive.dto.AnalyticsDto;

/**
 * Service interface for analytics operations.
 */
public interface AnalyticsService {

    /**
     * Compile all analytics data into one DTO.
     * Used by the analytics pages (fuel, engine, environmental).
     */
    AnalyticsDto getAnalyticsData();
}
