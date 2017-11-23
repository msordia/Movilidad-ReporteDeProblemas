package itesm.mx.movilidad_reportedeproblemas.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import itesm.mx.movilidad_reportedeproblemas.R;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.IDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.WebDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.DummyLoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.GetLoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.ILoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.LoginProviderFactory;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.ServerLoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.PermissionChecker;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginProvider.ILoginHandler {
    private ILoginProvider _loginProvider = LoginProviderFactory.getDefaultInstance();
    private IDatabaseProvider _db = new WebDatabaseProvider();

    private LoginActivity self = this;

    EditText etUser;
    EditText etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser = (EditText) findViewById(R.id.edit_login_user);
        etPassword = (EditText) findViewById(R.id.edit_login_password);
        btnLogin = (Button) findViewById(R.id.button_login);

        btnLogin.setOnClickListener(this);

        if (!PermissionChecker.checkPermission(this, Manifest.permission.INTERNET))
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        _loginProvider.logout();
        btnLogin.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        String username = etUser.getText().toString();
        String password = etPassword.getText().toString();

        _loginProvider.login(username, password, this);
        btnLogin.setEnabled(false);
    }

    @Override
    public void handle(String username, String password, boolean result) {
        Log.i("Login", username + " " + password + " " + result);
        btnLogin.setEnabled(true);
        if (result) {
            _db.isAdmin(username, new IDatabaseProvider.IDbHandler<Boolean>() {
                @Override
                public void handle(Boolean result) {
                    if (result)
                        startActivity(new Intent(self, AdminHomeActivity.class));
                    else
                        startActivity(new Intent(self, GenerateReportActivity.class));
                }
            });
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Context getContext() {
        return getContext();
    }
}
