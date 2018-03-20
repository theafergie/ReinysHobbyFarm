package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Product
{
    @Id
    private int productId;
    private String productName;
    private BigDecimal price;
    private boolean vegan;
    private int quantityInStock;
    private int maxPreOrder;
    private int categoryId;
    private int seasonId;
    private String size;
    private String description;
    private String ingredients;

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public boolean isVegan()
    {
        return vegan;
    }

    public void setVegan(boolean vegan)
    {
        this.vegan = vegan;
    }

    public int getQuantityInStock()
    {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock)
    {
        this.quantityInStock = quantityInStock;
    }

    public int getMaxPreOrder()
    {
        return maxPreOrder;
    }

    public void setMaxPreOrder(int maxPreOrder)
    {
        this.maxPreOrder = maxPreOrder;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    public int getSeasonId()
    {
        return seasonId;
    }

    public void setSeasonId(int seasonId)
    {
        this.seasonId = seasonId;
    }

    public String getSize()
    {
        return size;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public String getDescription()
    {
        return description;
    }

    public String getIngredients()
    {
        return ingredients;
    }
}
