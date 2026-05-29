import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.Comparator.*;
import static java.util.Comparator.comparing;
import static java.lang.System.out;
import static java.util.stream.Collectors.groupingBy;

public class Main {
    public static void main(String[] args) {

        out.println("\nНайдите в списке целых чисел 3-е наибольшее число " +
                "(пример: 5 2 10 9 4 3 10 1 13 => 10)");
        Integer result = Stream.of(5, 2, 10, 9, 4, 3, 10, 1, 13)
                .sorted(reverseOrder())
                .toList().get(2);
        out.println(result);

        out.println("\nНайдите в списке целых чисел наибольшее число " +
                "(пример: 5 2 10 9 4 3 10 1 13 => 9)");
        Integer result2 = Stream.of(5, 2, 10, 9, 4, 3, 10, 1, 13)
                .distinct()
                .sorted(naturalOrder())
                .toList().get(2);
        out.println(result2);

        out.println("\nИмеется список объектов типа Сотрудник (имя, возраст, должность), " +
                "необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер», " +
                "в порядке убывания возраста");
        List<String> result3 = Stream.of(
                        new Employee("Иван", 25, "Инженер"),
                        new Employee("Алексей", 30, "Инженер"),
                        new Employee("Мария", 28, "Инженер"),
                        new Employee("Ольга", 35, "Менеджер"),
                        new Employee("Дмитрий", 40, "Инженер"),
                        new Employee("Елена", 22, "Инженер"),
                        new Employee("Андрей", 33, "Инженер"))
                .filter(x -> x.position().equals("Инженер"))
                .sorted(comparing(Employee::age).reversed())
                .limit(3)
                .map(Employee::name)
                .toList();
        out.println(result3);

        out.println("\nИмеется список объектов типа Сотрудник (имя, возраст, должность), " +
                "посчитайте средний возраст сотрудников с должностью «Инженер»");
        double result4 = Stream.of(
                        new Employee("Иван", 25, "Инженер"),
                        new Employee("Алексей", 30, "Инженер"),
                        new Employee("Мария", 28, "Инженер"),
                        new Employee("Ольга", 35, "Менеджер"),
                        new Employee("Дмитрий", 40, "Инженер"),
                        new Employee("Елена", 22, "Инженер"),
                        new Employee("Андрей", 33, "Инженер"))
                .filter(x -> x.position().equals("Инженер"))
                .mapToInt(Employee::age)
                .average()
                .orElseThrow();
        out.println(result4);

        out.println("\nНайдите в списке слов самое длинное");
        String result5 = Stream.of("слово", "самоедлинноеслово", "риск", "путь", "мир")
                .max(comparingInt(String::length))
                .orElseThrow();
        out.println(result5);

        out.println("\nИмеется строка с набором слов в нижнем регистре, разделенных пробелом. " +
                "Постройте хеш-мапы, в которой будут хранится пары: слово - сколько раз оно встречается во входной строке");
        Map<String, Long> result6 = stream("строка с набором слов слов в нижнем регистре " .split(" "))
                .collect(groupingBy(String::new, Collectors.counting()));
        out.println(result6);

        out.println("\nОтпечатайте в консоль строки из списка в порядке увеличения длины слова, " +
                "если слова имеют одинаковую длины, то должен быть сохранен алфавитный порядок");
        Map<Integer, List<String>> result7 = Stream.of("слово", "самоедлинноеслово", "бокс", "риск", "путь", "ария", "мир")
                .sorted()
                .collect(groupingBy(String::length, TreeMap::new, Collectors.toList()));
        out.println(result7);

        out.println("\nИмеется массив строк, в каждой из которых лежит набор из 5 слов, разделенных пробелом, " +
                "найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них");
        String result8 = Stream.of("раз два три четыре пять", "самоедлинноеслово1 бокс риск путь ария", "самоедлинноеслово2 риск путь ария мир")
                .flatMap(x -> Stream.of(x.split(" ")))
                .max(comparing(String::length)).orElseThrow();
        out.println(result8);
    }
}

record Employee(String name, int age, String position) {
}