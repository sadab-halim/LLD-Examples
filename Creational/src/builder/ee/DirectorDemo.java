package builder.ee;

import java.util.List;

public class DirectorDemo {
    public static void main(String[] args) {
        ReportData data = new ReportData("Q1 Financials", List.of("Sales: 1M", "Cost: 500k"));

        // 1. Generate HTML
        HtmlReportBuilder htmlBuilder = new HtmlReportBuilder();
        ReportDirector director = new ReportDirector(htmlBuilder);
        director.constructReport(data);
        System.out.println("--- HTML Output ---");
        System.out.println(htmlBuilder.getResult());

        // 2. Generate Text (Using same director logic!)
        TextReportBuilder textBuilder = new TextReportBuilder();
        director = new ReportDirector(textBuilder); // Switch builder
        director.constructReport(data);
        System.out.println("\n--- Text Output ---");
        System.out.println(textBuilder.getResult());
    }
}
