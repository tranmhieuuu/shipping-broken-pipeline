# Bài 10 – Chạy 4 kịch bản đỏ → xanh với log thật

**QUAN TRỌNG:** Bản mặc định ở thư mục gốc `Bai10` đã sửa xong. Mỗi kịch bản dưới đây dùng **một branch riêng từ `main`** (baseline xanh). Chỉ sửa/khôi phục file tương ứng trên branch demo; không đưa lỗi trở lại `main`.

## Khởi tạo repository (một lần)
- Tạo repository GitHub trống; mở Git Bash trong `Bai10`.
- `git init`
- `git branch -M main`
- `git add .`
- `git commit -m "Working baseline shipping CI"`
- `git remote add origin <URL-REPO>` (thay URL chính xác)
- `git push -u origin main`
- Vào Actions, đợi baseline SUCCESS; kiểm tra log `Tests run: 3` và step `Confirm JUnit tests actually ran`.

## Kịch bản 1: thiếu checkout
- `git switch main && git switch -c demo-01-checkout`
- `cp demo/scenarios/01_no_checkout_ci.yml .github/workflows/ci.yml`
- `git add . && git commit -m "Demo error 1 missing checkout" && git push -u origin demo-01-checkout`
- GitHub Actions → job build → Maven: dự kiến báo không tìm thấy `pom.xml`.
- Lưu log thật + ảnh vào `docs/`.
- `git restore --source=main -- .github/workflows/ci.yml`
- `git add . && git commit -m "Fix error 1 restore checkout" && git push`
- Xác nhận workflow xanh trước khi sang lỗi 2.

## Kịch bản 2: Logback không tồn tại
- `git switch main && git switch -c demo-02-dependency`
- `cp demo/scenarios/02_bad_logback_pom.xml pom.xml`
- `git add . && git commit -m "Demo error 2 nonexistent Logback" && git push -u origin demo-02-dependency`
- Dự kiến Maven báo `Could not resolve dependencies`, `logback-classic:jar:9.9.9`.
- Lưu log thật + ảnh.
- `git restore --source=main -- pom.xml`
- `git add . && git commit -m "Fix error 2 valid Logback" && git push`
- Xác nhận workflow xanh trước khi sang lỗi 3.

## Kịch bản 3: Surefire cũ không chạy JUnit 5
- `git switch main && git switch -c demo-03-surefire`
- `cp demo/scenarios/03_old_surefire_pom.xml pom.xml`
- `git add . && git commit -m "Demo error 3 outdated Surefire" && git push -u origin demo-03-surefire`
- Quan sát kết quả bước `Build and run JUnit tests`; có thể Maven báo SUCCESS nhưng **không có report 3 test**.
- Bước `Confirm JUnit tests actually ran` sẽ phát hiện vấn đề, đánh dấu pipeline đỏ.
- Lưu log thực tế; không ghi bừa `BUILD FAILURE` tại Maven nếu Maven build vẫn xanh.
- `git restore --source=main -- pom.xml`
- `git add . && git commit -m "Fix error 3 JUnit5 compatible Surefire" && git push`
- Xác nhận workflow xanh trước khi sang lỗi 4.

## Kịch bản 4: tự tạo lỗi Assertion (không trùng 3 lỗi trên)
- `git switch main && git switch -c demo-04-test-failure`
- `cp demo/scenarios/04_wrong_assertion_ShippingCalculatorTest.java.txt src/test/java/com/lab/ShippingCalculatorTest.java`
- `git add . && git commit -m "Demo error 4 wrong assertion" && git push -u origin demo-04-test-failure`
- Dự kiến JUnit fail: `expected: <46000.0> but was: <45000.0>`.
- Lưu log thật và ảnh.
- `git restore --source=main -- src/test/java/com/lab/ShippingCalculatorTest.java`
- `git add . && git commit -m "Fix error 4 correct expected cost" && git push`
- Xác nhận workflow xanh.

## Khi xong
- `git switch main` để trả project về baseline sạch.
- Chép URL 8 lần chạy (mỗi lỗi 1 lần đỏ + 1 lần xanh) và log vào `docs/PIPELINE_EVIDENCE.md`.
- Nộp cả `demo/` để minh họa code gốc, mã sửa, và cách tái hiện từng vấn đề.

**Lưu ý:** `cp` chạy trong Git Bash. Nếu đang dùng PowerShell, dùng `Copy-Item -Force` hoặc thao tác copy bằng File Explorer.
