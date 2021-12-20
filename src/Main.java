import java.io.*;
import java.util.*;

public class Main {
    static int count = 0;
    static ArrayList<String> ComponentForOutput = new ArrayList<>();
    static ArrayList<LinkedList<Integer>> VertexForOutput = new ArrayList<>();
    static int str;
    static int CountForOutput = 0;
    static int componentCount = 0;
    static int n = 0;
    static int NumberOfAdjacentVertices = 0;
    static int connectivityCounter = 0;
    static int s = 0;
    static int NumberOfComponent = 1;
    static double chance = 50;
    static int[][] matrix;
    static int[] vis;
    static int[] component;
    static int[] component_ob;
    static int[] vDelete;
    static int[] vSkip;

    static int[][] StronglyCoupledDigraph = new int[][]{
            {0,1,0,1,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,1,1,0,0},
            {0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,0},
            {0,0,1,0,0,0,0,0,1},
            {0,0,0,0,0,0,1,0,0}
    };
    static int[][] WeaklyCoupledDigraph = new int[][]{
            {0,1,0,1,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,1,1,0,0},
            {0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,0},
            {0,0,0,0,0,0,0,0,1},
            {0,0,0,0,0,0,1,0,0}
    };
    static int[][] UnboundDigraph = new int[][]{
            {0,1,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,1,1,0,0},
            {0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,0},
            {0,0,0,1,0,0,0,0,1},
            {0,0,0,0,0,0,1,0,0}
    };

    public static void DFS(int v, int[][] M, int str, int[] vis, int[] component) {
        vis[v] = 1;
        component[n] = v;

        for (int i = 0; i < str; i++) {
            if ((M[v][i] == 1) && (vis[i] == 0)) {
                n++;
                DFS(i, M, str, vis, component);
            }
        }
    }

    public static  void toNull(int[] matr, int str) {
        for (int i = 0; i < str; i++) {
            matr[i] = 0;
        }
    }

    public static int Connectivity(int[] vis, int str) {
        var l = 0;
        for (int i = 0; i < str; i++) {
            if (vis[i] == 1)
                l++;
        }
        if (l == str)
            l = 1;
        else
            l = 0;
        return l;
    }

    public static void Component(int[][] M, int str, int[] component, int[] v_delete, int[] v_propysk, int[] component_ob, int[] vis) {
        NumberOfAdjacentVertices = 0;
        for (int i = 0; i < str; i++)
            if (v_propysk[i] == 0) {
                DFS(i, M, str, vis, component);
                int r = n;
                componentCount = n;
                s = 0;

                for (int j = 0; j < componentCount + 1; j++)
                    component_ob[j] = component[j];
                toNull(vis, str);
                toNull(component, str);
                n = 0;
                for (int j = 0; j < str; j++) {
                    DFS(j, M, str, vis, component);
                    for (int m = 0; m < componentCount + 1; m++) {
                        for (int t = 0; t < n + 1; t++)
                            if (component_ob[m] == component[t])
                                NumberOfAdjacentVertices++;
                    }
                    if (n == componentCount) {
                        if (NumberOfAdjacentVertices == (componentCount + 1))
                            v_propysk[j] = 1;
                    } else {
                        v_delete[s] = j;
                        s++;
                    }

                    n = 0;
                    toNull(vis, str);
                    toNull(component, str);
                    NumberOfAdjacentVertices = 0;
                }
                r = ConnectivityCheck(v_delete, component_ob, r);
                ComponentWrite(str, component, v_delete, component_ob, vis, r);
            }
    }

    private static int ConnectivityCheck(int[] v_delete, int[] component_ob, int r) {
        if (s != 0) {
            for (int q = 0; q < s; q++) {
                for (int z = 0; z < r + 1; z++)
                    if (component_ob[z] == v_delete[q]) {
                        component_ob[z] = 0;
                        for (int x = z; x < r + 1; x++) {
                            if (x == r)
                                component_ob[x] = 0;
                            else
                                component_ob[x] = component_ob[x + 1];
                        }
                        r--;
                    }
            }
        }
        return r;
    }

    private static void ComponentWrite(int str, int[] component, int[] v_delete, int[] component_ob, int[] vis, int r) {
        ComponentForOutput.add("Компонента " + NumberOfComponent + ":");
        System.out.println(ComponentForOutput.get(count));
        VertexForOutput.add(new LinkedList<>());
        for (int y = 0; y < r + 1; y++) {
            VertexForOutput.get(count).add(y, (component_ob[y] + 1));
            System.out.print(VertexForOutput.get(count).get(y) + " ");
        }
        count++;
        System.out.println("\n");
        NumberOfComponent++;
        componentCount = 0;
        toNull(vis, str);
        toNull(component_ob, str);
        toNull(v_delete, str);
        toNull(component, str);
        n = 0;
    }

    private static String baseMenu() {
        System.out.println("Вы хотите сгенерировать матрицу смежности?(Y\\N)");
        Scanner in1 = new Scanner(System.in);
        var d = in1.next();
        if ((Objects.equals(d, "y")) || (Objects.equals(d, "Y"))){
            System.out.println("Введите количество вершин в орграфе: ");
            Scanner in2 = new Scanner(System.in);
            str = in2.nextInt();

            System.out.println("Вы хотите ввести вероятность генерации ребра?(Y\\N)");
            Scanner in3 = new Scanner(System.in);
            var pro = in3.next();
            if ((Objects.equals(pro, "y")) || (Objects.equals(pro, "Y"))){
                System.out.println("Введите вероятность генерации ребра от 0 до 1: ");
                Scanner in4 = new Scanner(System.in);
                chance = in4.nextDouble();
                if (chance <= 1)
                    chance = chance * 100;
            }
            else
                System.out.println("Вероятность генерации ребра равна 0,5\n");

            matrix = new int[str][str];

            System.out.println(matrix.length);
            for (int i = 0; i < str; i++){
                for (int j = 0; j < str; j++){
                    if (i == j)
                        matrix[i][j] = 0;
                    else{
                        int tmp = (int) (Math.random() * 100);
                        if (tmp < chance)
                            matrix[i][j] = 1;
                        else
                            matrix[i][j] = 0;
                    }
                }
            }
        }
        return d;
    }

    private static void DeterminingTheDegreeOfConnectivityOfDigraph() {
        CountForOutput = s;
        if (s > 0){
            System.out.println("Орграф связный");
            DFS(0, matrix, str, vis, component);
            for (int i = 0; i < n+1; i++)
                component_ob[i] = component[i];
            componentCount = n;
            toNull(vis, str);
            toNull(component, str);
            n = 0;
            for (int m = 1; m < str; m++){
                DFS(m, matrix, str, vis, component);
                for (int i = 0; i < componentCount +1; i++){
                    for (int j = 0; j < n+1; j++)
                        if (component_ob[i] == component[j])
                            NumberOfAdjacentVertices++;
                }
                if (NumberOfAdjacentVertices == str){
                    connectivityCounter++;
                    NumberOfAdjacentVertices = 0;
                    n = 0;
                    toNull(vis, str);
                    toNull(component, str);
                }
            }
            if (connectivityCounter == (str - 1)){
                System.out.println("Орграф сильно связный");
                System.out.println("Компонента сильной связности орграфа: \n");
                ComponentForOutput.add("Компонента " + NumberOfComponent + ":");
                System.out.println(ComponentForOutput.get(count));
                VertexForOutput.add(new LinkedList<>());
                for (int i = 0; i < componentCount +1; i++){
                    VertexForOutput.get(count).add(i, (component_ob[i] + 1));
                    System.out.print((VertexForOutput.get(count).get(i) + " "));
                }
                count++;
                System.out.println();
            }
            else {
                componentCount = 0;
                n = 0;
                toNull(vis, str);
                toNull(component, str);
                toNull(component_ob, str);
                System.out.println("Орграф слабо связный");
                System.out.println("Компонента сильной связности орграфа: \n");
                Component(matrix, str, component, vDelete, vSkip, component_ob, vis);
            }
        }
        else {
            componentCount = 0;
            toNull(component_ob, str);
            System.out.println("Орграф несвязный");
            System.out.println("Компонента сильной связности орграфа: \n");
            Component(matrix, str, component, vDelete, vSkip, component_ob, vis);
        }
    }

    private static void CountingTheNumberOfLinks() {
        for (int i = 0; i < str; i++){
            DFS(i, matrix, str, vis, component);
            if (Connectivity(vis, str) == 1)
                s++;
            toNull(vis, str);
            toNull(component, str);
            n = 0;
        }
        toNull(vis, str);
        toNull(component, str);
        n = 0;
    }

    private static void InitializingArrays() {
        vis = new int[str];
        component = new int[str];
        component_ob = new int[str];
        vDelete = new int[str];
        vSkip = new int[str];


        for (int i = 0; i < str; i++){
            vis[i] = 0;
            component[i] = 0;
            component_ob[i] = 0;
            vDelete[i] = 0;
            vSkip[i] = 0;
        }
    }

    private static void Output() {
        System.out.print("   ");
        for (int i = 0; i < str; i++)
            System.out.print(" " + (i + 1));
        System.out.println();

        for (int i = 0; i < str; i++){
            System.out.print(" " + (i + 1) + ")");
            for (int j = 0; j < str; j++){
                System.out.print(" " + matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void mainMenu(String d) {
        if ((Objects.equals(d, "n")) || (Objects.equals(d, "N"))){
            System.out.println("Выберите действие: ");
            System.out.println("1.Вывести сильно связный орграф");
            System.out.println("2.Вывести слабо связный орграф");
            System.out.println("3.Вывести несвязный орграф");
            System.out.println("Нажмите клавишу:");

            Scanner in5 = new Scanner(System.in);
            var action = in5.next();
            str = 9;

            matrix = new int[str][str];

            if (Objects.equals(action, "1")){
                for (int i = 0; i < str; i++)
                    for (int j = 0; j < str; j++)
                        matrix[i][j] = StronglyCoupledDigraph[i][j];
            }
            if (Objects.equals(action, "2")) {
                for (int i = 0; i < str; i++)
                    for (int j = 0; j < str; j++)
                        matrix[i][j] = WeaklyCoupledDigraph[i][j];
            }
            if (Objects.equals(action, "3")) {
                for (int i = 0; i < str; i++)
                    for (int j = 0; j < str; j++)
                        matrix[i][j] = UnboundDigraph[i][j];
            }
        }
    }

    public static void WriteToFile(String filename, int[][] matrix, String d) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));

        for (int i = 0; i < matrix.length; i++) {
            outputWriter.write(Arrays.toString(matrix[i]));
            outputWriter.newLine();
        }
        outputWriter.newLine();
        outputWriter.newLine();

        if (CountForOutput > 0) {
            outputWriter.write("Орграф связный");
            if (connectivityCounter == (str - 1)) {
                outputWriter.newLine();
                outputWriter.write("Орграф сильно связный");
            }
            else {
                outputWriter.newLine();
                outputWriter.write("Орграф слабо связный");
            }
        }
        else {
            outputWriter.write("Орграф несвязный");
        }
        outputWriter.newLine();
        outputWriter.write("Компонента сильной связности орграфа:");

        for (int i = 0; i < count; i++){
            outputWriter.newLine();
            outputWriter.newLine();
            outputWriter.write(ComponentForOutput.get(i));
            outputWriter.newLine();
            for (int j = 0; j < VertexForOutput.get(i).size(); j++){
                outputWriter.write(VertexForOutput.get(i).get(j) + " ");
            }
        }

        outputWriter.flush();
        outputWriter.close();
    }

    public static void main(String[] args) throws IOException {
        String d = baseMenu();
        mainMenu(d);
        Output();
        InitializingArrays();
        CountingTheNumberOfLinks();
        DeterminingTheDegreeOfConnectivityOfDigraph();

        String filename = "test";
        WriteToFile(filename, matrix, d);
    }
}
