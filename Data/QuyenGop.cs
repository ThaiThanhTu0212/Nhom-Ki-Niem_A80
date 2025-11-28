using System;
using System.Collections.Generic;

namespace ThienNguyenAPI.Data;

public partial class QuyenGop
{
    public int IdQg { get; set; }

    public int? IdCd { get; set; }

    public int? IdNd { get; set; }

    public decimal? SoTien { get; set; }

    public DateTime? NgayQuyenGop { get; set; }

    public string? LoiNhan { get; set; }

    public virtual ICollection<GiaoDich> GiaoDiches { get; set; } = new List<GiaoDich>();

    public virtual ChienDich? IdCdNavigation { get; set; }

    public virtual NguoiDung? IdNdNavigation { get; set; }
}
