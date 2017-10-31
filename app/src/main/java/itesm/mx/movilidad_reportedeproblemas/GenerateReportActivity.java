package itesm.mx.movilidad_reportedeproblemas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import itesm.mx.movilidad_reportedeproblemas.Adapters.CategoryAdapter;
import itesm.mx.movilidad_reportedeproblemas.Models.Category;
import itesm.mx.movilidad_reportedeproblemas.Services.DummyLocationService;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILocationService;
import itesm.mx.movilidad_reportedeproblemas.Services.ListDatabaseProvider;

public class GenerateReportActivity extends AppCompatActivity implements View.OnClickListener {
    private ILocationService _locationService = new DummyLocationService();
    private IDatabaseProvider _db = new ListDatabaseProvider();

    Button btnGenerate;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);

        spinner = (Spinner) findViewById(R.id.spinner_generateReport);
        setupSpinner();

        btnGenerate = (Button) findViewById(R.id.button_generateReport);
        btnGenerate.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_generateReport:
                generateReport();
                break;
        }
    }

    private void setupSpinner() {
        CategoryAdapter adapter = new CategoryAdapter(this, (Category[])_db.getCategories().toArray());
        spinner.setAdapter(adapter);
    }

    private void generateReport() {
        ILocationService.Location location = _locationService.getLocation();

        Category category = (Category) spinner.getSelectedItem();

        Log.i("GenerateReport", String.format("(%f, %f) %s", location.Longitude, location.Latitude, category.getName()));
    }
}
