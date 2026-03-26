// Thay đổi URL này nếu Server của bạn chạy port khác
alert("Hệ thống JavaScript đã sẵn sàng!");
const BASE_URL = "http://localhost:8080/api/auth";

/**
 * 1. HÀM ĐĂNG KÝ (Register)
 */
async function handleRegister() {
    const usernameInput = document.getElementById('reg-user');
    const passwordInput = document.getElementById('reg-pass');
    const roleInput = document.getElementById('reg-role');

    // Kiểm tra nhanh input trước khi gửi
    if (!usernameInput.value || !passwordInput.value) {
        alert("Vui lòng nhập đầy đủ Username và Password!");
        return;
    }

    const userData = {
        username: usernameInput.value,
        password: passwordInput.value,
        role: roleInput.value
    };

    try {
        const response = await fetch(`${BASE_URL}/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userData)
        });

        if (response.ok) {
            alert("Đăng ký thành công! Bây giờ bạn có thể đăng nhập.");
            // Xóa trắng form sau khi đăng ký
            usernameInput.value = '';
            passwordInput.value = '';
        } else {
            const errorMsg = await response.text();
            alert("Đăng ký thất bại: " + errorMsg);
        }
    } catch (error) {
        console.error("Lỗi kết nối:", error);
        alert("Không thể kết nối đến Server. Hãy kiểm tra Backend đã chạy chưa!");
    }
}

/**
 * 2. HÀM ĐĂNG NHẬP (Login)
 */
async function handleLogin() {
    const username = document.getElementById('login-user').value;
    const password = document.getElementById('login-pass').value;

    if (!username || !password) {
        alert("Vui lòng nhập tài khoản và mật khẩu!");
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            const data = await response.json();
            
            // LƯU THÔNG TIN VÀO LOCALSTORAGE
            // data.accessToken và data.role phải khớp với class AuthResponse ở Backend
            localStorage.setItem('accessToken', data.accessToken);
            localStorage.setItem('userRole', data.role);
            localStorage.setItem('username', username);

            alert("Đăng nhập thành công!");
            
            // Chuyển hướng sang trang danh sách nhân viên
            window.location.href = "/employees/list";
        } else {
            alert("Tài khoản hoặc mật khẩu không chính xác!");
        }
    } catch (error) {
        console.error("Lỗi đăng nhập:", error);
        alert("Lỗi hệ thống! Vui lòng thử lại sau.");
    }
}

/**
 * 3. HÀM ĐĂNG XUẤT (Logout)
 */
function handleLogout() {
    // Xóa sạch dấu vết trong trình duyệt
    localStorage.removeItem('accessToken');
    localStorage.removeItem('userRole');
    localStorage.removeItem('username');
    
    alert("Đã đăng xuất!");
    window.location.href = "auth.html"; // Quay về trang login
}

/**
 * 4. HÀM TIỆN ÍCH: Lấy Token để gọi các API khác
 */
function getAuthHeader() {
    const token = localStorage.getItem('accessToken');
    return token ? { 'Authorization': `Bearer ${token}` } : {};
}