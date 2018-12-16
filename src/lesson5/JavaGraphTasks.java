package lesson5;

import kotlin.NotImplementedError;

import java.util.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     *
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     */
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ:
     *
     *      G    H
     *      |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     *
     * Дан граф без циклов (получатель), например
     *
     *      G -- H -- J
     *      |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     *
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     *
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     *
     * В данном случае ответ (A, E, F, D, G, J)
     *
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */
    // Трудоемкость O(N^2*M), где N - количество ребер, M - количество независимых множеств.
    // Ресурсоемкость O(N)
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        Set<Graph.Vertex> firstVertices = graph.getVertices();
        Set<Graph.Vertex> secondVertices = new HashSet<>();
        Set<Graph.Vertex> independentSet = new HashSet<>();
        Set<Graph.Vertex> maxIndependentSet = new HashSet<>();
        return findIndependentVertexSet(graph, maxIndependentSet, independentSet, firstVertices, secondVertices);
    }

    private static Set<Graph.Vertex> findIndependentVertexSet(Graph graph, Set<Graph.Vertex> maxIndependentSet,
                                                              Set<Graph.Vertex> independentSet,
                                                              Set<Graph.Vertex> firstVertices,
                                                              Set<Graph.Vertex> secondVertices) {
        while (isFind(graph, firstVertices, secondVertices)) {
            Graph.Vertex vertex = firstVertices.stream().findAny().get();
            independentSet.add(vertex);
            Set<Graph.Vertex> newFirstVertices = new HashSet<>(firstVertices);
            Set<Graph.Vertex> newSecondVertices = new HashSet<>(secondVertices);
            newFirstVertices.removeIf(v -> graph.getNeighbors(v).contains(vertex));
            newSecondVertices.removeIf(v -> graph.getNeighbors(v).contains(vertex));
            newFirstVertices.remove(vertex);
            if (newFirstVertices.isEmpty() && newSecondVertices.isEmpty()) {
                if (independentSet.size() > maxIndependentSet.size()) {
                    maxIndependentSet = new HashSet<>(independentSet);
                }
            } else {
                maxIndependentSet = findIndependentVertexSet(graph, maxIndependentSet,
                        independentSet, newFirstVertices, newSecondVertices);
            }
            independentSet.remove(vertex);
            firstVertices.remove(vertex);
            secondVertices.add(vertex);
        }
        return maxIndependentSet;
    }

    private static boolean isFind(Graph graph, Set<Graph.Vertex> firstVertices, Set<Graph.Vertex> secondVertices) {
        boolean isFind = false;
        if (firstVertices.isEmpty())
            return false;
        for (Graph.Vertex s : secondVertices) {
            for (Graph.Vertex f : firstVertices) {
                isFind = graph.getNeighbors(s).contains(f);
                if (isFind) break;
            }
            if (!isFind)
                return false;
            isFind = false;
        }
        return true;
    }

    /**
     * Наидлиннейший простой путь.
     * Сложная
     *
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    // Трудоемкость O(N!), где N - количество вершин.
    // Ресурсоемкость O(N!)
    public static Path longestSimplePath(Graph graph) {
        LinkedList<Path> list = new LinkedList<>();
        for (Graph.Vertex vertex : graph.getVertices())
            list.add(new Path(vertex));
        Path result = null;
        int length = 0;
        while (!list.isEmpty()){
            Path currentPath = list.remove();
            if (currentPath.getLength() > length) {
                result = currentPath;
                length = currentPath.getLength();
                if (result.getLength() == graph.getVertices().size())
                    break;
            }
            List<Graph.Vertex> vertices = currentPath.getVertices();
            for (Graph.Vertex neighbor : graph.getNeighbors(vertices.get(vertices.size() - 1)))
                if (!currentPath.contains(neighbor))
                    list.add(new Path(currentPath, graph, neighbor));
        }
        return result;
    }
}
