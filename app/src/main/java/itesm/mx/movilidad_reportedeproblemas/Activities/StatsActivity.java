package itesm.mx.movilidad_reportedeproblemas.Activities;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import itesm.mx.movilidad_reportedeproblemas.Models.Report;
import itesm.mx.movilidad_reportedeproblemas.R;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.IDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.WebDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.IKMeans.IKMeans;
import itesm.mx.movilidad_reportedeproblemas.Services.IKMeans.KMeans;
import itesm.mx.movilidad_reportedeproblemas.Services.Tuple;

public class StatsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private IKMeans _kmeans = new KMeans();
    private WebDatabaseProvider _db = new WebDatabaseProvider();

    TextView tvResult;

    private GoogleMap _map;
    private StatsActivity _self = this;
    private ArrayList<Tuple<MarkerOptions, IKMeans.Cluster>> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(_self);

        tvResult = findViewById(R.id.text_result);
        tvResult.setText("Obteniendo datos");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        _map = googleMap;
        _map.getUiSettings().setZoomControlsEnabled(true);
        LatLng ubica = new LatLng(25.650789, -100.289558);
        _map.moveCamera(CameraUpdateFactory.newLatLngZoom(ubica, 15));
        _map.setOnMarkerClickListener(this);

        _db.getReports(new IDatabaseProvider.IDbHandler<ArrayList<Report>>() {
            @Override
            public void handle(ArrayList<Report> result) {
                tvResult.setText("Calculando");
                final ArrayList<IKMeans.Point> points = new ArrayList<>();
                for (Report report : result) {
                    IKMeans.Point point = new IKMeans.Point(report.getLongitude(), report.getLatitude());
                    points.add(point);
                }

                _kmeans.solve(5, points, new IKMeans.IKMeansHandler() {
                    @Override
                    public void handle(ArrayList<IKMeans.Cluster> result) {
                        tvResult.setText("Calculo exitoso");
                        markers = new ArrayList<>();
                        for (final IKMeans.Cluster cluster : result) {
                            if (cluster.x != 0 || cluster.y != 0) {
                                markers.add(new Tuple<>(new MarkerOptions().position(new LatLng(cluster.y, cluster.x)), cluster));
                            }
                        }

                        resetMapAndDraw(new ArrayList<MarkerOptions>());
                    }
                });
            }
        });
    }

    private void resetMapAndDraw(final ArrayList<MarkerOptions> additionalMarkers) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //StringBuilder sb = new StringBuilder();
                _map.clear();
                for (Tuple<MarkerOptions, IKMeans.Cluster> tuple : markers) {
                    //sb.append(tuple.y.y + ", " + tuple.y.x + "\n");
                    Marker m = _map.addMarker(tuple.x);
                    m.setTag(tuple.y);
                }

                for (MarkerOptions marker : additionalMarkers) {
                    _map.addMarker(marker);
                }
                //tvResult.setText(sb.toString());
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTag() == null)
            return false;

        IKMeans.Cluster cluster = (IKMeans.Cluster) marker.getTag();
        ArrayList<MarkerOptions> additional = new ArrayList<>();
        for (IKMeans.Point point : cluster.points) {
            additional.add(new MarkerOptions().position(new LatLng(point.y, point.x)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }

        resetMapAndDraw(additional);

        return false;
    }
}
