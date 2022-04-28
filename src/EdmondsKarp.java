import java.io.*;
import java.util.*;

public class EdmondsKarp {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st = null;

    static final int MAX_SIZE = 52;
    static int N;
    static int maxFlow;
    static int S;
    static int T = 25;
    static int[] aPath;
    static int[][] capacity;
    static int[][] flow;
    static Queue<Integer> q;

    public static int charToInt(char c) {
        if('a' <= c && c <= 'z') {
            c -= 6;
        }
        return c - 65;
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        capacity = new int[MAX_SIZE][MAX_SIZE];
        flow = new int[MAX_SIZE][MAX_SIZE];
        q = new ArrayDeque<Integer>();
        aPath = new int[MAX_SIZE];

        N = Integer.parseInt(br.readLine());

        long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int start = charToInt(st.nextToken().charAt(0));
            int end = charToInt(st.nextToken().charAt(0));
            int weight = Integer.parseInt(st.nextToken());

            capacity[start][end] += weight;
            capacity[end][start] += weight;
        }

        while(true) {
            q.clear();
            Arrays.fill(aPath, -1);

            aPath[S] = S;
            q.add(S);

            // BFS
            while(!q.isEmpty() && aPath[T] == -1) {
                int source = q.poll();

                for(int sink = 0; sink < MAX_SIZE; sink++) {
                    // 유량이 흐를 수 있고 방문x
                    if(capacity[source][sink] > flow[source][sink] && aPath[sink] == -1) {
                        q.offer(sink);
                        aPath[sink] = source;
                    }
                }
            }

            // 경로가 없으면 종료
            if(aPath[T] == -1) {
                break;
            }
            // 증가 경로의 최소 잔여 용량을 찾음
            int flowAmount = Integer.MAX_VALUE;
            for(int i = T; i != S; i = aPath[i]) {
                flowAmount = Math.min(capacity[aPath[i]][i] - flow[aPath[i]][i], flowAmount);
            }
            for(int i = T; i != S; i = aPath[i]) {
                flow[aPath[i]][i] += flowAmount;
                flow[i][aPath[i]] -= flowAmount; // 유량의 대칭성
            }

            maxFlow += flowAmount;
        }

        System.out.println(maxFlow);

        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
        System.out.println("running time : "+secDiffTime + "ms");
    }
}