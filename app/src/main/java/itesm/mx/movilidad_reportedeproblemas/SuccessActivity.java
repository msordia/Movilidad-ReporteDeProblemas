package itesm.mx.movilidad_reportedeproblemas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SuccessActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_REPORT_ID = "reportId";

    private long _reportId = -1;

    private Button btnDone;
    private TextView tvReportId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _reportId = extras.getLong(EXTRA_REPORT_ID);
        }

        btnDone = (Button) findViewById(R.id.button_success_done);
        tvReportId = (TextView) findViewById(R.id.text_success_reportNumber);

        btnDone.setOnClickListener(this);

        tvReportId.setText(Long.toString(_reportId));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_success_done:
                endActivity();
        }
    }

    private void endActivity() {
        Intent intent = new Intent(this, GenerateReportActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
