package builder.ee;

// 1. The Builder Interface
interface ReportBuilder {
    void addHeader(String text);
    void addRow(String rowData);
    void addFooter();
}
