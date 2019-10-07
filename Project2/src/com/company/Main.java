package com.company;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {


    //odczyt z pliku================================


    static int[][] readFile(){
        int tab[][] = null;
        int size = 0;

        List<String> lines = new ArrayList<String>();

        try{
            FileInputStream fstream = new FileInputStream(System.getProperty("user.home")+"/Tree.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null){
                lines.add(strLine.trim());
            }
            size = Integer.parseInt(lines.get(0));
            tab = new int [size][size];
            int iterator = 0;

            for (int i = 2; i < lines.size(); i++) {
                String str = lines.get(i).replaceAll("[^0-9]+", " ");
                List<String> ints = Arrays.asList(str.trim().split(" "));
                for (int j = 1; j < ints.size(); j++) {
                    tab[iterator][j - 1] = Integer.parseInt(ints.get(j));
                }
                iterator++;
            }

//            for (int i = 0; i < tab.length; i++) {
//                for (int j = 0; j < tab.length; j++) {
//                    System.out.print(tab[i][j] + " ");
//                }
//                System.out.println();
//            }

        }catch (IOException e){
            System.err.println("Error...");
        }

        return tab;
    }


    //odczyt z pliku================================


    static int edges = 0;

    static int m[][] = readFile();
    //static int m[][] = {{}, {1}, {4, 5}, {2, 6}, {}, {}, {3, 9, 10}, {}, {}, {8}};

    //static int m[][] = {{6}, {1}, {4, 5}, {2, 6}, {}, {}, {3, 9, 10}, {}, {}, {8}};
    //static int m [][] = {{}, {1, 3}, {2}};
    static boolean visited[] = new boolean[m.length];
    static boolean used [] = new boolean[m.length]; //когда ищем вершину
    static boolean isTree = true;
    static int max = 0;
    static int min = 0;
    //static LinkedList<Integer> list = new LinkedList<Integer>();

    static int getPrev(int m [][], int v){
        int prev = v;
        used[prev-1] = true; //помечаем этот элемент (с целью предотвращения зацикленности)
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {

                if(m[i][j] == 0) //<-------------------
                    break;

                if(m[i][j] == v) {
                    prev = i + 1;
                    i = m.length;
                    break;
                }
            }
        }

        //System.out.println(prev + "--" +used[prev-1]);

        if(prev != v){
            if(!used[prev-1])
                prev = getPrev(m, prev);
            else
                prev = -1;
        }

        return prev;
    }

    static int getVertex(int m[][]){
        int vertex;
        int prev = -1;
        for (int i = 0; i < m.length; i++) {

            if(m[i][0] == 0){ //<-------------
                prev = getPrev(m, i+1);
                break;
            }
        }
        vertex = prev;

        if(vertex == -1){
            isTree = false;
        }

        return vertex;
    }

    static void dfs(int v) {
        if(isTree && v!=-1) {
            if (!visited[v])
                visited[v] = true;
            else {
                isTree = false;
                return;
            }

            edges++;
            //list.add(v + 1);
            max += (v + 1);
            min += (v + 1);

            if (m[v].length != 0) {
                for (int i = 0; i < m[v].length; i++) {
                    dfs(m[v][i] - 1);
                }
            }
        }
    }

    public static void main(String[] args){

        int vertex = getVertex(m);
        int MAX = -1;
        int MIN = 1000;

        if(isTree) {
            //list.add(vertex);
            for (int i = 0; i < m[vertex - 1].length; i++) {
                if (m[vertex - 1][i] != 0) { //<-------------------
                    dfs(m[vertex - 1][i] - 1);
                    if (max > MAX)
                        MAX = max;
                    if (min < MIN)
                        MIN = min;

                    max = 0;
                    min = 0;
                }
            }
        }

        //System.out.println(list);
        //System.out.println(edges);

        if(edges != m.length -1)
            isTree = false;

        if(isTree) {
            System.out.println("YES");
            System.out.println(MAX + " "+MIN);
        }
        else System.out.println("NO");
    }

}