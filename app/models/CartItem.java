package models;

import java.math.BigDecimal;

public class CartItem
{
    private int productId;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;

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
}
