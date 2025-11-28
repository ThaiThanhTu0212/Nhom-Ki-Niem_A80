using System;
using System.Collections.Generic;

namespace ThienNguyenAPI.Data;

public partial class GiaoDich
{
    public int IdGd { get; set; }

    public int? IdQg { get; set; }

    public string? PhuongThuc { get; set; }

    public string? MaGiaoDich { get; set; }

    public string? TrangThai { get; set; }

    public DateTime? NgayTao { get; set; }

    public virtual QuyenGop? IdQgNavigation { get; set; }
}
