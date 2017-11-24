package itesm.mx.movilidad_reportedeproblemas.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import itesm.mx.movilidad_reportedeproblemas.Models.Category;
import itesm.mx.movilidad_reportedeproblemas.R;

//////////////////////////////////////////////////////////
//Clase: CategoryAdapter
// Descripción: Adaptador para las categorias.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////

public class CategoryAdapter extends ArrayAdapter<Category> {
    public CategoryAdapter(@NonNull Context context, @NonNull List<Category> categories) {
        super(context, 0, categories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getDropDownView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Category category = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_spinner_row, parent, false);

        TextView tvName = convertView.findViewById(R.id.text_categorySpinner_name);
        tvName.setText(category.getName());

        return convertView;
    }
}
