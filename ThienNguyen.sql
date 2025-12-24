USE master;
GO

IF EXISTS (SELECT name FROM sys.databases WHERE name = N'ThienNguyen')
BEGIN
    EXEC sp_MSforeachdb 'IF ''?'' = ''ThienNguyen''
    BEGIN
        ALTER DATABASE [ThienNguyen] SET SINGLE_USER WITH ROLLBACK IMMEDIATE 
    END'
    USE master
    DROP DATABASE ThienNguyen
END

CREATE DATABASE ThienNguyen
GO

USE ThienNguyen
GO
-- 2. TẠO BẢNG NGƯỜI DÙNG
-- ======================================
CREATE TABLE nguoi_dung  (
    id_nd INT IDENTITY(1,1) PRIMARY KEY,
    ho_ten NVARCHAR(100),
    email NVARCHAR(100) UNIQUE,
    so_dien_thoai NVARCHAR(20),
    mat_khau NVARCHAR(100),
	avatar Nvarchar(max),
    vai_tro NVARCHAR(20), -- nguoi_ung_ho, nguoi_van_dong, admin
    trang_thai NVARCHAR(20) DEFAULT N'hoat_dong'
);

-- ======================================
-- 3. TẠO BẢNG DANH MỤC
-- ======================================
CREATE TABLE danh_muc (
    id_dm INT IDENTITY(1,1) PRIMARY KEY,
    ten_dm NVARCHAR(100)
);

-- ======================================
-- 4. TẠO BẢNG CHIẾN DỊCH
-- ======================================
CREATE TABLE chien_dich (
    id_cd INT IDENTITY(1,1) PRIMARY KEY,
    id_nguoi_to_chuc INT FOREIGN KEY REFERENCES nguoi_dung (id_nd),
    id_danh_muc INT FOREIGN KEY REFERENCES danh_muc(id_dm),
    ten_cd NVARCHAR(200),
    mo_ta NVARCHAR(MAX),
	hinh_anh nvarchar(max),
    so_tien_muc_tieu DECIMAL(18,2),
    so_tien_hien_tai DECIMAL(18,2) DEFAULT 0,
    ngay_bat_dau DATE,
    ngay_ket_thuc DATE,
    trang_thai NVARCHAR(20),
    tinh_thanh NVARCHAR(100),
    quan_huyen NVARCHAR(100),
    dia_chi NVARCHAR(200)
);

-- ======================================
-- 5. TẠO BẢNG QUYÊN GÓP
-- ======================================
CREATE TABLE quyen_gop (
    id_qg INT IDENTITY(1,1) PRIMARY KEY,
    id_cd INT FOREIGN KEY REFERENCES chien_dich(id_cd),
    id_nd INT FOREIGN KEY REFERENCES nguoi_dung (id_nd),
    so_tien DECIMAL(18,2),
    ngay_quyen_gop DATETIME DEFAULT GETDATE(),
    loi_nhan NVARCHAR(255)
);

-- ======================================
-- 6. TẠO BẢNG GIAO DỊCH
-- ======================================
CREATE TABLE giao_dich (
    id_gd INT IDENTITY(1,1) PRIMARY KEY,
    id_qg INT FOREIGN KEY REFERENCES quyen_gop(id_qg),
    phuong_thuc NVARCHAR(50),
    ma_giao_dich NVARCHAR(50),
    trang_thai NVARCHAR(20),
    ngay_tao DATETIME DEFAULT GETDATE()
);

-- ======================================
-- 7. TẠO BẢNG TỆP ĐÍNH KÈM
-- ======================================
CREATE TABLE tep_dinh_kem (
    id_tep INT IDENTITY(1,1) PRIMARY KEY,
    id_cd INT FOREIGN KEY REFERENCES chien_dich(id_cd),
    duong_dan NVARCHAR(255),
    loai_tep NVARCHAR(50)
);

-- ======================================
-- 8. TẠO BẢNG BÁO CÁO / PHẢN ÁNH
-- ======================================
CREATE TABLE bao_cao (
    id_bc INT IDENTITY(1,1) PRIMARY KEY,
    id_cd INT FOREIGN KEY REFERENCES chien_dich(id_cd),
    id_nd INT FOREIGN KEY REFERENCES nguoi_dung (id_nd),
    noi_dung NVARCHAR(500),
    ngay_gui DATETIME DEFAULT GETDATE()
);

CREATE TABLE BaiViet
(
    id_bv INT IDENTITY PRIMARY KEY,
    id_nguoi_dang INT NULL,
    id_chien_dich INT NULL,
    noi_dung NVARCHAR(MAX) NULL,
    hinh_anh NVARCHAR(500) NULL,
    ngay_dang DATETIME2 NULL,
    SoLuotThich INT NOT NULL DEFAULT 0,
    SoLuotBinhLuan INT NOT NULL DEFAULT 0
)
GO
-- XÓA BẢNG BÌNH LUẬN CŨ NẾU ĐÃ TỒN TẠI
IF OBJECT_ID('BinhLuans', 'U') IS NOT NULL
    DROP TABLE BinhLuans;
GO

-- TẠO BẢNG BÌNH LUẬN
CREATE TABLE BinhLuans
(
    Id INT IDENTITY(1,1) PRIMARY KEY,
    IdBaiViet INT NOT NULL,
    IdNguoiBinhLuan INT NOT NULL,
    NoiDung NVARCHAR(MAX) NOT NULL,
    NgayBinhLuan DATETIME NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_BinhLuans_BaiViet FOREIGN KEY (IdBaiViet) 
        REFERENCES BaiViet(id_bv) ON DELETE CASCADE
);
GO

-- KIỂM TRA VÀ TỰ ĐỘNG THÊM 1 BÀI VIẾT MẪU NẾU BẢNG BaiViet TRỐNG
IF NOT EXISTS (SELECT 1 FROM BaiViet)
BEGIN
    PRINT 'Bảng BaiViet đang trống → Tự động thêm 1 bài viết mẫu để test bình luận.'

    INSERT INTO BaiViet (id_nguoi_dang, id_chien_dich, noi_dung, hinh_anh, ngay_dang, SoLuotThich, SoLuotBinhLuan)
    VALUES 
    (1, 1, N'Đây là bài viết mẫu tự động tạo để test chức năng bình luận. Bạn có thể xóa sau.', NULL, GETDATE(), 0, 0);

    PRINT 'Đã thêm bài viết mẫu với id_bv = 1 (hoặc ID mới nhất).'
END
ELSE
BEGIN
    PRINT 'Bảng BaiViet đã có dữ liệu → Sử dụng bài viết hiện có.'
END
GO

-- LẤY ID BÀI VIẾT ĐẦU TIÊN ĐỂ INSERT BÌNH LUẬN (CHẮC CHẮN KHÔNG NULL)
DECLARE @IdBaiViet INT;
SELECT TOP 1 @IdBaiViet = id_bv FROM BaiViet ORDER BY id_bv;

PRINT 'Sử dụng IdBaiViet = ' + CAST(@IdBaiViet AS NVARCHAR) + ' để thêm bình luận mẫu.'

-- INSERT 8 BÌNH LUẬN MẪU VÀO BÀI VIẾT ĐÓ
INSERT INTO BinhLuans (IdBaiViet, IdNguoiBinhLuan, NoiDung)
VALUES
(@IdBaiViet, 1, N'Chiến dịch rất ý nghĩa! Mình ủng hộ hết mình ❤️'),
(@IdBaiViet, 2, N'Cho mình hỏi thêm thông tin chi tiết được không admin?'),
(@IdBaiViet, 3, N'Chúc mọi người sớm đạt mục tiêu quyên góp!'),
(@IdBaiViet, 1, N'Mình đã chia sẻ bài viết cho bạn bè rồi nha.'),
(@IdBaiViet, 2, N'Admin cập nhật thêm hình ảnh thực tế nữa nhé!'),
(@IdBaiViet, 3, N'Tuyệt vời quá, cảm ơn cộng đồng!'),
(@IdBaiViet, 1, N'Hy vọng nhiều người cùng tham gia hơn.'),
(@IdBaiViet, 2, N'Mình sẽ quyên góp ngay đây!');

-- CẬP NHẬT SỐ LƯỢT BÌNH LUẬN CHÍNH XÁC CHO TẤT CẢ BÀI VIẾT
UPDATE BaiViet 
SET SoLuotBinhLuan = (
    SELECT COUNT(*) 
    FROM BinhLuans 
    WHERE BinhLuans.IdBaiViet = BaiViet.id_bv
);
	
ALTER TABLE nguoi_dung ADD FirebaseUid NVARCHAR(250) NULL;
ALTER TABLE nguoi_dung ADD SoDienThoai NVARCHAR(15) NULL;
ALTER TABLE nguoi_dung ADD PhoneVerified BIT NULL;


-- ===== NGƯỜI DÙNG =====
INSERT INTO nguoi_dung  (ho_ten, email, so_dien_thoai, mat_khau, vai_tro)
VALUES
(N'Nguyễn Minh An', N'an.nguyen@gmail.com', N'0912345678', N'123456', N'nguoi_ung_ho'),
(N'Trần Thu Hà', N'ha.tran@gmail.com', N'0987654321', N'123456', N'nguoi_van_dong'),
(N'Lê Quang Vũ', N'vu.le@gmail.com', N'0905123456', N'123456', N'admin');

-- ===== DANH MỤC =====
INSERT INTO danh_muc (ten_dm)
VALUES
(N'Học sinh nghèo'),
(N'Cứu trợ thiên tai'),
(N'Hỗ trợ người vô gia cư');

-- ===== CHIẾN DỊCH =====
INSERT INTO chien_dich (
    id_nguoi_to_chuc, id_danh_muc, ten_cd, mo_ta,
    so_tien_muc_tieu, so_tien_hien_tai,
    ngay_bat_dau, ngay_ket_thuc, trang_thai,
    tinh_thanh, quan_huyen, dia_chi
)
VALUES
(2, 1, N'Hỗ trợ học sinh nghèo vùng cao', N'Trao học bổng và sách vở cho học sinh vùng cao khó khăn.', 10000000, 2500000, '2025-10-01', '2025-12-01', N'dang_dien_ra', N'Lào Cai', N'Bắc Hà', N'Trường Tiểu học Bắc Hà A'),
(2, 2, N'Chung tay vì miền Trung', N'Hỗ trợ người dân bị ảnh hưởng bởi lũ lụt.', 20000000, 8000000, '2025-09-15', '2025-11-15', N'dang_dien_ra', N'Quảng Trị', N'Hướng Hóa', N'Xã Tân Lập'),
(2, 3, N'Mái ấm cho người vô gia cư', N'Xây dựng nơi trú ẩn và hỗ trợ thực phẩm cho người vô gia cư tại TP.HCM.', 30000000, 5000000, '2025-08-01', '2025-12-31', N'dang_dien_ra', N'TP.HCM', N'Quận 3', N'123 Nguyễn Thị Minh Khai');

-- ===== QUYÊN GÓP =====
INSERT INTO quyen_gop (id_cd, id_nd, so_tien, loi_nhan)
VALUES
(1, 1, 500000, N'Chúc các em học tốt!'),
(2, 1, 1000000, N'Cùng nhau giúp miền Trung vượt qua khó khăn.'),
(3, 1, 300000, N'Mong mọi người có nơi ở ấm áp.');

-- ===== GIAO DỊCH =====
INSERT INTO giao_dich (id_qg, phuong_thuc, ma_giao_dich, trang_thai)
VALUES
(1, N'Momo', N'MM12345', N'thanh_cong'),
(2, N'Ngân hàng', N'VCB56789', N'thanh_cong'),
(3, N'Momo', N'MM98765', N'thanh_cong');

-- ===== TỆP ĐÍNH KÈM =====
INSERT INTO tep_dinh_kem (id_cd, duong_dan, loai_tep)
VALUES
(1, N'/images/hocsinhngheo1.jpg', N'hinhanh'),
(2, N'/images/mientrung1.jpg', N'hinhanh'),
(3, N'/images/vo_gia_cu1.jpg', N'hinhanh');

-- ===== BÁO CÁO =====
INSERT INTO bao_cao (id_cd, id_nd, noi_dung)
VALUES
(1, 1, N'Cập nhật tiến độ trao học bổng cho học sinh.'),
(2, 2, N'Chiến dịch đã gửi hàng cứu trợ đến 3 xã.'),
(3, 1, N'Tình nguyện viên đã hoàn thành việc phân phát thực phẩm.');
INSERT INTO BaiViet
    (id_nguoi_dang, id_chien_dich, noi_dung, hinh_anh, ngay_dang, SoLuotThich, SoLuotBinhLuan)
VALUES
    (1, 1, N'Bài viết về chiến dịch 1', N'anh1.jpg', GETDATE(), 10, 2),

    (2, 1, N'Người dùng 2 chia sẻ cảm nhận', N'anh2.jpg', GETDATE(), 5, 1),

    (3, 2, N'Thông báo hoạt động gây quỹ', N'anh3.png', GETDATE(), 25, 4),

    (1, 2, N'Kêu gọi quyên góp hỗ trợ trẻ em', N'anh4.png', GETDATE(), 40, 10),

    (2, 3, N'Cập nhật tình hình chiến dịch 3', N'anh5.jpg', GETDATE(), 7, 3);


-- ===== 1. NGƯỜI DÙNG =====
SELECT * FROM nguoi_dung ;

-- ===== 2. DANH MỤC =====
SELECT * FROM danh_muc;

-- ===== 3. CHIẾN DỊCH =====
SELECT * FROM chien_dich;

-- ===== 4. QUYÊN GÓP =====
SELECT * FROM quyen_gop;

-- ===== 5. GIAO DỊCH =====
SELECT * FROM giao_dich;

-- ===== 6. TỆP ĐÍNH KÈM =====
SELECT * FROM tep_dinh_kem;

-- ===== 7. BÁO CÁO =====
SELECT * FROM bao_cao;

-- ===== 8. BÀI VIẾT =====
