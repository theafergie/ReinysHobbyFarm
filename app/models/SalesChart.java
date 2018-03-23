package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class SalesChart
{
    @Id
    private int year;
    private double total;


    public SalesChart(int year, double total)
    {
        this.year = year;
        this.total = total;
    }

    public int getLabel()
    {
        return year;
    }

    public double getCount()
    {
        return total;
    }
}
