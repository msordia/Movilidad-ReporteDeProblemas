package itesm.mx.movilidad_reportedeproblemas.Services;

import java.util.List;

import itesm.mx.movilidad_reportedeproblemas.Models.Category;
import itesm.mx.movilidad_reportedeproblemas.Models.Report;

/**
 * Created by juanc on 10/31/2017.
 */

public interface IDatabaseProvider {
    Report getReport(long id);
    List<Report> getReports();
    boolean deleteReport(long id);
    long addReport(Report report);

    Category getCategory(long id);
    List<Category> getCategories();
    boolean deleteCategory(long id);
    long addCategory(Category cateogry);
}
