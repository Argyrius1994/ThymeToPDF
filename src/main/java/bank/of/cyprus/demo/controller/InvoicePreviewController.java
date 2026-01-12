package bank.of.cyprus.demo.controller;

import bank.of.cyprus.demo.model.InvoiceItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class InvoicePreviewController {
    
    @GetMapping("/invoice/preview")
    public String previewInvoice(Model model) {
        
        var items = List.of(
                new InvoiceItem("Laptop", 1, 1200.00),
                new InvoiceItem("Mouse", 2, 25.00)
        );
        
        model.addAttribute("customerName", "Acme Corporation");
        model.addAttribute("date", "2026-01-10");
        model.addAttribute("items", items);
        
        double grandTotal = items.stream()
                .mapToDouble(InvoiceItem::getTotal)
                .sum();
        
        model.addAttribute("grandTotal", grandTotal);
        
        return "invoice"; // invoice.html
    }
}
