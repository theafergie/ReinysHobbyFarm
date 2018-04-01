package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class BestSellers
{
    @Id
    private int productId;
    private String label;
    private long count;
    private BigDecimal total;

    public BestSellers(String label, BigDecimal total)
    {
        this.label = label;
        this.total = total;
    }

    public String getLabel()
    {
        return label;
    }

    public BigDecimal getCount()
    {
        return total;
    }
}
