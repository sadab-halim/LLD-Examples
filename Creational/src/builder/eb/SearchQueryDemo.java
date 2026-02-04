package builder.eb;

public class SearchQueryDemo {
    public static void main(String[] args) {
        // Goal: (status == "active" AND (role == "admin" OR role == "editor"))
        Query query = new BoolQueryBuilder()
                .must(new TermQueryBuilder("status", "active"))
                .should(new TermQueryBuilder("role", "admin"))
                .should(new TermQueryBuilder("role", "editor"))
                .build();

        System.out.println("Constructed Query JSON: \n" + query.toJson());
    }
}