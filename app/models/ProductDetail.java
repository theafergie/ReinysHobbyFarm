package models;

import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

import static play.mvc.Results.notFound;

@Entity
public class ProductDetail
{
    @Id
    private int productId;
    private String productName;
    private String ingredients;
    private String size;
    private BigDecimal price;
    private int categoryId;
    private int seasonId;
    private byte[] picture;

    public ProductDetail(int productId, String productName, BigDecimal price, String ingredients, String size, int categoryId, int seasonId)
    {
        this.productId = productId;
        this.productName = productName;
        this.ingredients = ingredients;
        this.size = size;
        this.price = price;
        this.seasonId = seasonId;

    }

    public int getProductId()
    {
        return productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public String getIngredients()
    {
        return ingredients;
    }

    public String getSize()
    {
        return size;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public int getSeasonId()
    {
        return seasonId;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public byte[] getPicture()
    {
        return picture;
    }

}
