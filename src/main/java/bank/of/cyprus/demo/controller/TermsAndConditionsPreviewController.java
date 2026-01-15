package bank.of.cyprus.demo.controller;

import bank.of.cyprus.demo.model.PageEntry;
import bank.of.cyprus.demo.model.ReportData;
import bank.of.cyprus.demo.service.PdfService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.context.Context;

import java.util.List;

@Controller
public class TermsAndConditionsPreviewController {
    private final PdfService pdfService;
    
    public TermsAndConditionsPreviewController(PdfService pdfService) {
        this.pdfService = pdfService;
    }
    
    @GetMapping("/tac/preview")
    public String  previewTermsAndConditions(Model model) {
        
        
        // 1. Prepare your dynamic data
        List<PageEntry> pageEntries = pdfService.generatePocData();
        
        // Add your content parts (Headlines and long text blocks)
//        pageEntries.add(new PageEntry("Terms of Service",
//                "By accessing this service, you agree to be bound by these terms... [Long text here]"));
//        pageEntries.add(new PageEntry("User Responsibilities",
//                "Users must ensure the accuracy of all provided data... [More long text]"));
//        pageEntries.add(new PageEntry("Privacy Policy",
//                "Your data is protected under the latest encryption standards... [Continue text]"));
        
        // 2. Wrap into the Master DTO
        ReportData report = new ReportData();
        report.setNewspaperName("LEGAL GAZETTE");
        report.setCurrentDate("14-01-2026");
        report.setEditionName("Terms & Conditions v1.0");
        report.setPages(pageEntries);
        
        // 3. Set the variable for Thymeleaf
        model.addAttribute("reportData", report);
        return "termsandconditions"; // termsandconditions.html
    }
}
