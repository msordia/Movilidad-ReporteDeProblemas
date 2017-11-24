package itesm.mx.movilidad_reportedeproblemas.Fragments;

//////////////////////////////////////////////////////////
//Clase: KmeansMapFragment
// Descripción: Fragmento para el mapa.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import itesm.mx.movilidad_reportedeproblemas.Models.Report;
import itesm.mx.movilidad_reportedeproblemas.R;
import itesm.mx.movilidad_reportedeproblemas.Services.IKMeans.IKMeans;
import itesm.mx.movilidad_reportedeproblemas.Services.IKMeans.KMeans;
import itesm.mx.movilidad_reportedeproblemas.Services.Tuple;

public class KmeansMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private static final String ARG_REPORTS = "reports";

    private IKMeans _kmeans = new KMeans();

    private Report[] _reports;
    private GoogleMap _map;
    private ScrollFriendlySupportMapFragment _mapFragment;
    private List<Tuple<MarkerOptions, IKMeans.Cluster>> _markers;
    private ScrollFriendlySupportMapFragment.OnTouchListener _listener;

    public KmeansMapFragment() { }

    public static KmeansMapFragment newInstance(Collection<Report> reports) {
        KmeansMapFragment fragment = new KmeansMapFragment();
        Bundle args = new Bundle();
        Report[] arrReport = new Report[reports.size()];
        reports.toArray(arrReport);
        args.putParcelableArray(ARG_REPORTS, arrReport);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _reports = (Report[]) getArguments().getParcelableArray(ARG_REPORTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kmeans_map, container, false);

        _mapFragment = (ScrollFriendlySupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap);
        _mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        _map = googleMap;
        _map.getUiSettings().setZoomControlsEnabled(true);
        LatLng location = new LatLng(25.650789, -100.289558);
        _map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        _map.setOnMarkerClickListener(this);

        _mapFragment.setListener(_listener);

        doKmeans();
    }

    public void setListener(ScrollFriendlySupportMapFragment.OnTouchListener listener) {
        _listener = listener;
        if (_mapFragment != null) {
            _mapFragment.setListener(_listener);
        }
    }

    private void resetMapAndDraw(final ArrayList<MarkerOptions> additionalMarkers) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                _map.clear();
                for (Tuple<MarkerOptions, IKMeans.Cluster> tuple : _markers) {
                    Marker m = _map.addMarker(tuple.x);
                    m.setTag(tuple.y);
                }

                for (MarkerOptions marker : additionalMarkers) {
                    _map.addMarker(marker);
                }
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

    private void doKmeans() {
        final List<IKMeans.Point> points = new ArrayList<>();
        for (Report report : _reports) {
            IKMeans.Point point = new IKMeans.Point(report.getLongitude(), report.getLatitude());
            points.add(point);
        }

        _kmeans.solve(Math.max(points.size() / 3, 1), points, new IKMeans.OnKmeansFinishedListener() {
            @Override
            public void onKmeansFinished(ArrayList<IKMeans.Cluster> points) {
                _markers = new ArrayList<>();
                for (final IKMeans.Cluster cluster : points) {
                    if (cluster.x != 0 || cluster.y != 0) {
                        _markers.add(new Tuple<>(new MarkerOptions().position(new LatLng(cluster.y, cluster.x)), cluster));
                    }
                }

                resetMapAndDraw(new ArrayList<MarkerOptions>());
            }
        });
    }
}
