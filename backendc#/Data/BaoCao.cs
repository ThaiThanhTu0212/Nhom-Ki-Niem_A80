using System;
using System.Collections.Generic;

namespace ThienNguyenAPI.Data;

public partial class BaoCao
{
    public int IdBc { get; set; }

    public int? IdCd { get; set; }

    public int? IdNd { get; set; }

    public string? NoiDung { get; set; }

    public DateTime? NgayGui { get; set; }

    public virtual ChienDich? IdCdNavigation { get; set; }

    public virtual NguoiDung? IdNdNavigation { get; set; }
}
