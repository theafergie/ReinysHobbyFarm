package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class OrderDetail
{
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int orderProductId;
    private int orderId;
    private int productId;
    private BigDecimal unitPrice;
    private int quantity;
    //private String productName;
    private int customerId;
    private int categoryId;

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public int getOrderProductId()
    {
        return orderProductId;
    }

    public void setOrderProductId(int orderProductId)
    {
        this.orderProductId = orderProductId;
    }

//    public String getProductName()
//    {
//        return productName;
//    }
//
//    public void setProductName(String productName)
//    {
//        this.productName = productName;
//    }

    public int getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }
}
