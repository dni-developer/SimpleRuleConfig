import java.util.*;

public class Application {

    private static Rule condition = new Rule("condition", "used");
    private static Rule category = new Rule("category", "sedan");
    private static Rule color = new Rule("color", "red");
    private static Rule maker = new Rule("maker", "bmw");
    private static Rule year1 = new Rule("year", "2017");
    private static Rule year2 = new Rule("year", "2016");

    private static Map<String, List<Rule>> rules;

    static {
        rules = new HashMap<>();
        rules.put("must_not", Arrays.asList(condition));
        rules.put("must", Arrays.asList(category, color, maker));
        rules.put("or", Arrays.asList(year1, year2));
    }


    public static void main(String[] args) {
        Map<String, String> input1 = new HashMap<>();
        input1.put("condition", "new");
        input1.put("color", "red");
        input1.put("category", "sedan");
        input1.put("maker", "bmw");
        input1.put("year", "2017");
        System.out.println("input1:" + check(input1));

        Map<String, String> input2 = new HashMap<>();
        input2.put("condition", "used");
        input2.put("color", "red");
        input2.put("category", "sedan");
        input2.put("maker", "bmw");
        input2.put("year", "2017");
        System.out.println("input2:" + check(input2));

        Map<String, String> input3 = new HashMap<>();
        input3.put("condition", "new");
        input3.put("color", "white");
        input3.put("category", "sedan");
        input3.put("maker", "bmw");
        input3.put("year", "2017");
        System.out.println("input3:" + check(input3));

        Map<String, String> input4 = new HashMap<>();
        input4.put("condition", "new");
        input4.put("color", "red");
        input4.put("category", "sedan");
        input4.put("maker", "bmw");
        input4.put("year", "2016");
        System.out.println("input4:" + check(input4));

        Map<String, String> input5 = new HashMap<>();
        input5.put("condition", "new");
        input5.put("color", "red");
        input5.put("category", "sedan");
        input5.put("maker", "bmw");
        input5.put("year", "2015");
        System.out.println("input5:" + check(input5));
    }

    private static boolean check(Map<String, String> input) {
        for (Rule rule : safe(rules.get("must_not"))) {
            if (input.get(rule.name) != null && input.get(rule.name).equals(rule.value)) {
                System.out.println("[" + rule.name + "] must not be [" + rule.value + "]");
                return false;
            }
        }

        for (Rule rule : safe(rules.get("must"))) {
            if (input.get(rule.name) == null || !input.get(rule.name).equals(rule.value)) {
                System.out.println("[" + rule.name + "] must be [" + rule.value + "]");
                return false;
            }
        }

        if (rules.get("or") != null) {
            boolean orConditionMatch = false;
            for (Rule rule : safe(rules.get("or"))) {
                if (input.get(rule.name) != null && input.get(rule.name).equals(rule.value)) {
                    orConditionMatch = true;
                }
            }

            if (!orConditionMatch) {
                System.out.println("none of the \"or\" condition matches");
                return false;
            }
        }
        return true;
    }

    private static List<Rule> safe(List other) {
        return other == null ? Collections.EMPTY_LIST : other;
    }

    public static class Rule {
        public String name;
        public String value;

        public Rule(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}
