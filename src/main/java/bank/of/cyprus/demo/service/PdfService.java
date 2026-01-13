package bank.of.cyprus.demo.service;


import bank.of.cyprus.demo.model.InvoiceItem;
import bank.of.cyprus.demo.model.PageEntry;
import bank.of.cyprus.demo.model.ReportData;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
    
    public byte[] generateTermsAndConditionsPdf() {
        Context context = new Context();
        
        // 1. Prepare your dynamic data
        List<PageEntry> pageEntries = generatePocData();
        
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
        context.setVariable("reportData", report);
        
        // 4. Process the HTML template
        String html = templateEngine.process("termsandconditions", context);
        
        // 5. Build the PDF
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            // Null is used for the base URI; change if you have local images
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
            
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Terms PDF", e);
        }
    }
    
    public List<PageEntry> generatePocData() {
        List<PageEntry> parts = new ArrayList<>();
        
        // Article 1: The Lead Story
        parts.add(new PageEntry(
                "The Digital Transformation of Print",
                "For decades, the newspaper industry relied on heavy machinery and physical distribution. " +
                        "Today, the same aesthetic is being captured through advanced CSS Paged Media and Java " +
                        "rendering engines. This transition allows for the rapid generation of reports that maintain " +
                        "the authority of print with the speed of the cloud."
        ));
        
        // Article 2: A very long body of text to force page breaks
        StringBuilder longBody = new StringBuilder();
        for (int i = 1; i <= 15; i++) {
            longBody.append("Section ").append(i).append(": Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "In this detailed analysis of automated PDF generation, we observe that vertical flow is " +
                    "essential for readability. By utilizing the OpenHTMLtoPDF library, developers can " +
                    "programmatically define headers and footers that persist across the entire document lifecycle. " +
                    "As the content expands, the engine calculates the remaining height on the A4 canvas and " +
                    "intelligently moves text to the next column or the subsequent page. ");
            
            // Add extra line breaks to take up more vertical space
            longBody.append("\n\n");
        }
        
        parts.add(new PageEntry("Technical Deep Dive", longBody.toString()));
        
        // Article 3: Secondary News
        parts.add(new PageEntry(
                "The Role of Thymeleaf",
                "Thymeleaf serves as the bridge between raw Java data and the visual HTML representation. " +
                        "By binding our ArticlePart list to the template, we remove the need for manual pagination. " +
                        "The following paragraphs will likely end up on page 2 or 3 depending on your margin settings."
        ));
        
        // Add another big block of text to ensure page 3 is reached
        parts.add(new PageEntry(
                "Conclusion of the POC",
                "If you see this text on a third page, your POC is successful. " +
                        "This confirms that the 'position: running' property and the column-count " +
                        "are working in harmony to handle infinite data streams."
        ));
        
        return parts;
    }
}
