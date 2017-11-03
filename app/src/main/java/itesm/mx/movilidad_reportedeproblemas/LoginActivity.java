package itesm.mx.movilidad_reportedeproblemas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.IDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.ListDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.DummyLoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.ILoginProvider;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ILoginProvider _loginProvider = DummyLoginProvider.getInstance();
    private IDatabaseProvider _db = ListDatabaseProvider.getInstance();

    EditText etUser;
    EditText etPassword;

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
            if (_db.isAdmin(username))
                startActivity(new Intent(this, AdminHomeActivity.class));
            else
                startActivity(new Intent(this, GenerateReportActivity.class));
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
        }
    }
}
