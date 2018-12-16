package lesson6;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.min;
@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Средняя
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    // Трудоемкость O(N^2), где N - количество чисел.
    // Ресурсоемкость O(N)
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.size() <= 1) {
            return list;
        }
        ArrayList<Integer> result = new ArrayList<>();
        int size = list.size();
        int[] f = new int[size];
        int[] prev = new int[size];
        for (int i = 0; i < size; i++) {
            f[i] = 1;
            prev [i] = -1;
            for (int j = 0; j < i; j++)
                if (list.get(i) > list.get(j) && f[i] <  f[j] + 1){
                    f[i] = f[j] + 1;
                    prev[i] = j;
                }
        }
        int max = f[0];
        int pos = 0;
        for (int i = 0; i < size; i++) {
            if (f[i] > max) {
                max = f[i];
                pos = i;
            }
        }
        while (pos != -1){
            result.add(list.get(pos));
            pos = prev[pos];
        }
        Collections.reverse(result);
        return result;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Сложная
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    // Трудоемкость O(N*M), где N - количество строк, М - количество столбцов.
    // Ресурсоемкость O(N*M)
    public static int shortestPathOnField(String inputName) throws IOException {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputName))) {
           for (String line; (line = br.readLine()) != null;)
               list.add(line.replaceAll(" ", ""));
        }
        int rows = list.size();
        int columns = list.get(0).length();
        int[][] matrix = new int[rows][columns];
        matrix[0][0] = list.get(0).charAt(0) - 48;
        for (int i = 1; i < rows; i++)
            matrix[i][0] = list.get(i).charAt(0) - 48 + matrix[i - 1][0];
        for (int i = 1; i < columns; i++)
            matrix[0][i] = list.get(0).charAt(i) - 48 + matrix[0][i - 1];
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                matrix[i][j] = min(matrix[i - 1][j], min(matrix[i][j - 1], matrix[i - 1][j - 1]))
                        + list.get(i).charAt(j) - 48;
            }
        }
        return matrix[rows - 1][columns - 1];
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
