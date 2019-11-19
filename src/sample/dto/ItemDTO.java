package sample.dto;

public class ItemDTO {

    private String code;
    private String description;
    private int quantityOnHand;
    private double unitPrice;

    public ItemDTO() {
    }

    public ItemDTO(String code, String description, int quantityOnHand, double unitPrice) {
        this.code = code;
        this.description = description;
        this.quantityOnHand = quantityOnHand;
        this.unitPrice = unitPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", quantityOnHand=" + quantityOnHand +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
