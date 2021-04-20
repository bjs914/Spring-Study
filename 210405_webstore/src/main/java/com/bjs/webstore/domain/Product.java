package com.bjs.webstore.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import com.bjs.webstore.util.ValueFormat;
import com.bjs.webstore.validator.ProductCategory;
import com.bjs.webstore.validator.ProductId;


@XmlRootElement
public class Product implements Serializable {	//이 클래스에 serialize 가 필드로 붙음
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Pattern(regexp="P[1-9]+", message="{Pattern.Product.productId.validation}")
	@ProductId
	private String productId;
	@Size(min=4, max=50, message="{Size.Product.name.validation}")
	private String name;
	@Min(value=0, message="{Min.Product.unitPrice.validation}")
	@Digits(integer=8, fraction=2, message="{Digits.Product.unitPrice.validation}")
	@NotNull(message= "{NotNull.Product.unitPrice.validation}")
	private BigDecimal unitPrice;
	private String unitPriceStr;

	private String description;
	private String manufacturer;
	@Pattern(regexp="[a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]+",
			message="{Pattern.Product.category.validation}")
	@NotNull(message= "{NotNull.Product.catogory.validation}")
	@ProductCategory
	private String category;
	@Digits(integer=8, fraction=0,message="{Digits.Product.unitsInStock.validation}")
	@Min(value=0, message="{Min.Product.unitsInStock.validation}")
	@NotNull(message="{NotNull.Product.unitsInStock.validation}")
	private Long unitsInStock;
	private String unitsInStockStr;
	private long unitsInOrder;
	private boolean discontinued;
	private String condition;
	
	@JsonIgnore
	private MultipartFile productImage;
	
	@JsonIgnore
	private MultipartFile productManual;
	
	public Product() {
		super();
	}

	public Product(String productId, String name, int unitPrice, long unitsInStock) {
		this.productId = productId;
		this.name = name;
		this.unitPrice = new BigDecimal(unitPrice);
		this.unitsInStock = unitsInStock;
	} //


	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnitPriceStr() {
		return unitPriceStr;
	}

	public void setUnitPriceStr(String unitPriceStr) {
		this.unitPriceStr = unitPriceStr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getUnitsInStock() {
		return unitsInStock;
	}

	public void setUnitsInStock(Long unitsInStock) {
		this.unitsInStock = unitsInStock;
		this.unitsInStockStr =
				ValueFormat.format(unitsInStock,
				ValueFormat.COMMAS);
	}
	
	public Object getUnitsInStockStr() {
		return unitsInStockStr;
	}

	public long getUnitsInOrder() {
		return unitsInOrder;
	}

	public void setUnitsInOrder(long unitsInOrder) {
		this.unitsInOrder = unitsInOrder;
	}

	public boolean isDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(boolean discontinued) {
		this.discontinued = discontinued;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	@XmlTransient
	public MultipartFile getProductImage() {
		return productImage;
	}

	public void setProductImage(MultipartFile productImage) {
		this.productImage = productImage;
	}
	@XmlTransient
	public MultipartFile getProductManual() {
		return productManual;
	}

	public void setProductManual(MultipartFile productManual) {
		this.productManual = productManual;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
		if (unitPrice == null)
			unitPrice = new BigDecimal(0);
		DecimalFormat df = new DecimalFormat("#,###");
		this.unitPriceStr = df.format(unitPrice);
	} // https://stackoverflow.com/questions/26101918/

	@Override
	public boolean equals(Object obj) {	//
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {	//
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}
}
