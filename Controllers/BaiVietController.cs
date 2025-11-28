using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ThienNguyenAPI.Data;

namespace ThienNguyenAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class BaiVietController : ControllerBase
    {
        private readonly ThienNguyenContext _db;
        public BaiVietController(ThienNguyenContext db) => _db = db;

        // GET: api/baiviet → lấy danh sách (có lượt thích + bình luận)
        [HttpGet]
        public async Task<IActionResult> Get()
        {
            var data = await _db.BaiViets
                .OrderByDescending(b => b.NgayDang)
                .Select(b => new
                {
                    id = b.IdBv,                                // Android dùng "id"
                    b.IdNguoiDang,
                    b.NoiDung,
                    b.HinhAnh,
                    b.SoLuotThich,       // lượt thích
                    b.SoLuotBinhLuan,    // lượt bình luận
                    NgayDang = b.NgayDang.HasValue
                        ? b.NgayDang.Value.ToString("dd/MM/yyyy HH:mm")
                        : ""
                })
                .Take(50)
                .ToListAsync();

            return Ok(data);
        }

        // DELETE: api/baiviet/5 → xóa bài + xóa ảnh
        [HttpDelete("{id:int}")]
        public async Task<IActionResult> Delete(int id)
        {
            var baiViet = await _db.BaiViets.FindAsync(id);
            if (baiViet == null) return NotFound();

            // Xóa ảnh nếu có
            if (!string.IsNullOrEmpty(baiViet.HinhAnh))
            {
                var fileName = Path.GetFileName(baiViet.HinhAnh.TrimStart('/'));
                var imagePath = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", "images", fileName);
                if (System.IO.File.Exists(imagePath))
                {
                    try { System.IO.File.Delete(imagePath); }
                    catch { /* không báo lỗi nếu xóa ảnh fail */ }
                }
            }

            _db.BaiViets.Remove(baiViet);
            await _db.SaveChangesAsync();
            return NoContent(); // 204
        }

        // POST: api/baiviet → đăng bài (có thể có ảnh)
        [HttpPost]
        public async Task<IActionResult> Post([FromForm] CreatePostRequest req)
        {
            if (req.IdNguoiDang <= 0)
                return BadRequest("Id người đăng không hợp lệ");

            string? hinhAnhUrl = null;

            if (req.File != null && req.File.Length > 0)
            {
                var allowed = new[] { ".jpg", ".jpeg", ".png", ".gif", ".webp" };
                var ext = Path.GetExtension(req.File.FileName).ToLowerInvariant();
                if (!allowed.Contains(ext))
                    return BadRequest("Chỉ chấp nhận file ảnh!");

                var fileName = Guid.NewGuid() + ext;
                var folder = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", "images");
                Directory.CreateDirectory(folder);
                var filePath = Path.Combine(folder, fileName);

                using var stream = new FileStream(filePath, FileMode.Create);
                await req.File.CopyToAsync(stream);

                hinhAnhUrl = $"/images/{fileName}";
            }

            var bv = new BaiViet
            {
                IdNguoiDang = req.IdNguoiDang,
                NoiDung = req.NoiDung ?? "",
                HinhAnh = hinhAnhUrl,
                NgayDang = DateTime.Now,
                SoLuotThich = 0,
                SoLuotBinhLuan = 0
            };

            _db.BaiViets.Add(bv);
            await _db.SaveChangesAsync();

            return Ok(new
            {
                message = "Đăng bài thành công!",
                id = bv.IdBv,
                hinhAnh = hinhAnhUrl
            });
        }

        // Trong file BaiVietController.cs
        // LIKE & COMMENT – ĐÃ SỬA ĐÚNG 100%
        [HttpPost("{id:int}/like")]
        public async Task<IActionResult> Like(int id)
        {
            var bv = await _db.BaiViets.FindAsync(id);
            if (bv == null) return NotFound();
            bv.SoLuotThich += 1;
            await _db.SaveChangesAsync();
            return Ok(new { soLuotThich = bv.SoLuotThich });
        }

        // Trong file BaiVietController.cs

        // GET: api/baiviet/{id}/comments  → Lấy danh sách bình luận của bài viết
        [HttpGet("{id}/comments")]
        public async Task<IActionResult> GetComments(int id)
        {
            var comments = await _db.BinhLuans
                .Where(c => c.IdBaiViet == id)
                .OrderBy(c => c.NgayBinhLuan)
                .Select(c => new {
                    c.Id,
                    c.IdNguoiBinhLuan,
                    c.NoiDung,
                    NgayBinhLuan = c.NgayBinhLuan.ToString("dd/MM/yyyy HH:mm")
                })
                .ToListAsync();
            return Ok(comments);
        }

        // POST: api/baiviet/{id}/comments → Thêm một bình luận mới
        [HttpPost("{id}/comments")]
        public async Task<IActionResult> AddComment(int id, [FromBody] CommentRequest req)
        {
            var baiViet = await _db.BaiViets.FindAsync(id);
            if (baiViet == null) return NotFound("Không tìm thấy bài viết");

            if (string.IsNullOrWhiteSpace(req.NoiDung))
                return BadRequest("Nội dung bình luận không được để trống");

            var comment = new BinhLuan
            {
                IdBaiViet = id,
                IdNguoiBinhLuan = req.IdNguoiBinhLuan,
                NoiDung = req.NoiDung,
                NgayBinhLuan = DateTime.Now
            };

            // Tăng số lượng bình luận trong bài viết gốc
            baiViet.SoLuotBinhLuan += 1;

            _db.BinhLuans.Add(comment);
            await _db.SaveChangesAsync();

            // Trả về bình luận vừa tạo để Android hiển thị ngay
            return Ok(new
            {
                comment.Id,
                comment.IdNguoiBinhLuan,
                comment.NoiDung,
                NgayBinhLuan = comment.NgayBinhLuan.ToString("dd/MM/yyyy HH:mm")
            });
        }
    }
    // Đặt DTO này ở cuối file BaiVietController.cs
    public class CommentRequest
    {
        public int IdNguoiBinhLuan { get; set; }
        public string NoiDung { get; set; }
    }
    

    // DTO cho đăng bài
    public class CreatePostRequest
    {
        public int IdNguoiDang { get; set; }
        public string? NoiDung { get; set; }
        public IFormFile? File { get; set; }
    }
}