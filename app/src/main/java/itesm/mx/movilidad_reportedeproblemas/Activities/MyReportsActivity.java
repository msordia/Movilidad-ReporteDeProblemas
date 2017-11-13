package itesm.mx.movilidad_reportedeproblemas.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import itesm.mx.movilidad_reportedeproblemas.Fragments.ReportListFragment;
import itesm.mx.movilidad_reportedeproblemas.Models.Category;
import itesm.mx.movilidad_reportedeproblemas.Models.Report;
import itesm.mx.movilidad_reportedeproblemas.R;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.IDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.WebDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.DummyLoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.ILoginProvider;

public class MyReportsActivity extends AppCompatActivity {

    private LinearLayout list;

    private IDatabaseProvider _db = new WebDatabaseProvider();
    private ILoginProvider _loginProvider = DummyLoginProvider.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reports);



        list = (LinearLayout) findViewById(R.id.view_content);

        _db.getReportsForUser(_loginProvider.getCurrentUser().getId(), new IDatabaseProvider.IDbHandler<ArrayList<Report>>() {
            @Override
            public void handle(final ArrayList<Report> reports) {
                final HashMap<Long, Category> hash = new HashMap<>();

                _db.getCategories(new IDatabaseProvider.IDbHandler<ArrayList<Category>>() {
                    @Override
                    public void handle(ArrayList<Category> categories) {
                        for (Category category : categories) {
                            hash.put(category.getId(), category);
                        }

                        for (Report report : reports) {
                            report.setCategory(hash.get(report.getCategoryId()));
                        }

                        android.app.FragmentManager manager = getFragmentManager();
                        manager.beginTransaction()
                                .replace(list.getId(), ReportListFragment.newInstance(reports))
                                .commit();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        _db.getReportsForUser(_loginProvider.getCurrentUser().getId(), new IDatabaseProvider.IDbHandler<ArrayList<Report>>() {
            @Override
            public void handle(final ArrayList<Report> reports) {
                final HashMap<Long, Category> hash = new HashMap<>();

                _db.getCategories(new IDatabaseProvider.IDbHandler<ArrayList<Category>>() {
                    @Override
                    public void handle(ArrayList<Category> categories) {
                        for (Category category : categories) {
                            hash.put(category.getId(), category);
                        }

                        for (Report report : reports) {
                            report.setCategory(hash.get(report.getCategoryId()));
                        }

                        android.app.FragmentManager manager = getFragmentManager();
                        manager.beginTransaction()
                                .replace(list.getId(), ReportListFragment.newInstance(reports))
                                .commit();
                    }
                });
            }
        });
    }
}
