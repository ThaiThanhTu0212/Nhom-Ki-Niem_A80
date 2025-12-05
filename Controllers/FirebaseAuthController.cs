using FirebaseAdmin.Auth;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ThienNguyenAPI.Data; // thay bằng namespace DBContext của bạn
// Nếu bạn có hàm tạo JWT riêng thì thêm using ở đây

namespace ThienNguyenAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class FirebaseAuthController : ControllerBase
    {
        private readonly ThienNguyenContext _db;

        public FirebaseAuthController(ThienNguyenContext db)
        {
            _db = db;
        }

        [HttpPost("login")]
        public async Task<IActionResult> LoginWithFirebase([FromBody] FirebaseLoginDto dto)
        {
            if (string.IsNullOrWhiteSpace(dto.Token))
                return BadRequest("Token không được để trống");

            try
            {
                // Verify Firebase ID Token
                FirebaseToken decodedToken = await FirebaseAuth.DefaultInstance.VerifyIdTokenAsync(dto.Token);
                string firebaseUid = decodedToken.Uid;
                string email = decodedToken.Claims["email"]?.ToString() ?? "";
                string? name = decodedToken.Claims.ContainsKey("name") ? decodedToken.Claims["name"]?.ToString() : null;

                if (string.IsNullOrEmpty(email))
                    return BadRequest("Không tìm thấy email trong token");

                // Tìm hoặc tạo user
                var user = await _db.NguoiDungs.FirstOrDefaultAsync(u => u.Email == email);

                if (user == null)
                {
                    user = new NguoiDung
                    {
                        Email = email,
                        HoTen = name ?? "Người dùng Firebase",
                        EmailVerified = true,
                        FirebaseUid = firebaseUid,
                        VaiTro = "User",
                        //TrangThai = true   // nếu bạn đổi thành bool
                        TrangThai = "Active"   // nếu bạn giữ string
                    };
                    _db.NguoiDungs.Add(user);
                }
                else
                {
                    user.FirebaseUid = firebaseUid;
                    user.EmailVerified = true;
                    //user.TrangThai = true;        // sửa ở đây
                    user.TrangThai = "Active"; // nếu giữ string
                    if (string.IsNullOrEmpty(user.HoTen) && !string.IsNullOrEmpty(name))
                        user.HoTen = name;
                }

                await _db.SaveChangesAsync();

                // Nếu bạn có hàm tạo JWT riêng → dùng ở đây
                // var jwt = GenerateJwtToken(user); // ← uncomment nếu có
                // Nếu chưa có → mình tạo tạm 1 cái đơn giản
                var jwt = "TEMP_JWT_" + Guid.NewGuid().ToString(); // ← thay bằng hàm thật của bạn

                return Ok(new
                {
                    message = "Đăng nhập Firebase thành công!",
                    userId = user.IdNd,
                    hoTen = user.HoTen,
                    email = user.Email,
                    vaiTro = user.VaiTro,
                    token = jwt
                });
            }
            catch (FirebaseAuthException ex)
            {
                return Unauthorized($"Token Firebase không hợp lệ: {ex.Message}");
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Lỗi server: {ex.Message}");
            }
        }
    }
    public class FirebaseLoginDto
    {
        public string Token { get; set; } = string.Empty;
    }
}