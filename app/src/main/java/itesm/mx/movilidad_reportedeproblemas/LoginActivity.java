package itesm.mx.movilidad_reportedeproblemas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import itesm.mx.movilidad_reportedeproblemas.Services.DummyLoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ILoginProvider _loginProvider;

    EditText etUser;
    EditText etPassword;

    public LoginActivity() {
        _loginProvider = new DummyLoginProvider();
    }

    public LoginActivity(ILoginProvider loginProvider){
        _loginProvider = loginProvider;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser = (EditText) findViewById(R.id.edit_login_user);
        etPassword = (EditText) findViewById(R.id.edit_login_password);
        Button btnLogin = (Button) findViewById(R.id.button_login);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String username = etUser.getText().toString();
        String password = etPassword.getText().toString();

        boolean result = _loginProvider.login(username, password);
        Log.i("Login", username + " " + password + " " + result);

        if (result) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
        }
    }
}
