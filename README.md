# 🍔 Food Delivery Order Queue

> **Queue Algorithm Design & Analysis with Java**

โปรเจกต์นี้เป็นการจำลองระบบจัดคิว Order ของร้านอาหารจาก Food Delivery Platform
โดยเปรียบเทียบการจัดคิว 2 รูปแบบ เพื่อดูความแตกต่างด้าน **Waiting Time, Fairness และประสิทธิภาพ**

---

## 📌 Algorithms

| Algorithm | หลักการ | เหมาะกับ |
|---|---|---|
| **A — FIFO Queue** | เข้าก่อน → ทำก่อน | ต้องการความยุติธรรม |
| **B — Priority Queue** | Priority สูง → ทำก่อน | มี Order เร่งด่วน |

**Priority:** `Express > Normal > Large`

---

## 🍱 Scenario

```text
O1  Normal   15 min
O2  Express   5 min
O3  Normal   10 min
O4  Express   8 min
O5  Large    25 min
```

ผลลำดับตัวอย่าง:

```text
FIFO     : O1 → O2 → O3 → O4 → O5
Priority : O2 → O4 → O1 → O3 → O5
```

---

## 📊 ผลเปรียบเทียบ

| รายการ | FIFO | Priority Queue |
|---|---:|---:|
| Average Waiting Time | **20.60 min** | **16.80 min** |
| Maximum Waiting Time | **38 min** | **38 min** |
| Fairness | ⭐⭐⭐⭐ | ⭐⭐⭐ |

> Priority Queue ช่วยให้ Express Order ได้รับบริการเร็วขึ้น
> แต่ถ้ามี Express เข้ามาต่อเนื่อง อาจทำให้ Normal/Large รอนาน (**Starvation**)

---

## ▶️ วิธีรัน

โปรเจกต์ใช้ `Main.java` เป็นไฟล์หลัก

```bash
javac Main.java
java Main
```

ภายในโปรแกรมมีทั้ง

`Algorithm A` · `Algorithm B` · `Queue Trace` · `Test Cases` · `Experiment`

---

## 🎯 จุดประสงค์

ศึกษาการทำงานของ Queue และเปรียบเทียบว่า Algorithm แบบใดเหมาะกับระบบ Food Delivery มากกว่า โดยพิจารณาทั้ง **ความเร็วและความยุติธรรม**

---

### 👥 Group 5 — Food Delivery Order Queue
