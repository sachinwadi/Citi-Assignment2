import com.citi.assignment.service.TransactionAnalysisService;

public class Main {

    public static void main(String[] args) {

        TransactionAnalysisService service = new TransactionAnalysisService();

//        System.out.println("===========All transactions for a given category - latest first===========");
//        service.getAllTransactionsByCategory()
//                .forEach((key, value) -> value.forEach(y -> System.out.println(key + " || " + y.toString())));
//
//        System.out.println("===========Total outgoing per category===========");
//        service.getOutgoingPerCategory()
//                .forEach((key, value) -> System.out.println(key + " || " + value));

//
//        System.out.println("===========Monthly average spend in a given category===========");
//        service.getMonthlyAverageSpendByCategory()
//                .forEach((key, value) ->
//                        value.forEach((key1, value1) -> System.out.println(key + " || " + key1 + " || " + value1)));

//        System.out.println("===========Highest spend in a given category, for a given year===========");
//        service.getHighestSpentInCategoryForGivenYear()
//                .forEach((key, value) ->
//                        value.forEach((key1, value1) -> System.out.println(key + " || " + key1 + " || " + value1)));

        System.out.println("===========Lowest spend in a given category, for a given year===========");
        service.getLowestSpentInCategoryForGivenYear()
                .forEach((key, value) ->
                        value.forEach((key1, value1) -> System.out.println(key + " || " + key1 + " || " + value1)));
    }
}
