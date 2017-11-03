package itesm.mx.movilidad_reportedeproblemas.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

import itesm.mx.movilidad_reportedeproblemas.Fragments.ReportListFragment;
import itesm.mx.movilidad_reportedeproblemas.Models.Report;
import itesm.mx.movilidad_reportedeproblemas.R;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.IDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.ListDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.DummyLoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.ILoginProvider;

public class MyReportsActivity extends AppCompatActivity {

    private LinearLayout list;

    private IDatabaseProvider _db = ListDatabaseProvider.getInstance();
    private ILoginProvider _loginProvider = DummyLoginProvider.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reports);

        list = (LinearLayout) findViewById(R.id.view_content);

        ArrayList<Report> reports = _db.getReportsForUser(_loginProvider.getCurrentUser().getId());

        android.app.FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .replace(list.getId(), ReportListFragment.newInstance(reports))
                .commit();
    }
}
