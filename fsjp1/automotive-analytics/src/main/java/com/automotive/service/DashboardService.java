package com.automotive.service;

import com.automotive.dto.DashboardStatsDto;

/**
 * Service interface for dashboard statistics.
 */
public interface DashboardService {

    /**
     * Gather all summary statistics for the dashboard home page.
     */
    DashboardStatsDto getDashboardStats();
}
