# README 2 — อธิบายโค้ด Main.java แบบละเอียด

เอกสารนี้อธิบายโค้ดใน `Main.java` ทีละส่วน

---

# ส่วนที่ 1 — import

```java
import java.util.*;
```

ใช้สำหรับเรียก Class ที่จำเป็น เช่น

- List
- Arrays
- Queue
- ArrayDeque
- PriorityQueue
- Comparator
- Random

ใช้ `*` เพื่อให้โค้ดสั้นและอ่านง่าย

---

# ส่วนที่ 2 — Class Main

```java
public class Main {
```

เป็น Class หลักของโปรแกรม

Java จะเริ่มรันจาก:

```java
public static void main(String[] args)
```

ดังนั้นผู้ใช้ไม่ต้องเลือกรันหลาย Class

---

# ส่วนที่ 3 — Class Order

```java
static class Order
```

เราใส่ `Order` ไว้ภายใน `Main` เพื่อไม่ต้องสร้าง `Order.java` แยกอีกไฟล์

ข้อมูลที่เก็บ:

```java
String id;
int orderTime;
String type;
int prepTime;
int priority;
```

ความหมาย:

- `id` = Order ID
- `orderTime` = ลำดับเวลาที่เข้ามา
- `type` = ประเภทอาหาร
- `prepTime` = เวลาเตรียมอาหาร
- `priority` = ระดับความสำคัญ

---

# ส่วนที่ 4 — Constructor

```java
Order(String id, int orderTime, String type, int prepTime, int priority)
```

Constructor ใช้ตอนสร้าง Order

ตัวอย่าง:

```java
new Order("O1", 1, "Food", 15, 2)
```

หมายถึง:

```text
ID = O1
Order Time = 1
Food Type = Food
Preparation Time = 15
Priority = 2 หรือ Normal
```

---

# ส่วนที่ 5 — priorityName()

```java
String priorityName()
```

ใช้แปลงเลข Priority เป็นข้อความ

```text
1 → Express
2 → Normal
3 → Large
```

ทำให้เวลาแสดงผลอ่านง่ายกว่าแสดงเลข

---

# ส่วนที่ 6 — toString()

```java
public String toString()
```

ใช้กำหนดรูปแบบตอน Print Object

เช่น:

```text
O1(Normal,15m)
```

มีประโยชน์ตอน Queue Trace และ Test Case

---

# ส่วนที่ 7 — createScenario()

```java
static List<Order> createScenario()
```

ใช้สร้างข้อมูลจากโจทย์

ประกอบด้วย:

```text
O1 Normal 15
O2 Express 5
O3 Normal 10
O4 Express 8
O5 Large 25
```

ข้อดีคือข้อมูล Scenario อยู่จุดเดียว แก้ไขง่าย

---

# ส่วนที่ 8 — algorithmA()

```java
static void algorithmA(List<Order> orders)
```

เป็น Algorithm A แบบ FIFO

สร้าง Queue:

```java
Queue<Order> queue = new ArrayDeque<>();
```

จากนั้นใส่ข้อมูลทั้งหมด:

```java
queue.addAll(orders);
```

ArrayDeque เหมาะกับ FIFO เพราะ:

- เพิ่มท้ายเร็ว
- นำหน้าคิวออกเร็ว
- enqueue/dequeue โดยทั่วไปเป็น O(1)

จากนั้นส่ง Queue ไปที่:

```java
processQueue(queue);
```

---

# ส่วนที่ 9 — algorithmB()

```java
static void algorithmB(List<Order> orders)
```

ใช้:

```java
PriorityQueue<Order>
```

มี Comparator:

```java
Comparator.comparingInt((Order o) -> o.priority)
          .thenComparingInt(o -> o.orderTime)
```

ความหมาย:

## ขั้นแรก
ดูค่า:

```java
o.priority
```

ค่าต่ำกว่าออกก่อน

ดังนั้น:

```text
1 Express → ก่อน
2 Normal
3 Large → หลัง
```

## ถ้า Priority เท่ากัน

ใช้:

```java
thenComparingInt(o -> o.orderTime)
```

Order ที่เข้ามาก่อนจะออกก่อน

ตัวอย่าง:

```text
O1 Express เวลา 1
O2 Express เวลา 2
```

O1 จะถูกเลือกก่อน O2

---

# ส่วนที่ 10 — processQueue()

นี่เป็น Method สำคัญที่ช่วยลดโค้ดซ้ำ

ทั้ง FIFO และ Priority Queue มีหลักการคำนวณ Waiting Time เหมือนกัน

จึงไม่ต้องเขียนโค้ดคำนวณ 2 รอบ

ตัวแปร:

```java
int currentTime = 0;
int totalWaiting = 0;
int maxWaiting = 0;
```

### currentTime

เวลาที่ผ่านไปแล้ว

### totalWaiting

ผลรวม Waiting Time

### maxWaiting

Waiting Time ที่มากที่สุด

---

# ส่วนที่ 11 — while Queue ไม่ว่าง

```java
while (!queue.isEmpty())
```

หมายถึง:

ทำงานต่อไปเรื่อย ๆ จนไม่มี Order

นำ Order ออก:

```java
Order o = queue.poll();
```

สำหรับ FIFO:

`poll()` จะเอาหน้าคิวออก

สำหรับ PriorityQueue:

`poll()` จะเอา Order Priority สูงสุดออก

นี่คือเหตุผลที่ `processQueue()` สามารถใช้ร่วมกันได้

---

# ส่วนที่ 12 — Waiting Time

```java
int waiting = currentTime;
```

Order ปัจจุบันต้องรอเท่ากับเวลาของ Order ก่อนหน้าทั้งหมด

จากนั้น:

```java
currentTime += o.prepTime;
```

เพิ่มเวลาเตรียม Order ปัจจุบัน

---

# ส่วนที่ 13 — Average Waiting Time

```java
double average = (double) totalWaiting / count;
```

แปลงเป็น `double` เพื่อให้ได้ทศนิยม

เช่น:

```text
103 / 5
= 20.60
```

ไม่ใช่ 20

---

# ส่วนที่ 14 — Maximum Waiting Time

```java
maxWaiting = Math.max(maxWaiting, waiting);
```

เปรียบเทียบว่าค่าใหม่มากกว่าค่าเดิมหรือไม่

---

# ส่วนที่ 15 — queueTrace()

Method นี้ใช้แสดง Queue อย่างน้อย 10 Operations

มี Method ย่อย 3 ตัว:

```java
traceEnqueue()
traceDequeue()
tracePeek()
```

แยกออกมาเพื่อให้โค้ดใน `queueTrace()` อ่านง่าย

---

# ส่วนที่ 16 — ENQUEUE

```java
q.offer(o);
```

คือการเพิ่มข้อมูลเข้า Queue

FIFO จะเพิ่มท้าย Queue

---

# ส่วนที่ 17 — DEQUEUE

```java
q.poll();
```

คือการนำสมาชิกด้านหน้าออก

ถ้า Queue ว่างจะได้:

```text
null
```

จึงปลอดภัยกว่า `remove()` ที่อาจเกิด Exception

---

# ส่วนที่ 18 — PEEK

```java
q.peek();
```

ใช้ดูสมาชิกตัวแรกโดยไม่ลบออก

---

# ส่วนที่ 19 — runTests()

รวม Test Cases ทั้ง 6 ข้อไว้ใน Method เดียว

ทำให้ไม่ต้องสร้าง `TestCases.java` เพิ่ม

---

# Test 1 — Normal Case

```java
q1.offer(...)
```

ใส่หลาย Order เพื่อทดสอบ Queue ปกติ

---

# Test 2 — Empty Queue

```java
q2.poll()
q2.peek()
```

เมื่อ Queue ว่างควรได้ `null`

โปรแกรมไม่ควร Crash

---

# Test 3 — Single Item

ใส่ Order หนึ่งรายการแล้วนำออก

Expected:

```text
Queue ว่างหลัง poll
```

---

# Test 4 — Large Queue

Loop:

```java
for (int i = 1; i <= 10000; i++)
```

ใช้ทดสอบว่ารองรับข้อมูลจำนวนมาก

---

# Test 5 — Same Priority

สร้าง PriorityQueue ที่ Order ทุกตัวเป็น Express

Comparator จะไปดู:

```java
orderTime
```

ดังนั้นลำดับควรเป็น:

```text
O1 O2 O3
```

---

# Test 6 — Cancel Case

ใช้:

```java
q6.removeIf(o -> o.id.equals("O2"));
```

แปลว่า:

ค้นหา Order ที่ `id` เป็น O2 แล้วลบ

ข้อสังเกต:

การค้นหาและลบลักษณะนี้เป็น:

```text
O(n)
```

เพราะอาจต้องตรวจสมาชิกหลายตัว

---

# ส่วนที่ 20 — runExperiment()

กำหนด:

```java
int[] sizes = {100, 1000, 10000, 50000};
```

ตรงตามข้อกำหนดงาน

รัน Warm-up:

```java
3 รอบ
```

แล้ววัดจริง:

```java
5 รอบ
```

---

# ส่วนที่ 21 — Random Seed

```java
long seed = 12345L;
```

Seed คงที่ช่วยให้ข้อมูลสุ่มแต่ละ Algorithm มีรูปแบบเดียวกัน

เหมาะสำหรับการเปรียบเทียบ

---

# ส่วนที่ 22 — System.nanoTime()

เริ่มจับเวลา:

```java
long start = System.nanoTime();
```

เมื่อทำงานเสร็จ:

```java
System.nanoTime() - start
```

จะได้เวลาเป็นหน่วย nanoseconds

---

# ส่วนที่ 23 — measureFIFO()

สร้างข้อมูลจำนวน n แล้ว:

1. enqueue ทั้งหมด
2. dequeue ทั้งหมด

เพื่อดูเวลารวมของ FIFO

ทฤษฎีโดยประมาณ:

```text
O(n)
```

---

# ส่วนที่ 24 — measurePriority()

ทำเหมือน FIFO แต่ใช้:

```java
PriorityQueue
```

ทุกการเพิ่มและนำออกมีค่าโดยทั่วไป:

```text
O(log n)
```

ดังนั้นรวม:

```text
O(n log n)
```

---

# ส่วนที่ 25 — main()

นี่คือจุดเริ่มต้นของโปรแกรม

```java
public static void main(String[] args)
```

ภายในเรียกตามลำดับ:

```java
algorithmA(orders);
algorithmB(orders);
queueTrace();
runTests();
runExperiment();
```

ดังนั้นใช้คำสั่งเดียว:

```bash
java Main
```

ก็เห็นผลทั้งหมด

---

# เหตุผลที่เวอร์ชันนี้ใช้โค้ดน้อยลง

เวอร์ชันเดิมแยกเป็นหลาย Class เช่น

```text
Order.java
Priority.java
AlgorithmA_FIFO.java
AlgorithmB_Priority.java
QueueSimulation.java
TestCases.java
Experiment.java
```

เวอร์ชันใหม่รวมไว้ใน:

```text
Main.java
```

แต่ยังแยกเป็น Method ชัดเจน

ข้อดี:

- Compile ง่าย
- Run ง่าย
- ส่งงานง่าย
- อธิบายหน้าห้องง่าย
- หาจุดแก้ไขง่าย
- ยังเห็น Algorithm A และ B ชัดเจน

---

# โครงสร้างโดยย่อ

```text
Main
│
├── Order
├── createScenario()
├── algorithmA()
├── algorithmB()
├── processQueue()
├── queueTrace()
├── runTests()
├── runExperiment()
├── measureFIFO()
├── measurePriority()
└── main()
```

นี่คือโครงสร้างหลักทั้งหมดของโปรแกรม


---

# ปรับรูปแบบ Queue Trace ให้อ่านง่าย

ในเวอร์ชันนี้ Queue Trace ไม่แสดงข้อมูล Order ทั้งหมด เช่น
`O1(Normal,15m)` เพราะทำให้บรรทัดยาวและดูยาก

โปรแกรมจะแสดงเฉพาะ Order ID:

```text
[O1, O2, O3]
```

และจัดข้อมูลเป็นตาราง:

```text
Step | Operation       | Queue Before       | Queue After        | Output
-----+-----------------+--------------------+--------------------+-------
1    | ENQUEUE O1      | []                 | [O1]               | -
2    | ENQUEUE O2      | [O1]               | [O1, O2]           | -
3    | ENQUEUE O3      | [O1, O2]           | [O1, O2, O3]       | -
4    | PEEK            | [O1, O2, O3]       | [O1, O2, O3]       | O1
5    | DEQUEUE         | [O1, O2, O3]       | [O2, O3]           | O1
```

## ความหมายของแต่ละช่อง

- **Step** = ลำดับการทำงาน
- **Operation** = คำสั่งที่ทำ
- **Queue Before** = Queue ก่อนทำคำสั่ง
- **Queue After** = Queue หลังทำคำสั่ง
- **Output** = สิ่งที่ได้จาก PEEK หรือ DEQUEUE

### ตัวอย่าง

```text
5 | DEQUEUE | [O1, O2, O3] | [O2, O3] | O1
```

แปลว่า:

1. ก่อนทำมี O1, O2, O3
2. ใช้ DEQUEUE
3. O1 ถูกนำออก
4. เหลือ O2, O3
5. Output คือ O1

รูปแบบนี้เหมาะกับการนำไปใส่ **รายงานหรือ Slide** เพราะเห็นการเปลี่ยนแปลงของ Queue ได้ทันที
