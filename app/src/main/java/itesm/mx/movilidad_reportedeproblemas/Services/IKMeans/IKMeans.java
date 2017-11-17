package itesm.mx.movilidad_reportedeproblemas.Services.IKMeans;

import java.util.ArrayList;

/**
 * Created by juanc on 11/16/2017.
 */

public interface IKMeans {
    void solve(int amountMeans, ArrayList<Point> points, IKMeansHandler handler);

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

    interface IKMeansHandler {
        void handle(ArrayList<Cluster> points);
    }
}
