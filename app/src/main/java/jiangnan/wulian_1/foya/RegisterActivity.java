package jiangnan.wulian_1.foya;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import jiangnan.wulian_1.foya.database.DatabaseHelper;
import jiangnan.wulian_1.foya.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        setupClickListeners();
    }

    private void setupClickListeners() {
        // 注册按钮
        binding.btnRegister.setOnClickListener(v -> performRegister());

        // 返回登录按钮
        binding.btnBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void performRegister() {
        String username = binding.etUsername.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String confirmPassword = binding.etConfirmPassword.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String phone = binding.etPhone.getText().toString().trim();

        // 验证输入
        if (!validateInput(username, password, confirmPassword, email, phone)) {
            return;
        }

        // 检查用户名是否已存在
        if (databaseHelper.isUsernameExists(username)) {
            binding.etUsername.setError("用户名已存在");
            binding.etUsername.requestFocus();
            return;
        }

        // 显示加载状态
        binding.btnRegister.setEnabled(false);
        binding.btnRegister.setText("注册中...");

        // 注册用户
        if (databaseHelper.registerUser(username, password, email, phone)) {
            Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
            
            // 返回登录页面
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
            binding.btnRegister.setEnabled(true);
            binding.btnRegister.setText("注册");
        }
    }

    private boolean validateInput(String username, String password, String confirmPassword, 
                                 String email, String phone) {
        // 验证用户名
        if (TextUtils.isEmpty(username)) {
            binding.etUsername.setError("请输入用户名");
            binding.etUsername.requestFocus();
            return false;
        }
        if (username.length() < 3) {
            binding.etUsername.setError("用户名至少3个字符");
            binding.etUsername.requestFocus();
            return false;
        }

        // 验证密码
        if (TextUtils.isEmpty(password)) {
            binding.etPassword.setError("请输入密码");
            binding.etPassword.requestFocus();
            return false;
        }
        if (password.length() < 6) {
            binding.etPassword.setError("密码至少6个字符");
            binding.etPassword.requestFocus();
            return false;
        }

        // 验证确认密码
        if (TextUtils.isEmpty(confirmPassword)) {
            binding.etConfirmPassword.setError("请确认密码");
            binding.etConfirmPassword.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            binding.etConfirmPassword.setError("两次输入的密码不一致");
            binding.etConfirmPassword.requestFocus();
            return false;
        }

        // 验证邮箱
        if (!TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError("请输入有效的邮箱地址");
            binding.etEmail.requestFocus();
            return false;
        }

        // 验证手机号
        if (!TextUtils.isEmpty(phone) && phone.length() != 11) {
            binding.etPhone.setError("请输入11位手机号");
            binding.etPhone.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
} 