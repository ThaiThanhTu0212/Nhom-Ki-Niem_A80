using System;
using System.Collections.Generic;

namespace ThienNguyenAPI.Data;

public partial class DanhMuc
{
    public int IdDm { get; set; }

    public string? TenDm { get; set; }

    public virtual ICollection<ChienDich> ChienDiches { get; set; } = new List<ChienDich>();
}
