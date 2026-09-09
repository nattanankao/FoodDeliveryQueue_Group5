import java.util.*;

public class Main {

    // =========================
    // 1) ข้อมูลของ Order
    // =========================
    static class Order {
        String id;
        int orderTime;
        String type;
        int prepTime;
        int priority; // 1 = Express, 2 = Normal, 3 = Large

        Order(String id, int orderTime, String type, int prepTime, int priority) {
            this.id = id;
            this.orderTime = orderTime;
            this.type = type;
            this.prepTime = prepTime;
            this.priority = priority;
        }

        String priorityName() {
            if (priority == 1) return "Express";
            if (priority == 2) return "Normal";
            return "Large";
        }

        public String toString() {
            return id + "(" + priorityName() + "," + prepTime + "m)";
        }
    }

    // Scenario ตามโจทย์
    static List<Order> createScenario() {
        return Arrays.asList(
            new Order("O1", 1, "Food", 15, 2),
            new Order("O2", 2, "Food", 5, 1),
            new Order("O3", 3, "Food", 10, 2),
            new Order("O4", 4, "Food", 8, 1),
            new Order("O5", 5, "Food", 25, 3)
        );
    }

    // =========================
    // 2) Algorithm A : FIFO
    // =========================
    static void algorithmA(List<Order> orders) {
        Queue<Order> queue = new ArrayDeque<>();
        queue.addAll(orders);

        System.out.println("\n===== Algorithm A : FIFO Queue =====");
        processQueue(queue);
    }

    // =========================
    // 3) Algorithm B : Priority
    // =========================
    static void algorithmB(List<Order> orders) {
        PriorityQueue<Order> queue = new PriorityQueue<>(
            Comparator.comparingInt((Order o) -> o.priority)
                      .thenComparingInt(o -> o.orderTime)
        );

        queue.addAll(orders);

        System.out.println("\n===== Algorithm B : Priority Queue =====");
        processQueue(queue);
    }

    // ใช้ร่วมกันทั้ง Algorithm A และ B
    static void processQueue(Queue<Order> queue) {
        int currentTime = 0;
        int totalWaiting = 0;
        int maxWaiting = 0;
        int count = queue.size();

        System.out.println("Order\tPriority\tWaiting\tPrep");

        while (!queue.isEmpty()) {
            Order o = queue.poll();

            int waiting = currentTime;
            totalWaiting += waiting;
            maxWaiting = Math.max(maxWaiting, waiting);

            System.out.printf("%s\t%s\t\t%d\t%d%n",
                    o.id, o.priorityName(), waiting, o.prepTime);

            currentTime += o.prepTime;
        }

        double average = count == 0 ? 0 : (double) totalWaiting / count;

        System.out.printf("Average Waiting Time = %.2f นาที%n", average);
        System.out.println("Maximum Waiting Time = " + maxWaiting + " นาที");
    }

    // =========================
    // 4) Queue Trace 10 Operations
    // =========================
    static void queueTrace() {
        Queue<Order> q = new ArrayDeque<>();

        System.out.println("\n===== Queue Trace : 10 Operations =====");
        System.out.println("Step | Operation       | Queue Before       | Queue After        | Output");
        System.out.println("-----+-----------------+--------------------+--------------------+-------");

        traceEnqueue(q, new Order("O1", 1, "Food", 15, 2), 1);
        traceEnqueue(q, new Order("O2", 2, "Food", 5, 1), 2);
        traceEnqueue(q, new Order("O3", 3, "Food", 10, 2), 3);
        tracePeek(q, 4);
        traceDequeue(q, 5);
        traceEnqueue(q, new Order("O4", 4, "Food", 8, 1), 6);
        traceEnqueue(q, new Order("O5", 5, "Food", 25, 3), 7);
        tracePeek(q, 8);
        traceDequeue(q, 9);
        traceDequeue(q, 10);

        System.out.println();
        System.out.println("หมายเหตุ: [] = Queue ว่าง และ Output คือ Order ที่ถูกดู/นำออก");
    }

    // แสดงเฉพาะ Order ID เพื่อให้ Queue สั้นและอ่านง่าย
    static String queueIds(Queue<Order> q) {
        if (q.isEmpty()) return "[]";

        StringBuilder result = new StringBuilder("[");
        for (Order o : q) {
            if (result.length() > 1) result.append(", ");
            result.append(o.id);
        }
        result.append("]");
        return result.toString();
    }

    static void printTrace(int step, String operation,
                           String before, String after, String output) {
        System.out.printf("%-4d | %-15s | %-18s | %-18s | %s%n",
                step, operation, before, after, output);
    }

    static void traceEnqueue(Queue<Order> q, Order o, int step) {
        String before = queueIds(q);
        q.offer(o);
        String after = queueIds(q);

        printTrace(step, "ENQUEUE " + o.id, before, after, "-");
    }

    static void traceDequeue(Queue<Order> q, int step) {
        String before = queueIds(q);
        Order out = q.poll();
        String after = queueIds(q);

        printTrace(step, "DEQUEUE", before, after,
                out == null ? "null" : out.id);
    }

    static void tracePeek(Queue<Order> q, int step) {
        String before = queueIds(q);
        Order out = q.peek();
        String after = queueIds(q);

        printTrace(step, "PEEK", before, after,
                out == null ? "null" : out.id);
    }

    // =========================
    // 5) Test Cases 6 แบบ
    // =========================
    static void runTests() {
        System.out.println("\n===== 6 Test Cases =====");

        // 1. Normal Case
        Queue<Order> q1 = new ArrayDeque<>();
        q1.offer(new Order("O1", 1, "Food", 15, 2));
        q1.offer(new Order("O2", 2, "Food", 5, 1));
        System.out.println("1) Normal Case: " + q1);

        // 2. Empty Queue
        Queue<Order> q2 = new ArrayDeque<>();
        System.out.println("2) Empty Queue: poll=" + q2.poll() + ", peek=" + q2.peek());

        // 3. Single Item
        Queue<Order> q3 = new ArrayDeque<>();
        q3.offer(new Order("O1", 1, "Food", 10, 2));
        System.out.println("3) Single Item: " + q3.poll());

        // 4. Large Queue
        Queue<Order> q4 = new ArrayDeque<>();
        for (int i = 1; i <= 10000; i++) {
            q4.offer(new Order("O" + i, i, "Food", 5, 2));
        }
        System.out.println("4) Large Queue: size=" + q4.size());

        // 5. Special / Edge Case : Priority เท่ากัน
        PriorityQueue<Order> q5 = new PriorityQueue<>(
            Comparator.comparingInt((Order o) -> o.priority)
                      .thenComparingInt(o -> o.orderTime)
        );
        q5.offer(new Order("O1", 1, "Food", 5, 1));
        q5.offer(new Order("O2", 2, "Food", 5, 1));
        q5.offer(new Order("O3", 3, "Food", 5, 1));

        System.out.print("5) Same Priority: ");
        while (!q5.isEmpty()) {
            System.out.print(q5.poll().id + " ");
        }
        System.out.println();

        // 6. Cancel Case
        Queue<Order> q6 = new ArrayDeque<>();
        q6.offer(new Order("O1", 1, "Food", 15, 2));
        q6.offer(new Order("O2", 2, "Food", 5, 1));
        q6.offer(new Order("O3", 3, "Food", 10, 2));

        boolean removed = q6.removeIf(o -> o.id.equals("O2"));
        System.out.println("6) Cancel O2: removed=" + removed + ", queue=" + q6);
    }

    // =========================
    // 6) Experiment
    // =========================
    static void runExperiment() {
        int[] sizes = {100, 1000, 10000, 50000};
        int rounds = 5;
        long seed = 12345L;

        System.out.println("\n===== Experiment =====");
        System.out.println("n\tFIFO avg(ns)\tPriority avg(ns)");

        for (int n : sizes) {
            // Warm-up
            for (int i = 0; i < 3; i++) {
                measureFIFO(n, seed);
                measurePriority(n, seed);
            }

            long fifoTotal = 0;
            long priorityTotal = 0;

            for (int i = 0; i < rounds; i++) {
                fifoTotal += measureFIFO(n, seed);
                priorityTotal += measurePriority(n, seed);
            }

            System.out.printf("%d\t%.2f\t\t%.2f%n",
                    n,
                    fifoTotal / (double) rounds,
                    priorityTotal / (double) rounds);
        }
    }

    static long measureFIFO(int n, long seed) {
        Random random = new Random(seed);
        Queue<Order> q = new ArrayDeque<>();

        long start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            int prep = random.nextInt(30) + 1;
            int priority = random.nextInt(3) + 1;
            q.offer(new Order("O" + i, i, "Food", prep, priority));
        }

        while (!q.isEmpty()) {
            q.poll();
        }

        return System.nanoTime() - start;
    }

    static long measurePriority(int n, long seed) {
        Random random = new Random(seed);

        PriorityQueue<Order> q = new PriorityQueue<>(
            Comparator.comparingInt((Order o) -> o.priority)
                      .thenComparingInt(o -> o.orderTime)
        );

        long start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            int prep = random.nextInt(30) + 1;
            int priority = random.nextInt(3) + 1;
            q.offer(new Order("O" + i, i, "Food", prep, priority));
        }

        while (!q.isEmpty()) {
            q.poll();
        }

        return System.nanoTime() - start;
    }

    // =========================
    // 7) Main
    // =========================
    public static void main(String[] args) {
        List<Order> orders = createScenario();

        System.out.println("FOOD DELIVERY ORDER QUEUE");
        System.out.println("O1 Normal  15 min");
        System.out.println("O2 Express  5 min");
        System.out.println("O3 Normal  10 min");
        System.out.println("O4 Express  8 min");
        System.out.println("O5 Large   25 min");

        algorithmA(orders);
        algorithmB(orders);
        queueTrace();
        runTests();
        runExperiment();

        System.out.println("\n===== Summary =====");
        System.out.println("FIFO Average Waiting Time     = 20.60 นาที");
        System.out.println("Priority Average Waiting Time = 16.80 นาที");
        System.out.println("Priority Queue ทำให้ Express เร็วขึ้น");
        System.out.println("แต่ถ้ามี Express เข้ามาตลอด อาจเกิด Starvation");
    }
}
