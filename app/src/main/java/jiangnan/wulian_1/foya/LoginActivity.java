package jiangnan.wulian_1.foya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import jiangnan.wulian_1.foya.database.DatabaseHelper;
import jiangnan.wulian_1.foya.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        // 检查是否已经登录
        if (isLoggedIn()) {
            startMainActivity();
            return;
        }

        setupClickListeners();
    }

    private void setupClickListeners() {
        // 登录按钮
        binding.btnLogin.setOnClickListener(v -> performLogin());

        // 注册按钮
        binding.btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // 忘记密码
        binding.btnForgotPassword.setOnClickListener(v -> {
            Toast.makeText(this, "请联系管理员重置密码", Toast.LENGTH_SHORT).show();
        });
    }

    private void performLogin() {
        String username = binding.etUsername.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        // 验证输入
        if (TextUtils.isEmpty(username)) {
            binding.etUsername.setError("请输入用户名");
            binding.etUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            binding.etPassword.setError("请输入密码");
            binding.etPassword.requestFocus();
            return;
        }

        // 显示加载状态
        binding.btnLogin.setEnabled(false);
        binding.btnLogin.setText("登录中...");

        // 验证用户
        if (databaseHelper.checkUser(username, password)) {
            // 保存登录状态
            saveLoginState(username);
            
            Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
            startMainActivity();
        } else {
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            binding.btnLogin.setEnabled(true);
            binding.btnLogin.setText("登录");
        }
    }

    private boolean isLoggedIn() {
        SharedPreferences prefs = getSharedPreferences("FoyaPrefs", MODE_PRIVATE);
        return prefs.getBoolean("isLoggedIn", false);
    }

    private void saveLoginState(String username) {
        SharedPreferences prefs = getSharedPreferences("FoyaPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("username", username);
        editor.apply();
    }

    private void startMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
} 