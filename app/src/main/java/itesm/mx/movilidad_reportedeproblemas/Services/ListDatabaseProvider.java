package itesm.mx.movilidad_reportedeproblemas.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import itesm.mx.movilidad_reportedeproblemas.Models.Category;
import itesm.mx.movilidad_reportedeproblemas.Models.Report;

/**
 * Created by juanc on 10/31/2017.
 */

public class ListDatabaseProvider implements IDatabaseProvider {
    private List<Report> reports = new ArrayList<>();
    private List<Category> categories = Arrays.asList(new Category[]{new Category("Categoria 0"), new Category("Categoria 1")});

    @Override
    public Report getReport(long id) {
        for (Report report : reports){
            if (report.getId() == id)
                return report;
        }
        return null;
    }

    @Override
    public List<Report> getReports() {
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
        return 0;
    }

    @Override
    public Category getCategory(String name) {
        for (Category category : categories){
            if (category.getName().equals(name))
                return category;
        }
        return null;
    }

    @Override
    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public boolean deleteCategory(String name) {
        for (Category category : categories){
            if (category.getName().equals(name))
                return categories.remove(category);
        }
        return false;
    }

    @Override
    public String addCategory(Category cateogry) {
        categories.add(cateogry);
        return cateogry.getName();
    }
}
