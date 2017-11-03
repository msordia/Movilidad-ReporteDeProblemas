package itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider;

import java.util.ArrayList;
import java.util.List;

import itesm.mx.movilidad_reportedeproblemas.Models.Category;
import itesm.mx.movilidad_reportedeproblemas.Models.Report;

/**
 * Created by juanc on 10/31/2017.
 */

public interface IDatabaseProvider {
    Report getReport(long id);
    ArrayList<Report> getReports();
    boolean deleteReport(long id);
    long addReport(Report report);

    Category getCategory(long id);
    ArrayList<Category> getCategories();
    boolean deleteCategory(long id);
    long addCategory(Category cateogry);

    ArrayList<Report> getReportsForUser(String userId);
    Report getPopulatedReport(long reportId);

    boolean isAdmin(String userId);
}
