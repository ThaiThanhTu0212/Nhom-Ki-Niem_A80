using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ThienNguyenAPI.Data
{
    [Table("BaiViet")]
    public partial class BaiViet
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Column("id_bv")]
        public int IdBv { get; set; }

        [Column("id_nguoi_dang")] public int? IdNguoiDang { get; set; }
        [Column("id_chien_dich")] public int? IdChienDich { get; set; }
        [Column("noi_dung")] public string? NoiDung { get; set; }
        [Column("hinh_anh")] public string? HinhAnh { get; set; }
        [Column("ngay_dang")] public DateTime? NgayDang { get; set; }

        // QUAN TRỌNG: Tên cột phải viết hoa đúng như trong SQL
        [Column("SoLuotThich")]
        public int SoLuotThich { get; set; } = 0;

        [Column("SoLuotBinhLuan")]
        public int SoLuotBinhLuan { get; set; } = 0;

        public virtual ChienDich? IdChienDichNavigation { get; set; }
        public virtual NguoiDung? IdNguoiDangNavigation { get; set; }
    }
}