package itesm.mx.movilidad_reportedeproblemas.Services.IKMeans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juanc on 11/16/2017.
 */

public interface IKMeans {
    void solve(int amountMeans, List<Point> points, OnKmeansFinishedListener handler);

    class Point{
        public double x;
        public double y;
        public Cluster cluster;
        public double distance;
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    class Cluster {
        public double x;
        public double y;
        public ArrayList<Point> points = new ArrayList<>();
        public Cluster(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    interface OnKmeansFinishedListener {
        void onKmeansFinished(ArrayList<Cluster> points);
    }
}
