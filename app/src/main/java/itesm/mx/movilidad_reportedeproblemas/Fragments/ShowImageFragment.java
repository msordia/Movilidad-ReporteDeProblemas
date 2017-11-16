package itesm.mx.movilidad_reportedeproblemas.Fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import itesm.mx.movilidad_reportedeproblemas.R;

public class ShowImageFragment extends android.app.Fragment {
    private static final String ARG_BYTES = "bytes";

    private byte[] _bytes;

    public ShowImageFragment() {
        // Required empty public constructor
    }

    public static ShowImageFragment newInstance(byte[] bytes) {
        ShowImageFragment fragment = new ShowImageFragment();
        Bundle args = new Bundle();
        args.putByteArray(ARG_BYTES, bytes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _bytes = getArguments().getByteArray(ARG_BYTES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_show_image, container, false);

        ImageView iv = view.findViewById(R.id.image_showImge);
        iv.setImageBitmap(BitmapFactory.decodeByteArray(_bytes, 0, _bytes.length));

        return view;
    }
}
