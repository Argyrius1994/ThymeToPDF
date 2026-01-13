package bank.of.cyprus.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportData {
    private String newspaperName;
    private String currentDate;
    private String editionName;
    private List<PageEntry> pages;
    
}