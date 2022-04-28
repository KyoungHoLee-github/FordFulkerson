import java.io.*;
import java.util.*;

public class FordFulkerson {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st = null;



    static final int MAX_SIZE = 52;
    static int N;
    static int maxFlow;         //최대유량
    static int S;
    static int T = 25;
    static int[][] capacity;
    static int[][] flow;
    static boolean[] check;

    public static int DFS(int source, int amount) {
        // 증가경로가 완성되면 해당 증가경로의 최소 잔여용량 리턴
        if(source == T) {
            return amount;
        }
        // 방문한 곳이면 리턴
        if(check[source]) {
            return 0;
        }

        check[source] = true;

        for(int sink = 0; sink < MAX_SIZE; sink++) {
            // 유량이 흐를 수 있는지
            if(capacity[source][sink] > flow[source][sink]) {
                // 현재 도달한 경로까지의 최소 잔여용량 저장
                int flowAmount = DFS(sink, Math.min(amount, capacity[source][sink]-flow[source][sink]));
                if(flowAmount > 0) {
                    // 잔여용량 갱신
                    flow[source][sink] += flowAmount;
                    flow[sink][source] -= flowAmount;   //유량의 대칭성

                    return flowAmount;
                }
            }
        }
        return 0;
    }

    public static int charToInt(char c) {
        if('a' <= c && c <= 'z') {
            c -= 6;
        }
        return c - 65;
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        capacity = new int[MAX_SIZE][MAX_SIZE];
        flow = new int[MAX_SIZE][MAX_SIZE];
        check = new boolean[MAX_SIZE];

        N = Integer.parseInt(br.readLine());    //값이 숫자인 문자열을 인자 값을 정수로 변환

        long startTime = System.nanoTime();

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int start = charToInt(st.nextToken().charAt(0));
            int end = charToInt(st.nextToken().charAt(0));
            int weight = Integer.parseInt(st.nextToken());
            
            capacity[start][end] += weight;
            capacity[end][start] += weight;
        }

        // 더 이상 증가경로가 없을 때 까지 반복
        while(true) {
            Arrays.fill(check, false);                  //초기화

            int flowAmount = DFS(S, Integer.MAX_VALUE);     //DFS
            if(flowAmount == 0) {
                break;
            }
            maxFlow += flowAmount;
        }

        System.out.println(maxFlow);

        long endTime = System.nanoTime();
        System.out.println("running time : " + (endTime - startTime) + " ns");
    }
}

