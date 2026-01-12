package bank.of.cyprus.demo.service;


import bank.of.cyprus.demo.model.InvoiceItem;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {
    
    private final TemplateEngine templateEngine;
    
    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    
    public byte[] generateInvoicePdf() {
        
        Context context = new Context();
        context.setVariable("customerName", "Acme Corporation");
        context.setVariable("date", "2026-01-10");
        
        var items = List.of(
                new InvoiceItem("Laptop", 1, 1200.00),
                new InvoiceItem("Mouse", 2, 25.00)
        );
        
        context.setVariable("items", items);
        
        double grandTotal = items.stream()
                .mapToDouble(InvoiceItem::getTotal)
                .sum();
        
        context.setVariable("grandTotal", grandTotal);
        
        String html = templateEngine.process("invoice", context);
        
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }
    
    
    public byte[] generateContractPdf() {
        
        Context context = new Context();
        context.setVariable("contractDate", "10-01-2026");
        context.setVariable("clientName", "Argyrios Gatidis");
        context.setVariable("providerName", "Bank of Cyprus");
        
        
        String html = templateEngine.process("contract", context);
        
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }
}
