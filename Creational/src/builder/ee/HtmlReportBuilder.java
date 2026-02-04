package builder.ee;

// 2. Concrete Builder A (HTML)
class HtmlReportBuilder implements ReportBuilder {
    private StringBuilder sb = new StringBuilder();

    public void addHeader(String text) {
        sb.append("<h1>").append(text).append("</h1>\n");
    }
    public void addRow(String rowData) {
        sb.append("<p>").append(rowData).append("</p>\n");
    }
    public void addFooter() {
        sb.append("<footer>Copyright 2024</footer>");
    }
    public String getResult() { return sb.toString(); }
}
