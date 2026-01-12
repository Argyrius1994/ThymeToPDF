package bank.of.cyprus.demo.controller;

import bank.of.cyprus.demo.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoiceController {
    
    private final PdfService pdfService;
    
    public InvoiceController(PdfService pdfService) {
        this.pdfService = pdfService;
    }
    
    @GetMapping("/invoice/pdf")
    public ResponseEntity<byte[]> downloadInvoice() {
        
        byte[] pdf = pdfService.generateInvoicePdf();
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
    @GetMapping("/contract/pdf")
    public ResponseEntity<byte[]> downloadContract() {
        
        byte[] pdf = pdfService.generateContractPdf();
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=contract.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
