package ECOO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ecoo20p2 {
    static class Offer {
        int price;
        int quantity;

        Offer(int price, int quantity) {
            this.price = price;
            this.quantity = quantity;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(reader.readLine());
        int T = Integer.parseInt(st.nextToken());

        for (int tc = 0; tc < T; tc++) {
            st = new StringTokenizer(reader.readLine());
            int N = Integer.parseInt(st.nextToken());
            Map<String, List<Offer>> offersMap = new HashMap<>();
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(reader.readLine());
                int M = Integer.parseInt(st.nextToken());

                for (int j = 0; j < M; j++) {
                    st = new StringTokenizer(reader.readLine());
                    String itemName = st.nextToken();
                    int price = Integer.parseInt(st.nextToken());
                    int qty = Integer.parseInt(st.nextToken());

                    offersMap
                            .computeIfAbsent(itemName, k -> new ArrayList<>())
                            .add(new Offer(price, qty));
                }
            }
            st = new StringTokenizer(reader.readLine());
            int K = Integer.parseInt(st.nextToken());
            long totalCost = 0L;

            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(reader.readLine());
                String wantItem = st.nextToken();
                int needQty = Integer.parseInt(st.nextToken());
                List<Offer> list = offersMap.getOrDefault(wantItem, Collections.emptyList());
                list.sort(Comparator.comparingInt(o -> o.price));

                int remaining = needQty;
                for (Offer off : list) {
                    if (remaining <= 0) break;
                    int take = Math.min(remaining, off.quantity);
                    totalCost += 1L * take * off.price;
                    remaining -= take;
                }
            }
            System.out.println(totalCost);
        }
    }
}