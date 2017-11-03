package itesm.mx.movilidad_reportedeproblemas.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import itesm.mx.movilidad_reportedeproblemas.Fragments.ViewCommentFragment;
import itesm.mx.movilidad_reportedeproblemas.Models.Comment;
import itesm.mx.movilidad_reportedeproblemas.Models.Report;
import itesm.mx.movilidad_reportedeproblemas.R;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.IDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.ListDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.DummyLoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.ILoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.StatusParser;

public class ReportDetailActivity extends AppCompatActivity {
    public final static String EXTRA_REPORT = "report";

    private ILoginProvider _loginProvider = DummyLoginProvider.getInstance();
    private IDatabaseProvider _db = ListDatabaseProvider.getInstance();

    Button btnUpdateStatus;
    TextView tvUser;
    TextView tvStatus;
    TextView tvDate;
    TextView tvCategory;
    ViewGroup vgExtras;

    private Report _report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        _report = getIntent().getExtras().getParcelable(EXTRA_REPORT);

        btnUpdateStatus = (Button) findViewById(R.id.button_reportDetail_updateStatus);
        tvUser = (TextView) findViewById(R.id.text_reportDetail_user);
        tvCategory = (TextView) findViewById(R.id.text_reportDetail_category);
        tvDate = (TextView) findViewById(R.id.text_reportDetail_date);
        tvStatus = (TextView) findViewById(R.id.text_reportDetail_status);
        vgExtras = (ViewGroup) findViewById(R.id.layout_reportDetail_extras);

        btnUpdateStatus.setEnabled(_db.isAdmin(_loginProvider.getCurrentUser().getId()));

        tvUser.setText(_loginProvider.getUser(_report.getUserId()).getName());
        tvCategory.setText(_report.getCategory().getName());
        tvStatus.setText(StatusParser.Parse(_report.getStatus()));
        tvDate.setText(_report.getDate().toString());

        android.app.FragmentManager manager = getFragmentManager();
        for (Comment comment : _report.getComments()) {
            manager.beginTransaction()
                    .add(vgExtras.getId(), ViewCommentFragment.newInstance(comment))
                    .commit();
        }
    }
}
