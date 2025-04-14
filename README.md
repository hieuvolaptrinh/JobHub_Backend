# JobHub Backend - Spring Boot Project

**Người viết:** [Hieuvolaptrinh](https://github.com/hieuvolaptrinh)

## Mô tả dự án:
**JobHub** là nền tảng kết nối giữa nhà tuyển dụng và ứng viên, giúp quản lý việc tuyển dụng, ứng tuyển và hồ sơ công việc.  
Backend được xây dựng bằng **Spring Boot**, sử dụng **Gradle** để quản lý phụ thuộc và xây dựng dự án.

### Các tính năng chính:
- **Quản lý người dùng:** Cho phép quản lý ứng viên và nhà tuyển dụng với hệ thống đăng ký, đăng nhập và phân quyền người dùng.
- **Quản lý công việc:** Đăng tuyển, ứng tuyển công việc, và quản lý lịch sử ứng tuyển.
- **Phân quyền người dùng:** Admin, nhà tuyển dụng và ứng viên có quyền truy cập và thao tác khác nhau.
- **Xác thực người dùng và bảo mật API:** Sử dụng **JWT** để bảo vệ các API và đảm bảo an toàn dữ liệu.
- **Cung cấp API RESTful:** Backend cung cấp các API cho frontend để kết nối và thao tác với dữ liệu.

## Công nghệ sử dụng:
- **Backend:** Java, Spring Boot, Spring Security, Spring Data JPA
- **Quản lý phụ thuộc:** Gradle
- **Cơ sở dữ liệu:** SQL Server
- **Xác thực:** JWT, BCrypt
- **API:** RESTful API
- **Công cụ khác:** Postman, GitHub

## Cài đặt dự án:

### Yêu cầu hệ thống:
- Java 11 hoặc cao hơn
- Gradle 6.0 hoặc cao hơn
- SQL Server (hoặc các cơ sở dữ liệu tương thích)

### Cài đặt và cấu hình dự án:

1. **Clone repository:**
   ```bash
   git clone https://github.com/hieuvolaptrinh/JobHub_BackEnd.git
