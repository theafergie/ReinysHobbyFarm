package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class InventoryCount
{
    @Id
    private int categoryId;
    private String label;
    private long count;

    public InventoryCount(int categoryId, String label, long count)
    {
        this.categoryId = categoryId;
        this.label = label;
        this.count = count;
    }

    public String getLabel()
    {
        return label;
    }

    public long getCount()
    {
        return count;
    }
}
