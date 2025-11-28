using System;
using System.Collections.Generic;

namespace ThienNguyenAPI.Data;

public partial class NguoiDung
{
    public int IdNd { get; set; }

    public string? HoTen { get; set; }

    public string? Email { get; set; }

    public string? SoDienThoai { get; set; }

    public string? MatKhau { get; set; }

    public string? VaiTro { get; set; }

    public string? TrangThai { get; set; }

    public virtual ICollection<BaiViet> BaiViets { get; set; } = new List<BaiViet>();

    public virtual ICollection<BaoCao> BaoCaos { get; set; } = new List<BaoCao>();

    public virtual ICollection<ChienDich> ChienDiches { get; set; } = new List<ChienDich>();

    public virtual ICollection<QuyenGop> QuyenGops { get; set; } = new List<QuyenGop>();
}
