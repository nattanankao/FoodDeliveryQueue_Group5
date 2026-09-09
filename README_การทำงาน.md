# README 1 — อธิบายการทำงานของโปรแกรม

# Food Delivery Order Queue

โปรแกรมนี้ทำขึ้นตามโจทย์ระบบจัดคิวคำสั่งซื้ออาหาร โดยเปรียบเทียบ Queue 2 แบบ

- Algorithm A = FIFO Queue
- Algorithm B = Priority Queue

โปรเจกต์นี้ลดจำนวนไฟล์ให้เหลือเพียง `Main.java` ไฟล์เดียว เพื่อให้เปิด อ่าน Compile และ Run ได้ง่าย

---

## 1. Scenario

ข้อมูลจากโจทย์:

| Order | Priority | Preparation Time |
|---|---|---:|
| O1 | Normal | 15 นาที |
| O2 | Express | 5 นาที |
| O3 | Normal | 10 นาที |
| O4 | Express | 8 นาที |
| O5 | Large | 25 นาที |

Priority กำหนดเป็น:

- 1 = Express
- 2 = Normal
- 3 = Large

---

# 2. วิธี Compile และ Run

เปิด Command Prompt หรือ Terminal ในโฟลเดอร์โปรเจกต์

Compile:

```bash
javac Main.java
```

Run:

```bash
java Main
```

รันเพียงไฟล์ `Main.java` ไฟล์เดียว โปรแกรมจะทำทุกส่วนให้ครบ

---

# 3. ลำดับการทำงานเมื่อรัน Main

โปรแกรมจะทำตามลำดับดังนี้

1. สร้างข้อมูล Order ตาม Scenario
2. รัน Algorithm A: FIFO
3. รัน Algorithm B: Priority Queue
4. แสดง Queue Trace 10 Operations
5. ทดสอบ Test Cases 6 กรณี
6. รัน Experiment
7. แสดงผลสรุป

---

# 4. Algorithm A — FIFO Queue

FIFO ย่อมาจาก:

**First In, First Out**

หมายถึง:

> เข้าก่อน → ทำก่อน

โปรแกรมใช้:

```java
Queue<Order> queue = new ArrayDeque<>();
```

ลำดับการทำ:

```text
O1 → O2 → O3 → O4 → O5
```

Waiting Time:

| Order | Waiting Time |
|---|---:|
| O1 | 0 |
| O2 | 15 |
| O3 | 20 |
| O4 | 30 |
| O5 | 38 |

Average Waiting Time:

```text
(0 + 15 + 20 + 30 + 38) / 5
= 20.60 นาที
```

Maximum Waiting Time:

```text
38 นาที
```

---

# 5. Algorithm B — Priority Queue

Priority Queue จะไม่ทำตามลำดับเข้าคิวอย่างเดียว แต่พิจารณา Priority

กำหนด:

```text
Express > Normal > Large
```

จึงได้ลำดับ:

```text
O2 → O4 → O1 → O3 → O5
```

Waiting Time:

| Order | Waiting Time |
|---|---:|
| O2 | 0 |
| O4 | 5 |
| O1 | 13 |
| O3 | 28 |
| O5 | 38 |

Average:

```text
(0 + 5 + 13 + 28 + 38) / 5
= 16.80 นาที
```

Maximum:

```text
38 นาที
```

---

# 6. Queue Trace

โปรแกรมแสดงอย่างน้อย 10 Operations ได้แก่

- ENQUEUE
- DEQUEUE
- PEEK

ตัวอย่าง:

```text
ENQUEUE O1
ENQUEUE O2
ENQUEUE O3
PEEK
DEQUEUE
ENQUEUE O4
ENQUEUE O5
PEEK
DEQUEUE
DEQUEUE
```

จุดประสงค์คือแสดงการเปลี่ยนแปลงของ Queue แบบ Step-by-step

---

# 7. Test Cases

โปรแกรมมีครบ 6 กรณีตามโจทย์

### Test 1 — Normal Case
ทดสอบ Queue ปกติที่มีหลาย Order

### Test 2 — Empty Queue
ทดสอบ Queue ว่าง

### Test 3 — Single Item
ทดสอบ Queue ที่มีเพียง 1 Order

### Test 4 — Large Queue
สร้าง 10,000 Orders

### Test 5 — Special / Edge Case
Priority ของทุก Order เท่ากัน

ในกรณีนี้ใช้ `orderTime` ตัดสินว่า Order ไหนมาก่อน

### Test 6 — Cancel Case
ยกเลิก Order ที่อยู่กลาง Queue

ใช้:

```java
removeIf(...)
```

---

# 8. Experiment

ทดลองด้วยขนาด:

```text
100
1,000
10,000
50,000
```

มี:

- Warm-up 3 รอบ
- วัดจริง 5 รอบ
- Seed = 12345
- ใช้ `System.nanoTime()`

วัดทั้ง:

- Algorithm A
- Algorithm B

เวลาที่ได้จะแตกต่างตามเครื่องที่ใช้รัน

---

# 9. Time Complexity

## FIFO — ArrayDeque

| Operation | Complexity |
|---|---:|
| enqueue | O(1) |
| dequeue | O(1) |
| peek | O(1) |
| search / cancel | O(n) |
| display | O(n) |

ประมวลผล n Order:

```text
O(n)
```

## Priority Queue

| Operation | Complexity |
|---|---:|
| offer | O(log n) |
| poll | O(log n) |
| peek | O(1) |
| search / cancel | O(n) |
| display | O(n) |

ประมวลผลทั้งหมด:

```text
O(n log n)
```

---

# 10. Space Complexity

ทั้งสองแบบใช้:

```text
O(n)
```

เพราะต้องเก็บ Order จำนวน n รายการ

---

# 11. Fairness

## FIFO

Fairness สูงกว่า เพราะทำตามลำดับที่เข้ามา

## Priority Queue

Express สามารถแซง Normal และ Large ได้

จึงทำให้ Express เร็วกว่า แต่ Fairness ต่ำกว่า

---

# 12. ปัญหา Starvation

ถ้ามี Express เข้ามาเรื่อย ๆ เช่น

```text
Express
Express
Express
Express
Express
...
```

Normal หรือ Large อาจรอนานมาก

เรียกว่า:

**Starvation**

วิธีแก้ที่สามารถเสนอได้:

- Aging
- จำกัดจำนวน Express ที่แซงต่อเนื่อง
- หลังทำ Express หลายรายการ ให้ทำ Normal/Large บ้าง

---

# 13. สรุป

| ประเด็น | FIFO | Priority |
|---|---|---|
| Average Waiting | 20.60 นาที | 16.80 นาที |
| Maximum Waiting | 38 นาที | 38 นาที |
| Fairness | สูง | ต่ำกว่า |
| Express | รอตามคิว | ได้ทำก่อน |
| Complexity | O(n) | O(n log n) |

ใน Scenario นี้ Priority Queue ลด Average Waiting Time ได้ แต่ต้องระวัง Starvation


---

# รูปแบบ Queue Trace ที่ใช้ในโปรแกรม

เพื่อให้อ่านง่าย โปรแกรมจะแสดง Queue เป็นเฉพาะ Order ID เช่น

```text
[O1, O2, O3]
```

แทนการแสดงรายละเอียดทั้งหมดของ Order

รูปแบบตาราง:

```text
Step | Operation       | Queue Before       | Queue After        | Output
```

มีทั้งหมด 10 Operations และสามารถนำผลลัพธ์นี้ไปใช้ในหัวข้อ Queue Trace ของรายงานได้โดยตรง
