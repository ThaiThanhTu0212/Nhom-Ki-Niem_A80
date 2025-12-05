using System;
using System.Collections.Generic;

namespace ThienNguyenAPI.Data;

public partial class ChienDich
{
    public int IdCd { get; set; }

    public int? IdNguoiToChuc { get; set; }

    public int? IdDanhMuc { get; set; }

    public string? TenCd { get; set; }

    public string? MoTa { get; set; }

    public decimal? SoTienMucTieu { get; set; }

    public decimal? SoTienHienTai { get; set; }

    public DateOnly? NgayBatDau { get; set; }

    public DateOnly? NgayKetThuc { get; set; }

    public string? TrangThai { get; set; }

    public string? TinhThanh { get; set; }

    public string? QuanHuyen { get; set; }

    public string? DiaChi { get; set; }

    public virtual ICollection<BaiViet> BaiViets { get; set; } = new List<BaiViet>();

    public virtual ICollection<BaoCao> BaoCaos { get; set; } = new List<BaoCao>();

    public virtual DanhMuc? IdDanhMucNavigation { get; set; }

    public virtual NguoiDung? IdNguoiToChucNavigation { get; set; }

    public virtual ICollection<QuyenGop> QuyenGops { get; set; } = new List<QuyenGop>();

    public virtual ICollection<TepDinhKem> TepDinhKems { get; set; } = new List<TepDinhKem>();
}
