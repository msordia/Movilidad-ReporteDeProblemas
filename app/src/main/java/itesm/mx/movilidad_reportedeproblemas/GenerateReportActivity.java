package itesm.mx.movilidad_reportedeproblemas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILocationService;
import itesm.mx.movilidad_reportedeproblemas.Services.ListDatabaseProvider;

public class GenerateReportActivity extends AppCompatActivity implements View.OnClickListener {
    private ILocationService _locationService;
    private IDatabaseProvider _db = new ListDatabaseProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_generateReport);

        List<String> categoryNames = new ArrayList<String>();


        Button btnGenerate = (Button) findViewById(R.id.button_generateReport);
        btnGenerate.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

    }
}
