# Flora Mart API 🌿

![Flora Mart API Banner](assets/flora-mart-api-banner.png)

**Flora Mart API** là backend RESTful API của hệ thống **Flora Mart**, một nền tảng mua bán cây cảnh trực tuyến, được phát triển bằng **Spring Boot** và tích hợp với cơ sở dữ liệu **MySQL**. API này cung cấp các endpoint để hỗ trợ ứng dụng khách hàng (React Native) và ứng dụng quản lý (Flutter), bao gồm các chức năng như quản lý người dùng, sản phẩm, đơn hàng, và thống kê doanh thu. Dự án được thực hiện bởi **Nguyễn Hà Quỳnh Giao** và **Hoàng Công Mạnh** trong học kỳ II, năm học 2024-2025, thuộc môn Lập trình Di động Nâng cao, Trường Đại học Sư phạm Kỹ thuật TP.HCM.

## Mục lục
- [Giới thiệu](#giới-thiệu)
- [Tính năng](#tính-năng)
- [Công nghệ sử dụng](#công-nghệ-sử-dụng)
- [Yêu cầu hệ thống](#yêu-cầu-hệ-thống)
- [Hướng dẫn cài đặt](#hướng-dẫn-cài-đặt)
- [API Endpoints](#api-endpoints)
- [Cách sử dụng](#cách-sử-dụng)
- [Đóng góp](#đóng-góp)
- [Giấy phép](#giấy-phép)
- [Liên hệ](#liên-hệ)
- [Tài liệu tham khảo](#tài-liệu-tham-khảo)

## Giới thiệu
**Flora Mart API** đóng vai trò trung tâm trong hệ sinh thái Flora Mart, cung cấp các dịch vụ backend để xử lý logic nghiệp vụ, lưu trữ dữ liệu, và đảm bảo bảo mật. API được thiết kế theo kiến trúc REST, tích hợp với các ứng dụng frontend để mang lại trải nghiệm liền mạch cho người dùng cuối (khách hàng) và người bán/quản trị viên. Dự án hỗ trợ các chức năng chính như đăng nhập, quản lý sản phẩm, xử lý đơn hàng, và thống kê, góp phần thúc đẩy lối sống xanh thông qua việc mua bán và chăm sóc cây cảnh.

Hệ thống Flora Mart bao gồm:
- **Flora Mart Client** (React Native): Ứng dụng dành cho khách hàng.
- **Flora Mart Manager** (Flutter): Ứng dụng dành cho người bán/quản trị viên.
- **Flora Mart API** (Spring Boot): Backend API này.

## Tính năng
- **Quản lý người dùng** (YC01, YC02): Đăng nhập, đăng ký, xác thực OTP, và quản lý hồ sơ người dùng (khách hàng, người bán, quản trị viên).
- **Quản lý sản phẩm** (YC14): Thêm, sửa, xóa, và lấy thông tin cây cảnh (tên, giá, mô tả, hướng dẫn chăm sóc).
- **Quản lý đơn hàng** (YC07, YC13): Tạo, cập nhật trạng thái, và xem chi tiết đơn hàng.
- **Thống kê** (YC15): Cung cấp số liệu doanh thu, số đơn hàng, và sản phẩm bán chạy.
- **Bảo mật**: Sử dụng JWT để xác thực và BCrypt để mã hóa mật khẩu.
- **Hỗ trợ tìm kiếm và lọc** (YC05): Lọc cây cảnh theo giá, loại cây, hoặc mức độ chăm sóc.

## Công nghệ sử dụng
- **Backend**: Spring Boot (Java).
- **Cơ sở dữ liệu**: MySQL.
- **Công cụ**:
  - IntelliJ IDEA, Visual Studio Code.
  - Maven (quản lý phụ thuộc).
  - Git, GitHub.
  - Postman (kiểm tra API).
- **Thư viện Spring Boot**:
  - `spring-boot-starter-web`: Xây dựng API REST.
  - `spring-boot-starter-data-jpa`: Giao tiếp với MySQL.
  - `mysql-connector-java`: Kết nối cơ sở dữ liệu.
  - `jjwt`: Hỗ trợ JWT.
  - `spring-boot-starter-security`: Bảo mật.

## Yêu cầu hệ thống
- **Hệ điều hành phát triển**: Windows 11, macOS Ventura.
- **Phần mềm cần thiết**:
  - Java JDK 17+.
  - Maven 3.6+.
  - MySQL Server 8.x.
- **Cổng**: API chạy mặc định trên `http://localhost:8080`.

## Hướng dẫn cài đặt
1. **Cài đặt môi trường**:
   - Cài Java JDK 17 từ [oracle.com](https://www.oracle.com/java).
   - Cài Maven:
     ```bash
     mvn --version
     ```
   - Cài MySQL Server và tạo database:
     ```sql
     CREATE DATABASE floramart;
     ```

2. **Tải dự án**:
   ```bash
   git clone https://github.com/ZaoQuynh/adpm-api-flora-mart.git
   cd adpm-api-flora-mart
   ```

3. **Cấu hình database**:
   - Chỉnh sửa `src/main/resources/application.properties`:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/floramart
     spring.datasource.username=root
     spring.datasource.password=yourpassword
     spring.jpa.hibernate.ddl-auto=update
     server.port=8080
     ```

4. **Cài đặt phụ thuộc**:
   - Đảm bảo `pom.xml` bao gồm các thư viện cần thiết:
     ```xml
     <dependencies>
       <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-web</artifactId>
       </dependency>
       <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-data-jpa</artifactId>
       </dependency>
       <dependency>
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
       </dependency>
       <dependency>
         <groupId>io.jsonwebtoken</groupId>
         <artifactId>jjwt</artifactId>
         <version>0.9.1</version>
       </dependency>
       <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-security</artifactId>
       </dependency>
     </dependencies>
     ```
   - Cài phụ thuộc:
     ```bash
     mvn install
     ```

5. **Chạy API**:
   ```bash
   mvn spring-boot:run
   ```

**Lưu ý**:
- API sẽ chạy trên `http://localhost:8080`.
- Đảm bảo MySQL Server đang hoạt động trước khi khởi động.

## API Endpoints
Dưới đây là các endpoint chính của API:
| **Phương thức** | **Endpoint**                   | **Mô tả**                              | **Quyền truy cập**         |
|------------------|--------------------------------|----------------------------------------|----------------------------|
| POST            | `/api/auth/login`             | Đăng nhập, trả về JWT token            | Công khai                  |
| POST            | `/api/auth/register`          | Đăng ký tài khoản                      | Công khai                  |
| GET             | `/api/products`               | Lấy danh sách cây cảnh (hỗ trợ lọc)    | Công khai                  |
| POST            | `/api/products`               | Thêm cây cảnh                          | SELLER, MANAGER            |
| PUT             | `/api/products/{id}`          | Sửa cây cảnh                           | SELLER, MANAGER            |
| DELETE          | `/api/products/{id}`          | Xóa cây cảnh                           | SELLER, MANAGER            |
| POST            | `/api/orders`                 | Tạo đơn hàng                           | CUSTOMER                   |
| GET             | `/api/orders`                 | Lấy danh sách đơn hàng                 | CUSTOMER, SELLER, MANAGER  |
| PUT             | `/api/orders/{id}/status`     | Cập nhật trạng thái đơn hàng           | SELLER, MANAGER            |
| GET             | `/api/stats/revenue`          | Thống kê doanh thu                     | SELLER, MANAGER            |

**Chi tiết**:
- Sử dụng header `Authorization: Bearer <token>` cho các endpoint yêu cầu xác thực.
- Lọc sản phẩm: `/api/products?type=indoor&priceMax=500`.

## Cách sử dụng
1. **Khởi động API**:
   - Chạy lệnh `mvn spring-boot:run` để khởi động server.

2. **Kiểm tra API**:
   - Sử dụng Postman hoặc cURL để kiểm tra endpoint. Ví dụ:
     ```bash
     curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{"email":"seller@example.com","password":"password123"}'
     ```

3. **Tích hợp với frontend**:
   - Kết nối ứng dụng khách hàng (React Native) hoặc quản lý (Flutter) với API bằng cách cấu hình `baseUrl` thành `http://localhost:8080/api`.
   - Xem thêm: [Flora Mart Client](https://github.com/ZaoQuynh/flora-mart-client), [Flora Mart Manager](https://github.com/ZaoQuynh/flora-mart-manager).

## Đóng góp
Chúng tôi hoan nghênh mọi đóng góp để cải thiện **Flora Mart API**! Để đóng góp:
1. Fork repository này.
2. Tạo nhánh mới:
   ```bash
   git checkout -b feature/ten-chuc-nang
   ```
3. Commit thay đổi:
   ```bash
   git commit -m "Mô tả thay đổi"
   ```
4. Push lên nhánh:
   ```bash
   git push origin feature/ten-chuc-nang
   ```
5. Tạo Pull Request trên GitHub.

Vui lòng đọc [CONTRIBUTING.md](docs/CONTRIBUTING.md) để biết thêm chi tiết.

## Giấy phép
Dự án được cấp phép theo [MIT License](LICENSE.md). Xem tệp `LICENSE.md` để biết thêm thông tin.

## Liên hệ
- **Nguyễn Hà Quỳnh Giao**: [GitHub](https://github.com/ZaoQuynh) | Email: nguyenhauquynhgiao9569@gmail.com
- **Hoàng Công Mạnh**: [GitHub](https://github.com/congmanhhoang) | Email: hoangmanh6889@gmail.com

## Tài liệu tham khảo
1. Pivotal Software, Inc. (2023). *Getting started: Building a Spring Boot application*. Spring. https://spring.io/guides/gs/spring-boot
2. W3Schools. (n.d.). *MySQL introduction*. W3Schools. https://www.w3schools.com/mysql/mysql_intro.asp
