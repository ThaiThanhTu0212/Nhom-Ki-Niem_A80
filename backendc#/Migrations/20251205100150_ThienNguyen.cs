using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace ThienNguyenAPI.Migrations
{
    /// <inheritdoc />
    public partial class ThienNguyen : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "BinhLuans",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    IdBaiViet = table.Column<int>(type: "int", nullable: false),
                    IdNguoiBinhLuan = table.Column<int>(type: "int", nullable: false),
                    NoiDung = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    NgayBinhLuan = table.Column<DateTime>(type: "datetime2", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_BinhLuans", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "DanhMuc",
                columns: table => new
                {
                    id_dm = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    ten_dm = table.Column<string>(type: "nvarchar(100)", maxLength: 100, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__DanhMuc__00B7C6D26273FE3F", x => x.id_dm);
                });

            migrationBuilder.CreateTable(
                name: "NguoiDung",
                columns: table => new
                {
                    id_nd = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    ho_ten = table.Column<string>(type: "nvarchar(100)", maxLength: 100, nullable: true),
                    email = table.Column<string>(type: "nvarchar(100)", maxLength: 100, nullable: true),
                    so_dien_thoai = table.Column<string>(type: "nvarchar(20)", maxLength: 20, nullable: true),
                    mat_khau = table.Column<string>(type: "nvarchar(100)", maxLength: 100, nullable: true),
                    vai_tro = table.Column<string>(type: "nvarchar(20)", maxLength: 20, nullable: true),
                    trang_thai = table.Column<string>(type: "nvarchar(20)", maxLength: 20, nullable: true, defaultValue: "hoat_dong"),
                    OtpCode = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    OtpExpiry = table.Column<DateTime>(type: "datetime2", nullable: true),
                    EmailVerified = table.Column<bool>(type: "bit", nullable: false),
                    FirebaseUid = table.Column<string>(type: "nvarchar(max)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__NguoiDun__0148B3BF9EE5EF75", x => x.id_nd);
                });

            migrationBuilder.CreateTable(
                name: "ChienDich",
                columns: table => new
                {
                    id_cd = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    id_nguoi_to_chuc = table.Column<int>(type: "int", nullable: true),
                    id_danh_muc = table.Column<int>(type: "int", nullable: true),
                    ten_cd = table.Column<string>(type: "nvarchar(200)", maxLength: 200, nullable: true),
                    mo_ta = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    so_tien_muc_tieu = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    so_tien_hien_tai = table.Column<decimal>(type: "decimal(18,2)", nullable: true, defaultValue: 0m),
                    ngay_bat_dau = table.Column<DateOnly>(type: "date", nullable: true),
                    ngay_ket_thuc = table.Column<DateOnly>(type: "date", nullable: true),
                    trang_thai = table.Column<string>(type: "nvarchar(20)", maxLength: 20, nullable: true),
                    tinh_thanh = table.Column<string>(type: "nvarchar(100)", maxLength: 100, nullable: true),
                    quan_huyen = table.Column<string>(type: "nvarchar(100)", maxLength: 100, nullable: true),
                    dia_chi = table.Column<string>(type: "nvarchar(200)", maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__ChienDic__00B7DEF7386E9E84", x => x.id_cd);
                    table.ForeignKey(
                        name: "FK__ChienDich__id_da__3E52440B",
                        column: x => x.id_danh_muc,
                        principalTable: "DanhMuc",
                        principalColumn: "id_dm");
                    table.ForeignKey(
                        name: "FK__ChienDich__id_ng__3D5E1FD2",
                        column: x => x.id_nguoi_to_chuc,
                        principalTable: "NguoiDung",
                        principalColumn: "id_nd");
                });

            migrationBuilder.CreateTable(
                name: "BaiViet",
                columns: table => new
                {
                    id_bv = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    id_nguoi_dang = table.Column<int>(type: "int", nullable: true),
                    id_chien_dich = table.Column<int>(type: "int", nullable: true),
                    noi_dung = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    hinh_anh = table.Column<string>(type: "nvarchar(255)", maxLength: 255, nullable: true),
                    ngay_dang = table.Column<DateTime>(type: "datetime", nullable: true, defaultValueSql: "(getdate())"),
                    SoLuotThich = table.Column<int>(type: "int", nullable: false),
                    SoLuotBinhLuan = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__BaiViet__00B7D10485BAC77F", x => x.id_bv);
                    table.ForeignKey(
                        name: "FK__BaiViet__id_chie__534D60F1",
                        column: x => x.id_chien_dich,
                        principalTable: "ChienDich",
                        principalColumn: "id_cd");
                    table.ForeignKey(
                        name: "FK__BaiViet__id_nguo__52593CB8",
                        column: x => x.id_nguoi_dang,
                        principalTable: "NguoiDung",
                        principalColumn: "id_nd");
                });

            migrationBuilder.CreateTable(
                name: "BaoCao",
                columns: table => new
                {
                    id_bc = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    id_cd = table.Column<int>(type: "int", nullable: true),
                    id_nd = table.Column<int>(type: "int", nullable: true),
                    noi_dung = table.Column<string>(type: "nvarchar(500)", maxLength: 500, nullable: true),
                    ngay_gui = table.Column<DateTime>(type: "datetime", nullable: true, defaultValueSql: "(getdate())")
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__BaoCao__00B7D117AFA82980", x => x.id_bc);
                    table.ForeignKey(
                        name: "FK__BaoCao__id_cd__4D94879B",
                        column: x => x.id_cd,
                        principalTable: "ChienDich",
                        principalColumn: "id_cd");
                    table.ForeignKey(
                        name: "FK__BaoCao__id_nd__4E88ABD4",
                        column: x => x.id_nd,
                        principalTable: "NguoiDung",
                        principalColumn: "id_nd");
                });

            migrationBuilder.CreateTable(
                name: "QuyenGop",
                columns: table => new
                {
                    id_qg = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    id_cd = table.Column<int>(type: "int", nullable: true),
                    id_nd = table.Column<int>(type: "int", nullable: true),
                    so_tien = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    ngay_quyen_gop = table.Column<DateTime>(type: "datetime", nullable: true, defaultValueSql: "(getdate())"),
                    loi_nhan = table.Column<string>(type: "nvarchar(255)", maxLength: 255, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__QuyenGop__0148AB2428A1610A", x => x.id_qg);
                    table.ForeignKey(
                        name: "FK__QuyenGop__id_cd__4222D4EF",
                        column: x => x.id_cd,
                        principalTable: "ChienDich",
                        principalColumn: "id_cd");
                    table.ForeignKey(
                        name: "FK__QuyenGop__id_nd__4316F928",
                        column: x => x.id_nd,
                        principalTable: "NguoiDung",
                        principalColumn: "id_nd");
                });

            migrationBuilder.CreateTable(
                name: "TepDinhKem",
                columns: table => new
                {
                    id_tep = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    id_cd = table.Column<int>(type: "int", nullable: true),
                    duong_dan = table.Column<string>(type: "nvarchar(255)", maxLength: 255, nullable: true),
                    loai_tep = table.Column<string>(type: "nvarchar(50)", maxLength: 50, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__TepDinhK__6A29A77BECD9F10F", x => x.id_tep);
                    table.ForeignKey(
                        name: "FK__TepDinhKe__id_cd__4AB81AF0",
                        column: x => x.id_cd,
                        principalTable: "ChienDich",
                        principalColumn: "id_cd");
                });

            migrationBuilder.CreateTable(
                name: "GiaoDich",
                columns: table => new
                {
                    id_gd = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    id_qg = table.Column<int>(type: "int", nullable: true),
                    phuong_thuc = table.Column<string>(type: "nvarchar(50)", maxLength: 50, nullable: true),
                    ma_giao_dich = table.Column<string>(type: "nvarchar(50)", maxLength: 50, nullable: true),
                    trang_thai = table.Column<string>(type: "nvarchar(20)", maxLength: 20, nullable: true),
                    ngay_tao = table.Column<DateTime>(type: "datetime", nullable: true, defaultValueSql: "(getdate())")
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__GiaoDich__00B7FE789B2CA3D7", x => x.id_gd);
                    table.ForeignKey(
                        name: "FK__GiaoDich__id_qg__46E78A0C",
                        column: x => x.id_qg,
                        principalTable: "QuyenGop",
                        principalColumn: "id_qg");
                });

            migrationBuilder.CreateIndex(
                name: "IX_BaiViet_id_chien_dich",
                table: "BaiViet",
                column: "id_chien_dich");

            migrationBuilder.CreateIndex(
                name: "IX_BaiViet_id_nguoi_dang",
                table: "BaiViet",
                column: "id_nguoi_dang");

            migrationBuilder.CreateIndex(
                name: "IX_BaoCao_id_cd",
                table: "BaoCao",
                column: "id_cd");

            migrationBuilder.CreateIndex(
                name: "IX_BaoCao_id_nd",
                table: "BaoCao",
                column: "id_nd");

            migrationBuilder.CreateIndex(
                name: "IX_ChienDich_id_danh_muc",
                table: "ChienDich",
                column: "id_danh_muc");

            migrationBuilder.CreateIndex(
                name: "IX_ChienDich_id_nguoi_to_chuc",
                table: "ChienDich",
                column: "id_nguoi_to_chuc");

            migrationBuilder.CreateIndex(
                name: "IX_GiaoDich_id_qg",
                table: "GiaoDich",
                column: "id_qg");

            migrationBuilder.CreateIndex(
                name: "UQ__NguoiDun__AB6E6164D2734D55",
                table: "NguoiDung",
                column: "email",
                unique: true,
                filter: "[email] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "IX_QuyenGop_id_cd",
                table: "QuyenGop",
                column: "id_cd");

            migrationBuilder.CreateIndex(
                name: "IX_QuyenGop_id_nd",
                table: "QuyenGop",
                column: "id_nd");

            migrationBuilder.CreateIndex(
                name: "IX_TepDinhKem_id_cd",
                table: "TepDinhKem",
                column: "id_cd");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "BaiViet");

            migrationBuilder.DropTable(
                name: "BaoCao");

            migrationBuilder.DropTable(
                name: "BinhLuans");

            migrationBuilder.DropTable(
                name: "GiaoDich");

            migrationBuilder.DropTable(
                name: "TepDinhKem");

            migrationBuilder.DropTable(
                name: "QuyenGop");

            migrationBuilder.DropTable(
                name: "ChienDich");

            migrationBuilder.DropTable(
                name: "DanhMuc");

            migrationBuilder.DropTable(
                name: "NguoiDung");
        }
    }
}
