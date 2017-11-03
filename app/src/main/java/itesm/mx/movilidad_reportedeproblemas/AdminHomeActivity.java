package itesm.mx.movilidad_reportedeproblemas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.DummyLoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.ILoginProvider;

public class AdminHomeActivity extends AppCompatActivity {
    private ILoginProvider _loginProvider = DummyLoginProvider.getInstance();

    TextView tvName;
    Button btnAdminister;
    Button btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        tvName = (TextView) findViewById(R.id.text_adminHome_userName);
        btnAdminister = (Button) findViewById(R.id.button_adminHome_administer);
        btnHistory = (Button) findViewById(R.id.button_adminHome_history);

        tvName.setText(_loginProvider.getCurrentUser().getName());

    }
}
