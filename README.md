# Bài 10 – The Broken Pipeline

## Các lỗi đã tìm được trong mã đề cho
1. **Thiếu `actions/checkout`** trong `.github/workflows/ci.yml`: GitHub runner không có mã nguồn/`pom.xml` trước khi chạy Maven. Sửa: thêm `actions/checkout@v7` vào đầu steps.
2. **Logback 9.9.9 không tồn tại** trong `pom.xml`: dependency resolution không thành công. Sửa: thay thành `logback-classic:1.4.11`.
3. **Maven Surefire 2.12.4 quá cũ**: không chạy đúng JUnit Jupiter 5.9.2; có thể tạo kết quả build thành công dù thực tế 0 test. Sửa: `maven-surefire-plugin:3.5.3` và kiểm tra 3 test thực sự chạy.
4. **Lỗi do tự tạo**: sửa `assertEquals(45000.0, calc.calculate(5, "EXPRESS"))` thành `assertEquals(46000.0, ...)`. Kết quả tính thật vẫn là 45000, khiến JUnit fail. Khôi phục 45000 thì xanh.

## Các file trong project
- `pom.xml`: bản Maven đã sửa.
- `.github/workflows/ci.yml`: bản GitHub Actions đã sửa, thêm xác minh số test.
- `src/main/java/com/lab/ShippingCalculator.java`: giữ logic từ đề.
- `src/test/java/com/lab/ShippingCalculatorTest.java`: giữ 3 test từ đề.
- `src/main/java/com/lab/Main.java`: entry point phục vụ `run.sh` trên máy chấm.
- `demo/legacy/`: hai file cấu hình lỗi gốc để so sánh.
- `demo/scenarios/`: 4 lỗi cố ý được tách riêng, chỉ dùng để thử pipeline.
- `docs/PIPELINE_EVIDENCE.md`: mẫu ghi link GitHub Actions và log thật.

## Chạy trong IntelliJ
- Mở project `Bai10` dạng Maven (JDK 17 hoặc cao hơn).
- Maven goals: `clean test`, `clean package`.
- Chạy `bash run.sh` (không cần Maven hay thư viện ngoài cho code production).

## GitHub
1. Tạo repository riêng tên `shipping-broken-pipeline` (tạo trống).
2. Mở Git Bash tại **Bai10**, chạy `git init`, `git branch -M main`, `git add .`, `git commit -m "Fix shipping CI baseline"`.
3. `git remote add origin <URL-REPO-THAT>`; `git push -u origin main`.
4. GitHub → Actions: kiểm tra baseline xanh, số test là 3.
5. Làm demo theo `demo/SCENARIOS.md` từng lỗi một để có bằng chứng đỏ → xanh.
6. Dán link các lần chạy và log thật vào `docs/PIPELINE_EVIDENCE.md`.

## Ghi chú
Không có log GitHub thật trong ZIP do chưa chạy workflow trên repository của bạn. Những đoạn error trong tài liệu chỉ là ví dụ dự kiến, không được ghi là bằng chứng đã thu thập.
