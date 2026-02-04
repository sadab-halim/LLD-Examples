package builder.ee;

// 4. The Director (Orchestrates the order)
class ReportDirector {
    private final ReportBuilder builder;

    public ReportDirector(ReportBuilder builder) {
        this.builder = builder;
    }

    public void constructReport(ReportData data) {
        // The Director defines the algorithm: Header -> Rows -> Footer
        builder.addHeader(data.title);
        for (String row : data.rows) {
            builder.addRow(row);
        }
        builder.addFooter();
    }
}