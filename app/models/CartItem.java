package models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;

public class CartItem
{
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int productId;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
    private int categortyId;

    public int getProductId()
    {
        return productId;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public void setUnitPrice(BigDecimal unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public int getCategortyId()
    {
        return categortyId;
    }

    public void setCategortyId(int categortyId)
    {
        this.categortyId = categortyId;
    }

    public static String toJSON (List<CartItem> cartItems)
    {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;

        try
        {
            json = mapper.writeValueAsString(cartItems);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return json;
    }

    public static List<CartItem> fromJSON(String json)
    {
        ObjectMapper mapper = new ObjectMapper();

        List<CartItem> cartItems = null;

        try
        {
            cartItems = mapper.readValue(json, new TypeReference<List<CartItem>>(){});
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return cartItems;
    }
}
