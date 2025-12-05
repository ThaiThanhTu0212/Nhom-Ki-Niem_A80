using System;
using System.Collections.Generic;

namespace ThienNguyenAPI.Data;

public partial class TepDinhKem
{
    public int IdTep { get; set; }

    public int? IdCd { get; set; }

    public string? DuongDan { get; set; }

    public string? LoaiTep { get; set; }

    public virtual ChienDich? IdCdNavigation { get; set; }
}
