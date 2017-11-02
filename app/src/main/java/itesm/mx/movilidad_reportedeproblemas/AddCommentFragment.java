package itesm.mx.movilidad_reportedeproblemas;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import itesm.mx.movilidad_reportedeproblemas.Services.ICommentManager.IStringManager;
import itesm.mx.movilidad_reportedeproblemas.Services.IContainer;

public class AddCommentFragment extends android.app.Fragment {
    private IStringManager _commentManager;
    private EditText etComment;
    private ImageButton btnRemove;

    public AddCommentFragment() { }

    public static AddCommentFragment newInstance() {
        AddCommentFragment fragment = new AddCommentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_comment, container, false);

        etComment = view.findViewById(R.id.edit_comment);
        etComment.addTextChangedListener(new TextWatcher() {
            String lastComment;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lastComment = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String newComment = etComment.getText().toString();
                onCommentChanged(lastComment, newComment);
            }
        });

        btnRemove = view.findViewById(R.id.button_comment_cancel);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCommentDeleted(etComment.getText().toString());
                ((ViewGroup)view.getParent()).removeView(view);
            }
        });

        return view;
    }

    public void onCommentChanged(String previousComment, String newComment) {
        if (_commentManager != null) {
            _commentManager.updateString(previousComment, newComment);
        }
    }

    public void onCommentDeleted(String comment) {
        if (_commentManager != null) {
            _commentManager.removeString(comment);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IStringManager) {
            _commentManager = (IStringManager) context;
        } else if (context instanceof IContainer) {
            _commentManager = (IStringManager)((IContainer) context).getComponent(IStringManager.class, GenerateReportActivity.COMMENT_CONTAINER);
        } else {
                throw new RuntimeException(context.toString()
                        + " must implement IStringManager or ICommentManagerContainer");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _commentManager = null;
    }
}
