// using ThienNguyenAPI.Data; // Đảm bảo bạn đã thêm using này
// using Microsoft.EntityFrameworkCore; // Và using này

using FirebaseAdmin.Auth;
using FirebaseAdmin;
using Google.Apis.Auth.OAuth2;
using Microsoft.AspNetCore.Builder.Extensions;
using Microsoft.EntityFrameworkCore;
using ThienNguyenAPI.Data;


var builder = WebApplication.CreateBuilder(args);

// =================== CÁC DỊCH VỤ CẦN THIẾT ===================
var firebaseKeyPath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "firebase-service-account.json");

FirebaseApp.Create(new AppOptions()
{
    Credential = GoogleCredential.FromFile(firebaseKeyPath),
    ProjectId = builder.Configuration["Firebase:ProjectId"]
});

// Đăng ký để inject vào controller (tùy chọn)
builder.Services.AddSingleton(FirebaseAuth.DefaultInstance);
//1. (QUAN TRỌNG) Thêm chính sách CORS để cho phép Android gọi vào
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowAll",
        policy =>
        {
            policy.AllowAnyOrigin()   // Chấp nhận yêu cầu từ bất kỳ đâu
                  .AllowAnyMethod()   // Chấp nhận mọi phương thức (GET, POST, DELETE,...)
                  .AllowAnyHeader();  // Chấp nhận mọi header
        });
});


// 2. Thêm dịch vụ Controllers
builder.Services.AddControllers();

// 3. (QUAN TRỌNG) Thêm và cấu hình DbContext để kết nối CSDL
// Đoạn mã này đọc chuỗi kết nối từ file appsettings.json
var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");
builder.Services.AddDbContext<ThienNguyenContext>(options =>
    options.UseSqlServer(connectionString)); // Giả sử bạn dùng SQL Server


// 4. Thêm Swagger để test API (giữ nguyên)
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();


// =================== BUILD ỨNG DỤNG ===================
var app = builder.Build();


// =================== CẤU HÌNH PIPELINE HTTP ===================

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

// KHÔNG DÙNG HTTPS cho môi trường dev với Android app dùng HTTP
// app.UseHttpsRedirection();


// 5. (QUAN TRỌNG) Sử dụng chính sách CORS đã định nghĩa ở trên
app.UseCors("AllowAll");


// 6. (BẮT BUỘC) Bật tính năng phục vụ file tĩnh từ thư mục wwwroot
// Đây là dòng code cho phép Android xem được ảnh của bạn
app.UseStaticFiles();


app.UseAuthorization();

app.MapControllers();

app.Run();
