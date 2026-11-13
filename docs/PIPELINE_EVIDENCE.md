# Bài 10 – Nhật ký debug GitHub Actions

**Repository URL:** [điền link]

Chỉ chép log và thời gian từ GitHub Actions thực tế. Không dùng log dự đoán làm bằng chứng.

| Lỗi | File, dòng | Link workflow đỏ | Đoạn execution log thực tế | Nguyên nhân | Link workflow xanh sau khi sửa |
| --- | --- | --- | --- | --- | --- |
| 1 | `.github/workflows/ci.yml`: thiếu checkout trước step Maven | | | runner chưa tải code | |
| 2 | `pom.xml`: `<version>9.9.9</version>` của Logback | | | artifact không tồn tại | |
| 3 | `pom.xml`: `<version>2.12.4</version>` của Surefire | | | không chạy JUnit Jupiter 5 đúng cách | |
| 4 | `src/test/java/com/lab/ShippingCalculatorTest.java`: cố tình sửa assert thành 46000 | | | test thất bại so với 45000 thực tế | |

## Ảnh nên lưu
- `01_no_checkout_error.png` và `01_checkout_fixed.png`
- `02_dependency_error.png` và `02_dependency_fixed.png`
- `03_surefire_error.png` và `03_surefire_fixed.png`
- `04_test_error.png` và `04_test_fixed.png`

## Cách lấy log
GitHub repository → Actions → chọn run đỏ → job build → bước báo đỏ → copy 3–10 dòng liên quan. Ghi commit hash hoặc link đến run.

## Chú ý kỹ thuật
Lỗi #3 có thể là **false green** (Surefire cũ không chạy test nhưng Maven vẫn SUCCESS). Workflow đã sửa có bước `Confirm JUnit tests actually ran` để biến tình trạng không kiểm thử thành lỗi có thể quan sát.

## Giải thích trình tự
Do lỗi gốc #1 và #2 tồn tại đồng thời, nếu tái hiện nguyên trạng rồi sửa từng lỗi thì pipeline không thể xanh hoàn toàn ngay sau mỗi lần sửa. Để đáp ứng yêu cầu mỗi lỗi có cặp đỏ → xanh, hãy xuất phát từ code đã sửa trên `main`, tạo branch demo riêng cho từng lỗi; gây lỗi → push → chụp log đỏ → phục hồi → push → chụp xanh, rồi mới sang lỗi khác.
