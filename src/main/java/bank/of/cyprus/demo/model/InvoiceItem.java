package bank.of.cyprus.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvoiceItem {
    private String name;
    private int quantity;
    private double price;
    
    public double getTotal() {
        return quantity * price;
    }
    
    // constructors, getters, setters
}
