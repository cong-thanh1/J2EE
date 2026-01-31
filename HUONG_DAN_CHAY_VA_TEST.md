# Hướng dẫn chạy ứng dụng và test API qua Postman

## Bước 1: Chạy ứng dụng Spring Boot

### Cách 1: Chạy từ IDE (IntelliJ IDEA / Eclipse)
1. Mở file `Lab02Application.java`
2. Click chuột phải vào file → Chọn **Run 'Lab02Application'**
   - Hoặc nhấn phím tắt: `Shift + F10` (IntelliJ) hoặc `Ctrl + F11` (Eclipse)

### Cách 2: Chạy từ Maven
Mở terminal/command prompt tại thư mục dự án và chạy:
```bash
mvn spring-boot:run
```

### Cách 3: Build và chạy JAR
```bash
mvn clean package
java -jar target/lab02-0.0.1-SNAPSHOT.jar
```

### Kiểm tra ứng dụng đã chạy
Khi ứng dụng chạy thành công, bạn sẽ thấy log:
```
Started Lab02Application in X.XXX seconds
```

Mặc định ứng dụng chạy tại: **http://localhost:8080**

---

## Bước 2: Test API qua Postman

### Cài đặt Postman
- Tải Postman tại: https://www.postman.com/downloads/
- Hoặc sử dụng Postman Web: https://web.postman.co/

---

## Các API Endpoints

Base URL: `http://localhost:8080/api/books`

### 1. Thêm sách mới (POST)

**Request:**
- **Method:** `POST`
- **URL:** `http://localhost:8080/api/books`
- **Headers:**
  - `Content-Type: application/json`
- **Body (raw JSON):**
```json
{
  "title": "Java Programming",
  "author": "John Doe"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "title": "Java Programming",
  "author": "John Doe"
}
```

**Cách test trong Postman:**
1. Chọn method **POST**
2. Nhập URL: `http://localhost:8080/api/books`
3. Vào tab **Headers**, thêm:
   - Key: `Content-Type`
   - Value: `application/json`
4. Vào tab **Body** → Chọn **raw** → Chọn **JSON**
5. Dán JSON body ở trên
6. Click **Send**

---

### 2. Lấy tất cả sách (GET)

**Request:**
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/books`

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "title": "Java Programming",
    "author": "John Doe"
  },
  {
    "id": 2,
    "title": "Spring Boot Guide",
    "author": "Jane Smith"
  }
]
```

**Cách test trong Postman:**
1. Chọn method **GET**
2. Nhập URL: `http://localhost:8080/api/books`
3. Click **Send**

---

### 3. Lấy sách theo ID (GET)

**Request:**
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/books/1`
  - (Thay `1` bằng ID sách bạn muốn lấy)

**Response (200 OK):**
```json
{
  "id": 1,
  "title": "Java Programming",
  "author": "John Doe"
}
```

**Response nếu không tìm thấy (404 Not Found):**
```
(Empty body)
```

**Cách test trong Postman:**
1. Chọn method **GET**
2. Nhập URL: `http://localhost:8080/api/books/1`
3. Click **Send**

---

### 4. Cập nhật sách (PUT)

**Request:**
- **Method:** `PUT`
- **URL:** `http://localhost:8080/api/books/1`
  - (Thay `1` bằng ID sách bạn muốn cập nhật)
- **Headers:**
  - `Content-Type: application/json`
- **Body (raw JSON):**
```json
{
  "title": "Advanced Java Programming",
  "author": "John Doe Updated"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "title": "Advanced Java Programming",
  "author": "John Doe Updated"
}
```

**Response nếu không tìm thấy (404 Not Found):**
```
(Empty body)
```

**Cách test trong Postman:**
1. Chọn method **PUT**
2. Nhập URL: `http://localhost:8080/api/books/1`
3. Vào tab **Headers**, thêm:
   - Key: `Content-Type`
   - Value: `application/json`
4. Vào tab **Body** → Chọn **raw** → Chọn **JSON**
5. Dán JSON body ở trên
6. Click **Send**

---

### 5. Xóa sách (DELETE)

**Request:**
- **Method:** `DELETE`
- **URL:** `http://localhost:8080/api/books/1`
  - (Thay `1` bằng ID sách bạn muốn xóa)

**Response (204 No Content):**
```
(Empty body)
```

**Response nếu không tìm thấy (404 Not Found):**
```
(Empty body)
```

**Cách test trong Postman:**
1. Chọn method **DELETE**
2. Nhập URL: `http://localhost:8080/api/books/1`
3. Click **Send**

---

## Demo Flow hoàn chỉnh

### Bước 1: Thêm sách
```
POST http://localhost:8080/api/books
Body: {"title": "Sách 1", "author": "Tác giả 1"}
→ Nhận được id = 1
```

### Bước 2: Thêm sách thứ 2
```
POST http://localhost:8080/api/books
Body: {"title": "Sách 2", "author": "Tác giả 2"}
→ Nhận được id = 2
```

### Bước 3: Lấy tất cả sách
```
GET http://localhost:8080/api/books
→ Thấy 2 cuốn sách
```

### Bước 4: Lấy sách theo ID
```
GET http://localhost:8080/api/books/1
→ Thấy thông tin sách id = 1
```

### Bước 5: Cập nhật sách
```
PUT http://localhost:8080/api/books/1
Body: {"title": "Sách 1 đã cập nhật", "author": "Tác giả 1"}
→ Sách được cập nhật
```

### Bước 6: Xóa sách
```
DELETE http://localhost:8080/api/books/2
→ Sách id = 2 bị xóa
```

### Bước 7: Kiểm tra lại
```
GET http://localhost:8080/api/books
→ Chỉ còn 1 cuốn sách (id = 1)
```

---

## Lưu ý

1. **Dữ liệu lưu trong memory:** Dữ liệu sẽ mất khi restart ứng dụng
2. **ID tự động tăng:** ID được tự động tạo bắt đầu từ 1
3. **Content-Type:** Nhớ set `Content-Type: application/json` cho POST và PUT
4. **Port mặc định:** 8080 (có thể thay đổi trong `application.properties`)

---

## Troubleshooting

### Lỗi: Connection refused
- Kiểm tra ứng dụng đã chạy chưa
- Kiểm tra port 8080 có bị chiếm không

### Lỗi: 404 Not Found
- Kiểm tra URL đúng chưa: `http://localhost:8080/api/books`
- Kiểm tra method (GET, POST, PUT, DELETE) đúng chưa

### Lỗi: 400 Bad Request
- Kiểm tra JSON body đúng format chưa
- Kiểm tra đã set `Content-Type: application/json` chưa

### Lỗi: 500 Internal Server Error
- Kiểm tra log trong console để xem lỗi chi tiết
- Đảm bảo đã build project thành công

