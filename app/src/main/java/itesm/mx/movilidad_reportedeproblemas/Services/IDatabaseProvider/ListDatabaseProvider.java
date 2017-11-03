package itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import itesm.mx.movilidad_reportedeproblemas.Models.Category;
import itesm.mx.movilidad_reportedeproblemas.Models.Report;

public class ListDatabaseProvider implements IDatabaseProvider {
    private static ListDatabaseProvider _instance = new ListDatabaseProvider();

    public static ListDatabaseProvider getInstance() {
        return _instance;
    }

    private ListDatabaseProvider() {}

    private ArrayList<Report> reports = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>(Arrays.asList(new Category[] {new Category("Categoria 0"), new Category("Categoria 1")}));

    private long _nextReportId;
    private long _nextCategoryId;

    @Override
    public Report getReport(long id) {
        for (Report report : reports){
            if (report.getId() == id)
                return report;
        }
        return null;
    }

    @Override
    public ArrayList<Report> getReports() {
        return reports;
    }

    @Override
    public boolean deleteReport(long id) {
        for (Report report : reports){
            if (report.getId() == id) {
                return reports.remove(report);
            }
        }
        return false;
    }

    @Override
    public long addReport(Report report) {
        reports.add(report);
        report.setId(_nextReportId++);
        return report.getId();
    }

    @Override
    public Category getCategory(long id) {
        for (Category category : categories){
            if (category.getId() == id)
                return category;
        }
        return null;
    }

    @Override
    public ArrayList<Category> getCategories() {
        return categories;
    }

    @Override
    public boolean deleteCategory(long id) {
        for (Category category : categories){
            if (category.getId() == id)
                return categories.remove(category);
        }
        return false;
    }

    @Override
    public long addCategory(Category cateogry) {
        categories.add(cateogry);
        cateogry.setId(_nextCategoryId++);
        return cateogry.getId();
    }

    @Override
    public ArrayList<Report> getReportsForUser(final String userId) {
        ArrayList<Report> selected = new ArrayList<>();
        for (Report report : reports) {
            if (Objects.equals(report.getUserId(), userId))
                selected.add(report);
        }
        return selected;
    }

    @Override
    public Report getPopulatedReport(long reportId) {
        for (Report report : reports){
            if (report.getId() == reportId)
                return report;
        }
        return null;
    }

    boolean admin = true;
    @Override
    public boolean isAdmin(String userId) {
        admin = !admin;
        return admin;
    }
}
