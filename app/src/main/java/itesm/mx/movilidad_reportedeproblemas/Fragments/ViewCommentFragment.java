package itesm.mx.movilidad_reportedeproblemas.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import itesm.mx.movilidad_reportedeproblemas.Models.Comment;
import itesm.mx.movilidad_reportedeproblemas.R;

public class ViewCommentFragment extends android.app.Fragment {
    private static final String ARG_COMMENT = "comment";

    private Comment _comment;

    public ViewCommentFragment() { }

    public static ViewCommentFragment newInstance(Comment comment) {
        ViewCommentFragment fragment = new ViewCommentFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_COMMENT, comment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _comment = getArguments().getParcelable(ARG_COMMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_view_comment, container, false);

        String body = _comment == null ? "ERROR" : _comment.getBody();
        TextView tvBody = view.findViewById(R.id.text_viewComment_body);
        tvBody.setText(body);

        return view;
    }
}
