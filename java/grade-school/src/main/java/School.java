import java.util.*;
import java.util.stream.Collectors;

public class School {
    private static final List<String> EMPTY_LIST =
            Collections.unmodifiableList(new ArrayList<>());

    private List<Student> _db = new ArrayList<>();

    public void add(String name, Integer grade) {
        _db.add(new Student(name, grade));
    }

    public Map<Integer, List<String>> db() {
        return _db.stream()
                .collect(Collectors.groupingBy(Student::getGrade,
                        Collectors.mapping(Student::getName, Collectors.toList())));
    }

    public List<String> grade(Integer grade) {
        return db().getOrDefault(grade, EMPTY_LIST);
    }

    public Map<Integer, List<String>> sort() {
        return db().entrySet().stream()
                .map(integerListEntry -> new AbstractMap.SimpleEntry<Integer, List<String>>(
                        integerListEntry.getKey(),
                        integerListEntry.getValue().stream()
                                .sorted()
                                .collect(Collectors.toList())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static class Student {
        private final String name;
        private final Integer grade;

        public Student(String name, Integer grade) {
            this.name = name;
            this.grade = grade;
        }

        public String getName() {
            return name;
        }

        public Integer getGrade() {
            return grade;
        }
    }
}

