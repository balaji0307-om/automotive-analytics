package com.automotive.controller;

import com.automotive.dto.ReportDto;
import com.automotive.service.ReportService;
import com.automotive.service.VehicleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller for Report generation and viewing.
 * Generating reports is available to all authenticated users.
 * Deleting reports is ADMIN-only.
 */
@Controller
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;
    private final VehicleService vehicleService;

    public ReportController(ReportService reportService, VehicleService vehicleService) {
        this.reportService = reportService;
        this.vehicleService = vehicleService;
    }

    /**
     * List all reports.
     */
    @GetMapping
    public String listReports(@RequestParam(required = false) String type, Model model) {
        List<ReportDto> reports;
        if (type != null && !type.isEmpty()) {
            reports = reportService.getReportsByType(type);
        } else {
            reports = reportService.getAllReports();
        }
        model.addAttribute("reports", reports);
        model.addAttribute("selectedType", type);
        model.addAttribute("activePage", "reports");
        return "reports/list";
    }

    /**
     * View a single report.
     */
    @GetMapping("/view/{id}")
    public String viewReport(@PathVariable Long id, Model model) {
        ReportDto report = reportService.getReportById(id);
        model.addAttribute("report", report);
        model.addAttribute("activePage", "reports");
        return "reports/view";
    }

    /**
     * Show generate report form.
     */
    @GetMapping("/generate")
    public String showGenerateForm(Model model) {
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("activePage", "reports");
        return "reports/generate";
    }

    /**
     * Generate a new report.
     */
    @PostMapping("/generate")
    public String generateReport(@RequestParam Long vehicleId,
                                  @RequestParam String reportType,
                                  RedirectAttributes redirectAttrs) {
        try {
            ReportDto report = reportService.generateReport(vehicleId, reportType);
            redirectAttrs.addFlashAttribute("successMessage",
                    "Report generated successfully! Report ID: " + report.getReportId());
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/reports";
    }

    /**
     * Delete a report. ADMIN only.
     */
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteReport(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        reportService.deleteReport(id);
        redirectAttrs.addFlashAttribute("successMessage", "Report deleted.");
        return "redirect:/reports";
    }
}
