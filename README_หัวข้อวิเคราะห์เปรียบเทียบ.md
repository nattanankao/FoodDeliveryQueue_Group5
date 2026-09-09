# README — วิเคราะห์เปรียบเทียบ Algorithm A และ Algorithm B

หัวข้อนี้ใช้สำหรับส่วน **วิเคราะห์เปรียบเทียบ** ของงาน Food Delivery Order Queue

## 1. Average Waiting Time

Average Waiting Time คือค่าเฉลี่ยเวลาที่ Order ทุกตัวต้องรอก่อนเริ่มเตรียมอาหาร

### Algorithm A: FIFO Queue

FIFO ใช้หลักการ:

> เข้ามาก่อน → ได้รับการเตรียมก่อน

Scenario:

```text
O1 Normal  15 นาที
O2 Express  5 นาที
O3 Normal  10 นาที
O4 Express  8 นาที
O5 Large   25 นาที
```

ลำดับการทำอาหาร:

```text
O1 → O2 → O3 → O4 → O5
```

Waiting Time:

```text
O1 = 0
O2 = 15
O3 = 20
O4 = 30
O5 = 38
```

Average Waiting Time:

```text
(0 + 15 + 20 + 30 + 38) / 5
= 20.60 นาที
```

ดังนั้น:

**FIFO Average Waiting Time = 20.60 นาที**

---

### Algorithm B: Priority Queue

Priority Queue ให้ความสำคัญกับ Order ตาม Priority:

```text
Express > Normal > Large
```

ลำดับการทำอาหาร:

```text
O2 → O4 → O1 → O3 → O5
```

Waiting Time:

```text
O2 = 0
O4 = 5
O1 = 13
O3 = 28
O5 = 38
```

Average Waiting Time:

```text
(0 + 5 + 13 + 28 + 38) / 5
= 16.80 นาที
```

ดังนั้น:

**Priority Queue Average Waiting Time = 16.80 นาที**

---

## 2. Maximum Waiting Time

Maximum Waiting Time คือเวลารอที่มากที่สุดของ Order ใด ๆ

### FIFO

Order ที่รอนานที่สุดคือ O5

```text
Maximum Waiting Time = 38 นาที
```

### Priority Queue

Order ที่รอนานที่สุดคือ O5

```text
Maximum Waiting Time = 38 นาที
```

ดังนั้นใน Scenario นี้:

| Algorithm | Maximum Waiting Time |
|---|---:|
| FIFO | 38 นาที |
| Priority Queue | 38 นาที |

แม้ Priority Queue จะทำให้ Express เร็วขึ้น แต่ไม่ได้ลด Maximum Waiting Time ใน Scenario นี้

---

## 3. Fairness

Fairness หมายถึงความยุติธรรมในการให้บริการ Order

### FIFO

FIFO มี Fairness สูงกว่า เพราะทุก Order ได้รับบริการตามลำดับที่เข้ามา

ตัวอย่าง:

```text
O1 → O2 → O3 → O4 → O5
```

ไม่มี Order ไหนสามารถแซง Order ที่มาก่อนได้

**ข้อดี:** ยุติธรรมและเข้าใจง่าย

**ข้อเสีย:** Express อาจต้องรอ Normal หรือ Large ที่มาก่อน

---

### Priority Queue

Priority Queue มี Fairness ต่ำกว่า FIFO เพราะ Order ที่ Priority สูงสามารถแซง Order ที่ Priority ต่ำได้

ตัวอย่าง:

```text
O1 Normal เข้ามาก่อน
O2 Express เข้ามาทีหลัง

ผล:
O2 ได้ทำก่อน O1
```

**ข้อดี:** เหมาะกับงานเร่งด่วน เช่น Express Order

**ข้อเสีย:** Normal หรือ Large อาจต้องรอนาน

---

# 4. สรุปเปรียบเทียบ

| ประเด็น | Algorithm A: FIFO | Algorithm B: Priority Queue |
|---|---|---|
| หลักการ | เข้าก่อนทำก่อน | Priority สูงทำก่อน |
| Average Waiting Time | 20.60 นาที | 16.80 นาที |
| Maximum Waiting Time | 38 นาที | 38 นาที |
| Fairness | สูง | ต่ำกว่า FIFO |
| Express Order | รอตามคิว | ได้รับบริการก่อน |
| เหมาะกับ | ระบบที่ต้องการความยุติธรรม | ระบบที่มีงานเร่งด่วน |

## ข้อสรุป

จาก Scenario นี้ Priority Queue มี Average Waiting Time ต่ำกว่า FIFO:

```text
FIFO            = 20.60 นาที
Priority Queue  = 16.80 นาที
```

ดังนั้น Priority Queue เหมาะกับสถานการณ์ที่ต้องการให้ Express Order ได้รับบริการเร็ว

แต่ Priority Queue แลกมาด้วย Fairness ที่ลดลง เพราะ Normal และ Large สามารถถูกเลื่อนออกไปได้

---

# 5. ปัญหาจากการให้ Express Order แซงคิวอย่างต่อเนื่อง

คำถาม:

> การให้ Express Order แซงคิวอย่างต่อเนื่องอาจสร้างปัญหาอะไร?

คำตอบคือ:

## Starvation

Starvation คือกรณีที่ Order ที่มี Priority ต่ำต้องรอเป็นเวลานาน เพราะมี Order Priority สูงเข้ามาและถูกเลือกก่อนอยู่ตลอด

ตัวอย่าง:

```text
Normal O1 รออยู่

มี Express เข้ามา
→ Express ทำก่อน

มี Express ใหม่เข้ามา
→ Express ใหม่ทำก่อน

มี Express ใหม่อีก
→ Express ใหม่ทำก่อน

...
```

ทำให้:

```text
Normal / Large
      ↓
รอนานมาก
```

และอาจทำให้ผู้ใช้ที่สั่ง Normal หรือ Large ไม่ได้รับบริการในเวลาที่เหมาะสม

---

# 6. วิธีลดปัญหา Starvation

### วิธีที่ 1: Aging

เมื่อ Order รอนานขึ้น ให้เพิ่มความสำคัญของ Order นั้น

ตัวอย่าง:

```text
Normal รอนาน
      ↓
Priority เพิ่ม
      ↓
มีโอกาสถูกเลือกเร็วขึ้น
```

### วิธีที่ 2: จำกัด Express

กำหนดว่าไม่สามารถทำ Express ติดต่อกันได้ไม่จำกัด

ตัวอย่าง:

```text
Express
Express
Normal
Express
Express
Large
```

วิธีนี้ช่วยให้ Normal และ Large ยังมีโอกาสได้รับบริการ

### วิธีที่ 3: กำหนดเวลารอสูงสุด

ถ้า Order ใดรอนานเกินกำหนด ให้เพิ่ม Priority ของ Order นั้น

---

# 7. สรุป

สามารถใช้ข้อความนี้ได้:

> จากการทดลองพบว่า Priority Queue ให้ Average Waiting Time ต่ำกว่า FIFO โดย FIFO มีค่า 20.60 นาที ขณะที่ Priority Queue มีค่า 16.80 นาที เนื่องจาก Express Order ได้รับการจัดการก่อน อย่างไรก็ตาม Priority Queue มี Fairness ต่ำกว่า เพราะ Normal และ Large Order อาจถูกเลื่อนออกไปเมื่อมี Express Order เข้ามาอย่างต่อเนื่อง ปัญหานี้เรียกว่า Starvation ซึ่งสามารถลดปัญหาได้ด้วยวิธี Aging หรือการจำกัดจำนวน Express ที่สามารถแซงคิวติดต่อกัน
