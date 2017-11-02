package itesm.mx.movilidad_reportedeproblemas;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.Collection;

import itesm.mx.movilidad_reportedeproblemas.Adapters.CategoryAdapter;
import itesm.mx.movilidad_reportedeproblemas.Models.Category;
import itesm.mx.movilidad_reportedeproblemas.Models.Comment;
import itesm.mx.movilidad_reportedeproblemas.Models.Image;
import itesm.mx.movilidad_reportedeproblemas.Models.Report;
import itesm.mx.movilidad_reportedeproblemas.Models.UploadedFile;
import itesm.mx.movilidad_reportedeproblemas.Models.Voicenote;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.DummyLoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.IFileReader.FileReader;
import itesm.mx.movilidad_reportedeproblemas.Services.IFileReader.IFileReader;
import itesm.mx.movilidad_reportedeproblemas.Services.FileNameFinder;
import itesm.mx.movilidad_reportedeproblemas.Services.ILocationService.LocationService;
import itesm.mx.movilidad_reportedeproblemas.Services.IBitmapManager.HashByteArrayManager;
import itesm.mx.movilidad_reportedeproblemas.Services.IBitmapManager.IByteArrayManager;
import itesm.mx.movilidad_reportedeproblemas.Services.ICommentManager.HashStringManager;
import itesm.mx.movilidad_reportedeproblemas.Services.ICommentManager.IStringManager;
import itesm.mx.movilidad_reportedeproblemas.Services.IContainer;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.IDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.ILocationService.ILocationService;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.ILoginProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.ListDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.PermissionChecker;
import itesm.mx.movilidad_reportedeproblemas.Services.UriPathFinder;

public class GenerateReportActivity extends AppCompatActivity implements View.OnClickListener, IContainer{

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_FILE_RESULT_CODE = 2;
    public static final int BITMAP_CONTAINER = 1;
    public static final int AUDIO_CONTAINER = 2;
    public static final int PATH_CONTAINER = 1;
    public static final int COMMENT_CONTAINER = 2;

    private ILocationService _locationService;
    private IDatabaseProvider _db = new ListDatabaseProvider();
    private IStringManager _commentManager = new HashStringManager();
    private IByteArrayManager _bitmapManager = new HashByteArrayManager();
    private IByteArrayManager _soundManager = new HashByteArrayManager();
    private IStringManager _fileManager = new HashStringManager();
    private IFileReader _fileReader = new FileReader();
    private ILoginProvider _loginProvider = new DummyLoginProvider();

    private Spinner spinner;
    private ViewGroup vgExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);

        spinner = (Spinner) findViewById(R.id.spinner_generateReport);
        CategoryAdapter adapter = new CategoryAdapter(this, (Category[])_db.getCategories().toArray());
        spinner.setAdapter(adapter);

        Button btnGenerate = (Button) findViewById(R.id.button_generateReport);
        btnGenerate.setOnClickListener(this);

        ImageButton btnTakePhoto = (ImageButton) findViewById(R.id.button_generateReport_photo);
        btnTakePhoto.setOnClickListener(this);

        ImageButton btnRecordAudio = (ImageButton) findViewById(R.id.button_generateReport_recordAudio);
        btnRecordAudio.setOnClickListener(this);

        ImageButton btnAddComment = (ImageButton) findViewById(R.id.button_generateReport_addComment);
        btnAddComment.setOnClickListener(this);

        ImageButton btnAttachFile = (ImageButton) findViewById(R.id.button_generateReport_addAtachment);
        btnAttachFile.setOnClickListener(this);

        vgExtras = (LinearLayout) findViewById(R.id.layout_generateReport_extras);

        _locationService = new LocationService(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_generateReport:
                generateReport();
                break;
            case R.id.button_generateReport_photo:
                takePicture();
                break;
            case R.id.button_generateReport_recordAudio:
                generateRecordAudioFragment();
                break;
            case R.id.button_generateReport_addComment:
                generateCommentFragment();
                break;
            case R.id.button_generateReport_addAtachment:
                selectFile();
                break;
        }
    }

    private void generateReport() {
        if (!PermissionChecker.checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        ILocationService.Location location = _locationService.getLocation();
        if (location == null) {
            location = new ILocationService.Location();
        }

        Category category = (Category) spinner.getSelectedItem();

        Report report = new Report();
        report.setCategoryId(category.getId());
        report.setLatitude(location.getLatitude());
        report.setLongitude(location.getLongitude());
        report.setUserId(_loginProvider.getCurrentUserId());

        Collection<Image> images = report.getImages();
        for (byte[] bytes : _bitmapManager.getByteArrays()) {
            images.add(new Image(bytes));
        }

        Collection<Voicenote> voicenotes = report.getVoicenotes();
        for (byte[] bytes : _soundManager.getByteArrays()) {
            voicenotes.add(new Voicenote(bytes));
        }

        Collection<UploadedFile> files = report.getFiles();
        for (String uriPath : _fileManager.getStrings()) {
            try {
                Uri uri = Uri.parse(uriPath);
                String path = UriPathFinder.getPath(this, uri);
                files.add(new UploadedFile(FileNameFinder.getName(path), _fileReader.readFile(this, uri)));
            } catch (IOException e) {
                Log.e("GenerateReport", e.toString());
            }
        }

        Collection<Comment> comments = report.getComments();
        for (String comment : _commentManager.getStrings()) {
            comments.add(new Comment(comment));
        }

        report.log();

        _db.addReport(report);
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void selectFile() {
        if (!PermissionChecker.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        }

        Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        fileIntent.setType("*/*");
        fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        fileIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        try {
            startActivityForResult(fileIntent, PICK_FILE_RESULT_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No hay aplicaciones para seleccionar archivos.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            generatePhotoFragment(bitmap);
        }
        else if (requestCode == PICK_FILE_RESULT_CODE && resultCode == RESULT_OK) {
            String filePath = data.getData().toString();
            generateFileFragment(filePath);
        }
    }

    private void generatePhotoFragment(Bitmap bitmap){
        android.app.FragmentManager manager = getFragmentManager();
        manager.beginTransaction().add(vgExtras.getId(), TakePhotoFragment.newInstance(bitmap), "photo").commit();
    }

    private void generateRecordAudioFragment() {
        android.app.FragmentManager manager = getFragmentManager();
        manager.beginTransaction().add(vgExtras.getId(), AudioRecordFragment.newInstance(), "audio").commit();
    }

    private void generateFileFragment(String filePath) {
        android.app.FragmentManager manager = getFragmentManager();
        manager.beginTransaction().add(vgExtras.getId(), SelectFileFragment.newInstance(filePath), "file").commit();
    }

    private void generateCommentFragment() {
        android.app.FragmentManager manager = getFragmentManager();
        manager.beginTransaction().add(vgExtras.getId(), AddCommentFragment.newInstance(), "comment").commit();
    }

    @Override
    public Object getComponent(Class<?> $class, int code) {
        if ($class == IStringManager.class) {
            switch (code) {
                case PATH_CONTAINER:
                    return _fileManager;
                case COMMENT_CONTAINER:
                    return _commentManager;
            }
            return null;
        }
        if ($class == IByteArrayManager.class) {
            switch(code){
                case BITMAP_CONTAINER:
                    return _bitmapManager;
                case AUDIO_CONTAINER:
                    return  _soundManager;
            }
            return null;
        }
        return null;
    }
}
