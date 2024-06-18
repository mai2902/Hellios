
    function togglePassword() {
      var passwordField = document.getElementById("password");
      var passwordIcon = document.querySelector(".toggle-password");

      if (passwordField.type === "password") {
        passwordField.type = "text";
        passwordIcon.classList.remove("glyphicon-eye-open");
        passwordIcon.classList.add("glyphicon-eye-close");
      } else {
        passwordField.type = "password";
        passwordIcon.classList.remove("glyphicon-eye-close");
        passwordIcon.classList.add("glyphicon-eye-open");
      }
    }

    document.getElementById("loginForm").addEventListener("submit", function(event) {
      event.preventDefault(); // Prevent form submission

      var username = document.getElementById("username").value;
      var password = document.getElementById("password").value;

      // Đoạn mã xác thực đơn giản (chỉ để mô phỏng)
      if (username === "admin123" && password === "123456") {
        // Chuyển hướng đến trang menu.html sau khi đăng nhập thành công
        window.location.href = "home.html";
      } else {
        // Hiển thị thông báo lỗi trên thanh đăng nhập
        var errorMessage = document.getElementById("errorMessage");
        errorMessage.textContent = "Tên đăng nhập hoặc mật khẩu không đúng!";
      }
    });