package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BestSellers
{
    @Id
    private int productId;
    private String label;
    private long count;

    public BestSellers(int productId, String label, long count)
    {
        this.productId = productId;
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
