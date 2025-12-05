using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;

namespace ThienNguyenAPI.Data;

public partial class ThienNguyenContext : DbContext
{
    public ThienNguyenContext()
    {
    }

    public ThienNguyenContext(DbContextOptions<ThienNguyenContext> options)
        : base(options)
    {
    }

    public virtual DbSet<BaiViet> BaiViets { get; set; }

    public virtual DbSet<BaoCao> BaoCaos { get; set; }

    public virtual DbSet<ChienDich> ChienDiches { get; set; }

    public virtual DbSet<DanhMuc> DanhMucs { get; set; }

    public virtual DbSet<GiaoDich> GiaoDiches { get; set; }

    public virtual DbSet<NguoiDung> NguoiDungs { get; set; }

    public virtual DbSet<QuyenGop> QuyenGops { get; set; }

    public virtual DbSet<TepDinhKem> TepDinhKems { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see https://go.microsoft.com/fwlink/?LinkId=723263.
        => optionsBuilder.UseSqlServer("Server=CHAODAIKA\\THAITHANHTU2340;Database=ThienNguyen;User Id=sa;Password=12345;TrustServerCertificate=true;");

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<BaiViet>(entity =>
        {
            entity.HasKey(e => e.IdBv).HasName("PK__BaiViet__00B7D10485BAC77F");

            entity.ToTable("BaiViet");

            entity.Property(e => e.IdBv).HasColumnName("id_bv");
            entity.Property(e => e.HinhAnh)
                .HasMaxLength(255)
                .HasColumnName("hinh_anh");
            entity.Property(e => e.IdChienDich).HasColumnName("id_chien_dich");
            entity.Property(e => e.IdNguoiDang).HasColumnName("id_nguoi_dang");
            entity.Property(e => e.NgayDang)
                .HasDefaultValueSql("(getdate())")
                .HasColumnType("datetime")
                .HasColumnName("ngay_dang");
            entity.Property(e => e.NoiDung).HasColumnName("noi_dung");

            entity.HasOne(d => d.IdChienDichNavigation).WithMany(p => p.BaiViets)
                .HasForeignKey(d => d.IdChienDich)
                .HasConstraintName("FK__BaiViet__id_chie__534D60F1");

            entity.HasOne(d => d.IdNguoiDangNavigation).WithMany(p => p.BaiViets)
                .HasForeignKey(d => d.IdNguoiDang)
                .HasConstraintName("FK__BaiViet__id_nguo__52593CB8");
        });

        modelBuilder.Entity<BaoCao>(entity =>
        {
            entity.HasKey(e => e.IdBc).HasName("PK__BaoCao__00B7D117AFA82980");

            entity.ToTable("BaoCao");

            entity.Property(e => e.IdBc).HasColumnName("id_bc");
            entity.Property(e => e.IdCd).HasColumnName("id_cd");
            entity.Property(e => e.IdNd).HasColumnName("id_nd");
            entity.Property(e => e.NgayGui)
                .HasDefaultValueSql("(getdate())")
                .HasColumnType("datetime")
                .HasColumnName("ngay_gui");
            entity.Property(e => e.NoiDung)
                .HasMaxLength(500)
                .HasColumnName("noi_dung");

            entity.HasOne(d => d.IdCdNavigation).WithMany(p => p.BaoCaos)
                .HasForeignKey(d => d.IdCd)
                .HasConstraintName("FK__BaoCao__id_cd__4D94879B");

            entity.HasOne(d => d.IdNdNavigation).WithMany(p => p.BaoCaos)
                .HasForeignKey(d => d.IdNd)
                .HasConstraintName("FK__BaoCao__id_nd__4E88ABD4");
        });

        modelBuilder.Entity<ChienDich>(entity =>
        {
            entity.HasKey(e => e.IdCd).HasName("PK__ChienDic__00B7DEF7386E9E84");

            entity.ToTable("ChienDich");

            entity.Property(e => e.IdCd).HasColumnName("id_cd");
            entity.Property(e => e.DiaChi)
                .HasMaxLength(200)
                .HasColumnName("dia_chi");
            entity.Property(e => e.IdDanhMuc).HasColumnName("id_danh_muc");
            entity.Property(e => e.IdNguoiToChuc).HasColumnName("id_nguoi_to_chuc");
            entity.Property(e => e.MoTa).HasColumnName("mo_ta");
            entity.Property(e => e.NgayBatDau).HasColumnName("ngay_bat_dau");
            entity.Property(e => e.NgayKetThuc).HasColumnName("ngay_ket_thuc");
            entity.Property(e => e.QuanHuyen)
                .HasMaxLength(100)
                .HasColumnName("quan_huyen");
            entity.Property(e => e.SoTienHienTai)
                .HasDefaultValue(0m)
                .HasColumnType("decimal(18, 2)")
                .HasColumnName("so_tien_hien_tai");
            entity.Property(e => e.SoTienMucTieu)
                .HasColumnType("decimal(18, 2)")
                .HasColumnName("so_tien_muc_tieu");
            entity.Property(e => e.TenCd)
                .HasMaxLength(200)
                .HasColumnName("ten_cd");
            entity.Property(e => e.TinhThanh)
                .HasMaxLength(100)
                .HasColumnName("tinh_thanh");
            entity.Property(e => e.TrangThai)
                .HasMaxLength(20)
                .HasColumnName("trang_thai");

            entity.HasOne(d => d.IdDanhMucNavigation).WithMany(p => p.ChienDiches)
                .HasForeignKey(d => d.IdDanhMuc)
                .HasConstraintName("FK__ChienDich__id_da__3E52440B");

            entity.HasOne(d => d.IdNguoiToChucNavigation).WithMany(p => p.ChienDiches)
                .HasForeignKey(d => d.IdNguoiToChuc)
                .HasConstraintName("FK__ChienDich__id_ng__3D5E1FD2");
        });

        modelBuilder.Entity<DanhMuc>(entity =>
        {
            entity.HasKey(e => e.IdDm).HasName("PK__DanhMuc__00B7C6D26273FE3F");

            entity.ToTable("DanhMuc");

            entity.Property(e => e.IdDm).HasColumnName("id_dm");
            entity.Property(e => e.TenDm)
                .HasMaxLength(100)
                .HasColumnName("ten_dm");
        });

        modelBuilder.Entity<GiaoDich>(entity =>
        {
            entity.HasKey(e => e.IdGd).HasName("PK__GiaoDich__00B7FE789B2CA3D7");

            entity.ToTable("GiaoDich");

            entity.Property(e => e.IdGd).HasColumnName("id_gd");
            entity.Property(e => e.IdQg).HasColumnName("id_qg");
            entity.Property(e => e.MaGiaoDich)
                .HasMaxLength(50)
                .HasColumnName("ma_giao_dich");
            entity.Property(e => e.NgayTao)
                .HasDefaultValueSql("(getdate())")
                .HasColumnType("datetime")
                .HasColumnName("ngay_tao");
            entity.Property(e => e.PhuongThuc)
                .HasMaxLength(50)
                .HasColumnName("phuong_thuc");
            entity.Property(e => e.TrangThai)
                .HasMaxLength(20)
                .HasColumnName("trang_thai");

            entity.HasOne(d => d.IdQgNavigation).WithMany(p => p.GiaoDiches)
                .HasForeignKey(d => d.IdQg)
                .HasConstraintName("FK__GiaoDich__id_qg__46E78A0C");
        });

        modelBuilder.Entity<NguoiDung>(entity =>
        {
            entity.HasKey(e => e.IdNd).HasName("PK__NguoiDun__0148B3BF9EE5EF75");

            entity.ToTable("NguoiDung");

            entity.HasIndex(e => e.Email, "UQ__NguoiDun__AB6E6164D2734D55").IsUnique();

            entity.Property(e => e.IdNd).HasColumnName("id_nd");
            entity.Property(e => e.Email)
                .HasMaxLength(100)
                .HasColumnName("email");
            entity.Property(e => e.HoTen)
                .HasMaxLength(100)
                .HasColumnName("ho_ten");
            entity.Property(e => e.MatKhau)
                .HasMaxLength(100)
                .HasColumnName("mat_khau");
            entity.Property(e => e.SoDienThoai)
                .HasMaxLength(20)
                .HasColumnName("so_dien_thoai");
            entity.Property(e => e.TrangThai)
                .HasMaxLength(20)
                .HasDefaultValue("hoat_dong")
                .HasColumnName("trang_thai");
            entity.Property(e => e.VaiTro)
                .HasMaxLength(20)
                .HasColumnName("vai_tro");
        });

        modelBuilder.Entity<QuyenGop>(entity =>
        {
            entity.HasKey(e => e.IdQg).HasName("PK__QuyenGop__0148AB2428A1610A");

            entity.ToTable("QuyenGop");

            entity.Property(e => e.IdQg).HasColumnName("id_qg");
            entity.Property(e => e.IdCd).HasColumnName("id_cd");
            entity.Property(e => e.IdNd).HasColumnName("id_nd");
            entity.Property(e => e.LoiNhan)
                .HasMaxLength(255)
                .HasColumnName("loi_nhan");
            entity.Property(e => e.NgayQuyenGop)
                .HasDefaultValueSql("(getdate())")
                .HasColumnType("datetime")
                .HasColumnName("ngay_quyen_gop");
            entity.Property(e => e.SoTien)
                .HasColumnType("decimal(18, 2)")
                .HasColumnName("so_tien");

            entity.HasOne(d => d.IdCdNavigation).WithMany(p => p.QuyenGops)
                .HasForeignKey(d => d.IdCd)
                .HasConstraintName("FK__QuyenGop__id_cd__4222D4EF");

            entity.HasOne(d => d.IdNdNavigation).WithMany(p => p.QuyenGops)
                .HasForeignKey(d => d.IdNd)
                .HasConstraintName("FK__QuyenGop__id_nd__4316F928");
        });

        modelBuilder.Entity<TepDinhKem>(entity =>
        {
            entity.HasKey(e => e.IdTep).HasName("PK__TepDinhK__6A29A77BECD9F10F");

            entity.ToTable("TepDinhKem");

            entity.Property(e => e.IdTep).HasColumnName("id_tep");
            entity.Property(e => e.DuongDan)
                .HasMaxLength(255)
                .HasColumnName("duong_dan");
            entity.Property(e => e.IdCd).HasColumnName("id_cd");
            entity.Property(e => e.LoaiTep)
                .HasMaxLength(50)
                .HasColumnName("loai_tep");

            entity.HasOne(d => d.IdCdNavigation).WithMany(p => p.TepDinhKems)
                .HasForeignKey(d => d.IdCd)
                .HasConstraintName("FK__TepDinhKe__id_cd__4AB81AF0");
        });

        OnModelCreatingPartial(modelBuilder);
    }
    // Trong file /Data/ThienNguyenContext.cs
    public virtual DbSet<BinhLuan> BinhLuans { get; set; }
    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
