// Trong file /Data/BinhLuan.cs
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

public class BinhLuan
{
    [Key]
    public int Id { get; set; }

    public int IdBaiViet { get; set; }

    public int IdNguoiBinhLuan { get; set; }

    public string NoiDung { get; set; }

    public DateTime NgayBinhLuan { get; set; }
}