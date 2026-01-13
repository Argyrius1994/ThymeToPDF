package bank.of.cyprus.demo.controller;

import bank.of.cyprus.demo.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TermsAndConditionsController {
    private final PdfService pdfService;
    
    public TermsAndConditionsController(PdfService pdfService) {
        this.pdfService = pdfService;
    }
    
    @GetMapping("/tac/pdf")
    public ResponseEntity<byte[]> downloadInvoice() {
        
        byte[] pdf = pdfService.generateTermsAndConditionsPdf();
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
