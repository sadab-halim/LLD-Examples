package builder.ee;

// 3. Concrete Builder B (Plain Text)
class TextReportBuilder implements ReportBuilder {
    private StringBuilder sb = new StringBuilder();

    public void addHeader(String text) {
        sb.append("=== ").append(text).append(" ===\n");
    }
    public void addRow(String rowData) {
        sb.append("- ").append(rowData).append("\n");
    }
    public void addFooter() {
        sb.append("----------------\n");
    }
    public String getResult() { return sb.toString(); }
}
