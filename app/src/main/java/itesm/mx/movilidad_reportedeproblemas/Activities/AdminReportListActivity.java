package itesm.mx.movilidad_reportedeproblemas.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import itesm.mx.movilidad_reportedeproblemas.Fragments.ReportListFragment;
import itesm.mx.movilidad_reportedeproblemas.Models.Category;
import itesm.mx.movilidad_reportedeproblemas.Models.Report;
import itesm.mx.movilidad_reportedeproblemas.R;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.IDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.WebDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.DummyLoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.ILoginProvider;

public class AdminReportListActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private LinearLayout list;

    private IDatabaseProvider _db = new WebDatabaseProvider();
    private ILoginProvider _loginProvider = DummyLoginProvider.getInstance();

    private ArrayList<MarkerOptions> _markers = null;
    private GoogleMap _map;
    private HashMap<Long, Report> _reports = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report_list);

        list = (LinearLayout) findViewById(R.id.layout_list);

        final android.app.FragmentManager manager = getFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        _db.getReports(new IDatabaseProvider.IDbHandler<ArrayList<Report>>() {
            @Override
            public void handle(final ArrayList<Report> reports) {
                final HashMap<Long, Category> hash = new HashMap<>();

                ArrayList<Report> toRemove = new ArrayList<>();
                for (Report report : reports) {
                    int status = report.getStatus();
                    if (status == Report.STATUS_FAILURE || status == Report.STATUS_SUCCESS) {
                        toRemove.add(report);
                    }
                }

                for (Report report : toRemove) {
                    reports.remove(report);
                }

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

                if (_map != null)
                    for (Report report : reports) {
                        LatLng ubica = new LatLng(report.getLatitude(), report.getLongitude());
                        _map.addMarker(new MarkerOptions().position(ubica).title(Long.toString(report.getId())));
                        _reports.put(report.getId(), report);
                    }
                else {
                    _markers = new ArrayList<>();
                    for (Report report : reports) {
                        LatLng ubica = new LatLng(report.getLatitude(), report.getLongitude());
                        _markers.add(new MarkerOptions().position(ubica).title(Long.toString(report.getId())));
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        _map = googleMap;
        _map.getUiSettings().setZoomControlsEnabled(true);
        LatLng ubica = new LatLng(25.650789, -100.289558);
        _map.moveCamera(CameraUpdateFactory.newLatLngZoom(ubica, 15));
        _map.setOnMarkerClickListener(this);

        if (_markers != null) {
            for (MarkerOptions markerOptions : _markers) {
                _map.addMarker(markerOptions);
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        long reportId = Long.parseLong(marker.getTitle());
        Intent intent = new Intent(this, ReportDetailActivity.class);
        intent.putExtra(ReportDetailActivity.EXTRA_REPORT, _reports.get(reportId));
        startActivity(intent);
        return false;
    }
}
