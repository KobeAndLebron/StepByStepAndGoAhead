package com.cjs.gohead.jdk1_8.stream;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class Student {
    private Integer id; // ID
    private Grade grade; // 年级
    private Integer score; // 分数

    public Student(Integer id, Grade grade, Integer score) {
        this.id = id;
        this.grade = grade;
        this.score = score;
    }

    public static void main(String[] args) {
        final Collection<Student> students = Arrays.asList(
            new Student(1, Grade.FIRST, 60),
            new Student(2, Grade.SECOND, 80),
            new Student(3, Grade.FIRST, 100),
            new Student(4, Grade.THTREE, 40)
        );
        // 找一年级的所有学生，然后得到按学生分数值降序排序好的学生ID的集合，如果没有Stream, 在Java7中只能遍历三次来实现,
        iterator_jdk7(students);
        stream_jdk8(students);

        // Collectors的其余reduce操作(主要包括聚合操作和集合转化操作).
        groupBy(students);
        groupByWithSum(students);

        partitioningBy(students);

        averagingInt(students);

        joining(students);

        list2Map(students);
    }

    /**
     * 将List转化为Map
     * @param students
     */
    private static void list2Map(Collection<Student> students) {
        Map<Grade, String> grade2Score = students.stream().collect(Collectors.toMap(
            Student::getGrade, // key是什么
            student -> student.getScore() + "", // value是什么
            (score1, score2) -> score1 + ";" + score2// 当发生冲突的时候, 取第一个Student的score.
        ));
        System.out.println("Convert list to map: " + grade2Score);
    }

    private static void stream_jdk8(Collection<Student> students) {
        List<Integer> studentIds = students.stream()
            .filter(student -> student.getGrade().equals(Grade.FIRST))
            .sorted(Comparator.comparingInt(Student::getScore))
            .map(Student::getId).collect(Collectors.toList());

        System.out.println(studentIds);
    }

    public static void iterator_jdk7(Collection<Student> students) {
        List<Student> gradeOneStudents = new LinkedList<>();
        for (Student student : students) {
            if (Grade.FIRST.equals(student.getGrade())) {
                gradeOneStudents.add(student);
            }
        }

        Collections.sort(gradeOneStudents, (o1, o2) -> o1.getScore().compareTo(o2.getScore()));

        List<Integer> studentIds = new LinkedList<>();
        for (Student t : gradeOneStudents) {
            studentIds.add(t.getId());
        }

        System.out.println(studentIds);
    }

    /**
     * groupBy: 根据group by的判断条件, 将流进行分组. 类似于SQL的groupBy操作.
     *
     * 按照班级分组, 然后输出每个组的学生列表.
     *
     * @param students
     */
    public static void groupBy(Collection<Student> students) {
        Map<Grade, List<Student>> studentByGrade = students.stream().collect(Collectors.groupingBy(Student::getGrade));
        studentByGrade.forEach((grade1, students1) -> {
            System.out.println("GroupName: " + grade1);
            System.out.print("StudentList in group: ");
            students1.forEach((student) -> {
                System.out.print(student.getId() + ":" + student.getScore() + ", ");
            });
            System.out.println();
        });
    }

    /**
     * groupBy: 根据group by的判断条件, 将流进行分组. 并且对每一组进行聚合操作.
     *
     * 按照班级分组, 并且求每一组的成绩总和.
     *
     * @param students
     */
    public static void groupByWithSum(Collection<Student> students) {
        Map<Grade, Integer> sumByGrade =
            students.stream().collect(Collectors.groupingBy(Student::getGrade, Collectors.summingInt(Student::getScore)));
        sumByGrade.forEach((grade, sum) -> {
            System.out.println("Grade: " + grade.name() + ", Sum of score: " + sum);
        });
    }

    /**
     * partitioningBy: 根据{@linkplain java.util.function.Predicate}将集合分区然后把它们放进{@code Map<Boolean, List<T>>}.
     */
    public static void partitioningBy(Collection<Student> students) {
        Map<Boolean, List<Student>> collect = students.stream().collect(Collectors.partitioningBy(student -> student.getScore() >= 60));
        collect.forEach((score, students1) -> {
            System.out.println("Is passed?: " + score);
            System.out.print("StudentList in score: ");
            students1.forEach((student) -> {
                System.out.print(student.getId() + ":" + student.getScore() + ", ");
            });
            System.out.println();
        });
    }

    /**
     * averaging.
     *
     * 求所有学生分数的平均值.
     *
     * @param students
     */
    private static void averagingInt(Collection<Student> students) {
        Double average = students.stream().collect(Collectors.averagingInt(Student::getScore));
        System.out.println("学生成绩的平均值: " + average);
    }

    /**
     * joining: concatenate input element, delimiter, specified prefix and postfix(prefix and postfix are optional).
     *
     * 输出: Students x and y and z are good students. (x y z为成绩大于等于60分的学生)
     *
     * @param students
     */
    private static void joining(Collection<Student> students) {
        String collect = students.stream().
            filter(p -> p.getScore() >= 60). // 过滤掉不及格的学生.
            map(p -> p.getId() + ""). // 只取出学生的ID, 形成新的Stream.
            collect(Collectors.joining(" and ", "Students ", " are good students.")); // 最后得到Students x and y and z are good students.
        System.out.println(collect);
    }
}
