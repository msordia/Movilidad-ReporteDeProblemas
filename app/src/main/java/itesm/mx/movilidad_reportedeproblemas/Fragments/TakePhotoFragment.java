package itesm.mx.movilidad_reportedeproblemas.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import itesm.mx.movilidad_reportedeproblemas.Activities.GenerateReportActivity;
import itesm.mx.movilidad_reportedeproblemas.R;
import itesm.mx.movilidad_reportedeproblemas.Services.IBitmapManager.IByteArrayManager;
import itesm.mx.movilidad_reportedeproblemas.Services.IContainer;

//////////////////////////////////////////////////////////
//Clase: TakePhotoFragment
// Descripción: Fragmento para tomar fotos.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class TakePhotoFragment extends android.app.Fragment {
    private static final String ARG_BITMAP = "bitmap";

    private byte[] _bytes;
    private IByteArrayManager _byteArrayManager;

    public TakePhotoFragment() { }

    public static TakePhotoFragment newInstance(Bitmap bitmap) {
        TakePhotoFragment fragment = new TakePhotoFragment();
        Bundle args = new Bundle();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        args.putByteArray(ARG_BITMAP, byteArray);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _bytes = getArguments().getByteArray(ARG_BITMAP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_take_photo, container, false);

        if (_byteArrayManager != null) {
            _byteArrayManager.addByteArray(_bytes);
        }

        ImageView iv = view.findViewById(R.id.image_extraImage);
        iv.setImageBitmap(BitmapFactory.decodeByteArray(_bytes, 0, _bytes.length));

        TextView tv = view.findViewById(R.id.text_extraImage_dateTime);
        tv.setText(new Date().toString());

        ImageButton btn = view.findViewById(R.id.button_extraImage_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _byteArrayManager.removeByteArray(_bytes);
                ((ViewGroup)view.getParent()).removeView(view);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IByteArrayManager) {
            _byteArrayManager = (IByteArrayManager) context;
        } else if (context instanceof IContainer) {
            _byteArrayManager = (IByteArrayManager)((IContainer) context).getComponent(IByteArrayManager.class, GenerateReportActivity.BITMAP_CONTAINER);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IByteArrayManager or IByteArrayManagerContainer");
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof IByteArrayManager) {
            _byteArrayManager = (IByteArrayManager) context;
        } else if (context instanceof IContainer) {
            _byteArrayManager = (IByteArrayManager)((IContainer) context).getComponent(IByteArrayManager.class, GenerateReportActivity.BITMAP_CONTAINER);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IByteArrayManager or IByteArrayManagerContainer");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _byteArrayManager = null;
    }
}
