package itesm.mx.movilidad_reportedeproblemas.Models;

/**
 * Created by juanc on 10/31/2017.
 */

public class Report {
    private long id;
    private long categoryId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
