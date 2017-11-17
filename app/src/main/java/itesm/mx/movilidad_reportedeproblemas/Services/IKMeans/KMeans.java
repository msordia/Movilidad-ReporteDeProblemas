package itesm.mx.movilidad_reportedeproblemas.Services.IKMeans;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by juanc on 11/16/2017.
 */

public class KMeans implements IKMeans {
    private final static Random _rand = new Random();

    @Override
    public void solve(int amountMeans, ArrayList<Point> points, IKMeansHandler handler) {
        KMeansTask.Params params = new KMeansTask.Params();
        params.amountMeans = amountMeans;
        params.points = points;
        params.handler = handler;
        new KMeansTask().execute(params);
    }

    static class KMeansTask extends AsyncTask<KMeansTask.Params, ArrayList<Cluster>, ArrayList<Cluster>> {
        private Params _params;
        @Override
        protected ArrayList<Cluster> doInBackground(Params... params) {
            _params = params[0];
            IKMeansHandler handler = params[0].handler;
            int amountMeans = params[0].amountMeans;
            ArrayList<Point> points = params[0].points;

            double minX = Double.MAX_VALUE;
            double maxX = Double.MIN_VALUE;
            double minY = minX;
            double maxY = maxX;
            for (Point point : points) {
                if (point.x < minX) minX = point.x;
                if (point.x > maxX) maxX = point.x;
                if (point.y < minY) minY = point.y;
                if (point.y > maxY) maxY = point.y;
            }
            ArrayList<Cluster> clusters = new ArrayList<>();
            for (int i = 0; i < amountMeans; i++) {
                double x = _rand.nextDouble() * (maxX - minX) + minX;
                double y = _rand.nextDouble() * (maxY - minY) + minY;
                clusters.add(new Cluster(x, y));
            }

            boolean changed;
            do {
                changed = false;
                for (Point point : points) {
                    double minDistance = Double.MAX_VALUE;
                    Cluster closestCluster = null;
                    for (Cluster cluster : clusters) {
                        double distance = Math.sqrt(Math.pow(point.x-cluster.x,2) + Math.pow(point.y - cluster.y,2));
                        if (distance < minDistance) {
                            minDistance = distance;
                            closestCluster = cluster;
                        }
                    }
                    point.distance = minDistance;
                    if (point.cluster == null || point.cluster != closestCluster)
                    {
                        changed = true;
                        if (point.cluster != null)
                            point.cluster.points.remove(point);

                        closestCluster.points.add(point);
                        point.cluster = closestCluster;
                    }
                }

                for (Cluster cluster : clusters) {
                    int amount = cluster.points.size();

                    double sumX = 0;
                    double sumY = 0;
                    for (Point point : cluster.points) {
                        sumX += point.x;
                        sumY += point.y;
                    }
                    double newX = amount == 0 ? 0 : sumX / amount;
                    double newY = amount == 0 ? 0 : sumY / amount;
                    cluster.x = newX;
                    cluster.y = newY;
                }

                for (Cluster cluster : clusters) {
                    if (cluster.points.size() == 0) {
                        Cluster biggestCluster = cluster;
                        for (Cluster c : clusters) {
                            if (biggestCluster.points.size() < c.points.size()) {
                                biggestCluster = c;
                            }
                        }

                        Point farthestPoint = biggestCluster.points.get(0);
                        for (Point point : biggestCluster.points) {
                            if (point.distance > farthestPoint.distance)
                                farthestPoint = point;
                        }

                        farthestPoint.cluster = cluster;
                        biggestCluster.points.remove(farthestPoint);
                        cluster.points.add(farthestPoint);
                        cluster.x = farthestPoint.x;
                        cluster.y = farthestPoint.y;
                        changed = true;
                    }
                }

                publishProgress(clusters);
            } while (changed);

            return clusters;
        }

        @Override
        protected void onProgressUpdate(ArrayList<Cluster>... values) {
            super.onProgressUpdate(values);
            //_params.handler.handle(values[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Cluster> clusters) {
            super.onPostExecute(clusters);
            _params.handler.handle(clusters);
        }

        static class Params {
            public int amountMeans;
            public ArrayList<Point> points;
            public IKMeansHandler handler;
        }
    }
}
