package itesm.mx.movilidad_reportedeproblemas.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import itesm.mx.movilidad_reportedeproblemas.Fragments.FileDownloadFragment;
import itesm.mx.movilidad_reportedeproblemas.Fragments.MarkAsFinishedFragment;
import itesm.mx.movilidad_reportedeproblemas.Fragments.MarkReportAsStartedFragment;
import itesm.mx.movilidad_reportedeproblemas.Fragments.PlayAudioFragment;
import itesm.mx.movilidad_reportedeproblemas.Fragments.ShowImageFragment;
import itesm.mx.movilidad_reportedeproblemas.Fragments.ViewCommentFragment;
import itesm.mx.movilidad_reportedeproblemas.Models.Comment;
import itesm.mx.movilidad_reportedeproblemas.Models.Image;
import itesm.mx.movilidad_reportedeproblemas.Models.Report;
import itesm.mx.movilidad_reportedeproblemas.Models.UploadedFile;
import itesm.mx.movilidad_reportedeproblemas.Models.Voicenote;
import itesm.mx.movilidad_reportedeproblemas.R;
import itesm.mx.movilidad_reportedeproblemas.Services.IContainer;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.IDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.WebDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.DummyLoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.GetLoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.ILoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.LoginProviderFactory;
import itesm.mx.movilidad_reportedeproblemas.Services.StatusParser;
import itesm.mx.movilidad_reportedeproblemas.Services.WebFileReader;

//////////////////////////////////////////////////////////
//Clase: ReportDetailActivity
// Descripción: Se muestra el detalle del reporte.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////

public class ReportDetailActivity extends AppCompatActivity implements IContainer, OnMapReadyCallback {
    public final static String EXTRA_REPORT = "report";

    private ILoginProvider _loginProvider = LoginProviderFactory.getDefaultInstance();
    private IDatabaseProvider _db = new WebDatabaseProvider();

    TextView tvUser;
    TextView tvStatus;
    TextView tvDate;
    TextView tvCategory;
    ViewGroup vgExtras;
    LinearLayout updateLay;
    GoogleMap map;

    private Report _report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        _report = getIntent().getExtras().getParcelable(EXTRA_REPORT);

        tvUser = (TextView) findViewById(R.id.text_reportDetail_user);
        tvCategory = (TextView) findViewById(R.id.text_reportDetail_category);
        tvDate = (TextView) findViewById(R.id.text_reportDetail_date);
        tvStatus = (TextView) findViewById(R.id.text_reportDetail_status);
        vgExtras = (ViewGroup) findViewById(R.id.layout_reportDetail_extras);

        tvUser.setText(_report.getUserId());
        tvCategory.setText(_report.getCategory().getName());
        tvStatus.setText(StatusParser.Parse(_report.getStatus()));
        tvDate.setText(_report.getDate().toString());

        final android.app.FragmentManager manager = getFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(this);

        _db.isAdmin(_loginProvider.getCurrentUser().getId(), new IDatabaseProvider.IDbHandler<Boolean>() {
            @Override
            public void handle(Boolean result) {
                if (result) {
                    updateLay = (LinearLayout) findViewById(R.id.layout_updateStatus);
                    switch (_report.getStatus()) {
                        case Report.STATUS_PENDING:
                            manager.beginTransaction().add(updateLay.getId(), MarkReportAsStartedFragment.newInstance(_report.getId())).commit();
                            break;
                        case Report.STATUS_IN_PROCESS:
                            manager.beginTransaction().add(updateLay.getId(), MarkAsFinishedFragment.newInstance(_report.getId())).commit();
                            break;
                    }
                }
            }
        });

        _db.getCommentsForReport(_report.getId(), new IDatabaseProvider.IDbHandler<ArrayList<Comment>>() {
            @Override
            public void handle(ArrayList<Comment> comments) {
                if (comments == null) {
                    Log.e("ReportDetail", "Could not get comments");
                    Toast.makeText(getApplicationContext(), "Hubo un problema con la conexion con el servior. Intente de nuevo.", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                for (Comment comment : comments) {
                    addExtraFragment(manager, ViewCommentFragment.newInstance(comment));
                }
            }
        });

        _db.getVoicenotesForReport(_report.getId(), new IDatabaseProvider.IDbHandler<ArrayList<Voicenote>>() {
            @Override
            public void handle(ArrayList<Voicenote> voicenotes) {
                if (voicenotes == null) {
                    Log.e("ReportDetail", "Could not get voicenotes");
                    Toast.makeText(getApplicationContext(), "Hubo un problema con la conexion con el servior. Intente de nuevo.", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            for (final Voicenote voicenote : voicenotes) {
                String url = WebFileReader.BASE_URL + WebFileReader.DIR_VOICENOTE + voicenote.getName();
                WebFileReader.readFile(url, new WebFileReader.WebFileHandler() {
                    @Override
                    public void handle(byte[] bytes) {
                        voicenote.setBytes(bytes);
                        addExtraFragment(manager, PlayAudioFragment.newInstance(voicenote));
                    }
                });
            }
            }
        });

        _db.getImagesForReport(_report.getId(), new IDatabaseProvider.IDbHandler<ArrayList<Image>>() {
            @Override
            public void handle(ArrayList<Image> images) {
                if (images == null) {
                    Log.e("ReportDetail", "Could not get images");
                    Toast.makeText(getApplicationContext(), "Hubo un problema con la conexion con el servior. Intente de nuevo.", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                for (final Image image: images) {
                    String url = WebFileReader.BASE_URL + WebFileReader.DIR_IMAGE + image.getName();
                    WebFileReader.readFile(url, new WebFileReader.WebFileHandler() {
                        @Override
                        public void handle(byte[] bytes) {
                            addExtraFragment(manager, ShowImageFragment.newInstance(bytes));
                        }
                    });
                }
            }
        });

        _db.getFilesForReport(_report.getId(), new IDatabaseProvider.IDbHandler<ArrayList<UploadedFile>>() {
            @Override
            public void handle(ArrayList<UploadedFile> files) {
                if (files == null) {
                    Log.e("ReportDetail", "Could not get files");
                    Toast.makeText(getApplicationContext(), "Hubo un problema con la conexion con el servior. Intente de nuevo.", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                for (final UploadedFile file : files) {
                    addExtraFragment(manager, FileDownloadFragment.newInstance(file.getName()));
                }
            }
        });
    }

    private void addExtraFragment(android.app.FragmentManager manager, Fragment fragment) {
        manager.beginTransaction()
                .add(vgExtras.getId(), fragment)
                .commit();
    }

    @Override
    public Object getComponent(Class<?> $class, int code) {
        if ($class == MarkReportAsStartedFragment.IListener.class) {
            return new MarkReportAsStartedFragment.IListener() {
                @Override
                public void handle(boolean success) {
                    if (success) {
                        tvStatus.setText(StatusParser.Parse(Report.STATUS_IN_PROCESS));
                        Toast.makeText(getApplicationContext(), "Se cambio exitosamente.", Toast.LENGTH_SHORT).show();
                        final android.app.FragmentManager manager = getFragmentManager();
                        updateLay.removeAllViews();
                        manager.beginTransaction().add(updateLay.getId(), MarkAsFinishedFragment.newInstance(_report.getId())).commit();

                    } else {
                        Toast.makeText(getApplicationContext(), "No se cambio exitosamente.", Toast.LENGTH_SHORT).show();
                    }
                }
            };
        } else if ($class == MarkAsFinishedFragment.IListener.class) {
            return new MarkAsFinishedFragment.IListener() {
                @Override
                public void handle(boolean success, int newStatus) {
                    if (success) {
                        tvStatus.setText(StatusParser.Parse(newStatus));
                        Toast.makeText(getApplicationContext(), "Se cambio exitosamente.", Toast.LENGTH_SHORT).show();
                        final android.app.FragmentManager manager = getFragmentManager();
                        updateLay.removeAllViews();

                    } else {
                        Toast.makeText(getApplicationContext(), "No se cambio exitosamente.", Toast.LENGTH_SHORT).show();
                    }
                }
            };
        }
        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        LatLng ubica = new LatLng(_report.getLatitude(), _report.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ubica,20));

        MarkerOptions reporte = new MarkerOptions().title("titulo reporte").position(ubica);
        map.addMarker(reporte);
    }
}
