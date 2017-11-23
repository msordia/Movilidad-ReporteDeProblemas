package itesm.mx.movilidad_reportedeproblemas.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import itesm.mx.movilidad_reportedeproblemas.Models.Category;
import itesm.mx.movilidad_reportedeproblemas.R;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.IDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.WebDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.DummyLoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.GetLoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.ILoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.LoginProviderFactory;

public class ManageAdminsActivity extends AppCompatActivity implements View.OnClickListener {
    private IDatabaseProvider _db = new WebDatabaseProvider();
    private ILoginProvider _loginProvider = LoginProviderFactory.getDefaultInstance();

    Button btnAddAdmin;
    Button btnRemoveAdmin;
    Button btnAddCategory;
    EditText etAddAdmin;
    EditText etRemoveAdmin;
    EditText etAddCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_admins);

        btnAddAdmin = (Button) findViewById(R.id.button_manageAdmin_addAdmin);
        btnRemoveAdmin = (Button) findViewById(R.id.button_manageAdmin_removeAdmin);
        btnAddCategory = (Button) findViewById(R.id.button_manageAdmin_addCategory);
        etAddAdmin = (EditText) findViewById(R.id.edit_manageAdmin_addAdmin);
        etRemoveAdmin = (EditText) findViewById(R.id.edit_manageAdmin_removeAdmin);
        etAddCategory = (EditText) findViewById(R.id.edit_manageAdmin_addCategory);

        btnAddAdmin.setOnClickListener(this);
        btnRemoveAdmin.setOnClickListener(this);
        btnAddCategory.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_manageAdmin_addAdmin:
                onAddAdminClicked();
                break;
            case R.id.button_manageAdmin_removeAdmin:
                onRemoveAdminClicked();
                break;
            case R.id.button_manageAdmin_addCategory:
                onAddCategoryClicked();
                break;
        }
    }

    private void onAddAdminClicked() {
        String id = etAddAdmin.getText().toString();
        btnAddAdmin.setEnabled(false);
        _db.makeAdmin(id, new IDatabaseProvider.IDbHandler<Boolean>() {
            @Override
            public void handle(Boolean result) {
                onAddAdminFinished(result);
            }
        });
    }

    private void onAddAdminFinished(boolean result) {
        btnAddAdmin.setEnabled(true);
        String text = result
                ? "Administrador agregado exitosamente."
                : "No se pudo agregar al administrador";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void onRemoveAdminClicked() {
        String id = etRemoveAdmin.getText().toString();
        if (id.equals(_loginProvider.getCurrentUser().getId())) {
            Toast.makeText(this, "No puedes eliminarte a ti mismo.", Toast.LENGTH_SHORT).show();
            return;
        }

        btnRemoveAdmin.setEnabled(false);
        _db.removeAdmin(id, new IDatabaseProvider.IDbHandler<Boolean>() {
            @Override
            public void handle(Boolean result) {
                onRemoveAdminFinished(result);
            }
        });
    }

    private void onRemoveAdminFinished(boolean result) {
        btnRemoveAdmin.setEnabled(true);
        String text = result
                ? "Administrador eliminado exitosamente."
                : "No se pudo eliminar al administrador";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void onAddCategoryClicked() {
        String id = etAddCategory.getText().toString();
        btnAddCategory.setEnabled(false);
        _db.addCategory(new Category(id), new IDatabaseProvider.IDbHandler<Long>() {
            @Override
            public void handle(Long result) {
                onAddCategoryFinished(result != -1);
            }
        });
    }

    private void onAddCategoryFinished(boolean result) {
        btnAddCategory.setEnabled(true);
        String text = result
                ? "Categoria agregada exitosamente."
                : "No se pudo agregar la categoria";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
