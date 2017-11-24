package itesm.mx.movilidad_reportedeproblemas.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.Collection;

import itesm.mx.movilidad_reportedeproblemas.R;
import itesm.mx.movilidad_reportedeproblemas.Services.Tuple;

//////////////////////////////////////////////////////////
//Clase: PieChartFragment
// Descripción: Fragmento para visualizar las graficas.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////

public class PieChartFragment extends Fragment {
    private static final String ARG_VALUES = "values";
    private static final String ARG_NAMES = "names";
    private static final String ARG_COLORS = "colors";

    private double[] _values;
    private String[] _names;
    private int[] _colors;

    private GraphicalView _chart;
    private DefaultRenderer _renderer = new DefaultRenderer();
    private CategorySeries _series = new CategorySeries("");

    public PieChartFragment() { }

    public static PieChartFragment newInstance(Collection<Tuple<Tuple<String,Double>, Integer>> pairs) {
        PieChartFragment fragment = new PieChartFragment();
        Bundle args = new Bundle();

        String[] names = new String[pairs.size()];
        double[] values = new double[pairs.size()];
        int[] colors = new int[pairs.size()];
        int i = 0;
        for (Tuple<Tuple<String, Double>, Integer> pair : pairs) {
            names[i] = pair.x.x;
            values[i] = pair.x.y;
            colors[i] = pair.y;
            i++;
        }
        args.putDoubleArray(ARG_VALUES, values);
        args.putStringArray(ARG_NAMES, names);
        args.putIntArray(ARG_COLORS, colors);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _values = getArguments().getDoubleArray(ARG_VALUES);
            _names = getArguments().getStringArray(ARG_NAMES);
            _colors = getArguments().getIntArray(ARG_COLORS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);

        _renderer.setApplyBackgroundColor(true);
        _renderer.setBackgroundColor(Color.TRANSPARENT);
        _renderer.setChartTitleTextSize(20);
        _renderer.setLabelsTextSize(30);
        _renderer.setLabelsColor(Color.BLACK);
        _renderer.setStartAngle(90);
        _renderer.setInScroll(false);
        _renderer.setShowLegend(false);

        for (int i = 0; i < _values.length; i++) {
            _series.add(_names[i] + ": " + _values[i], _values[i]);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(_colors[i]);
            _renderer.addSeriesRenderer(renderer);
        }

        if (_chart != null) {
            _chart.repaint();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (_chart == null) {
            _chart = ChartFactory.getPieChartView(getActivity(), _series, _renderer);
            _renderer.setClickEnabled(true);
            _renderer.setSelectableBuffer(10);
            LinearLayout layout = getView().findViewById(R.id.layout_chart);
            layout.addView(_chart, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        else {
            _chart.repaint();
        }
    }

}
