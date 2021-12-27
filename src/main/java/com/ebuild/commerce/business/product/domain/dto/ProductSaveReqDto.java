package com.ebuild.commerce.business.product.domain.dto;

import com.ebuild.commerce.business.product.domain.common.ProductCategory;
import com.ebuild.commerce.business.product.domain.common.ProductStatus;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.common.Enum;
import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ProductSaveReqDto {

  @Valid
  private ProductSaveParam product;

  @Getter
  public static class ProductSaveParam {

    private Long id;

    @NotBlank(message = "회사번호는 필수 입력 값입니다.")
    private Long companyId;

    @Size(min = 1, max = 16)
    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9]*$",
        message = "상품명은 한글/영문/숫자 포함 1~16의 글자여야 합니다.")
    private String name;

    @NotBlank(message = "상품 상태는 필수 입력 값입니다.")
    @Enum(enumClass = ProductStatus.class, ignoreCase = true, message = "SALE, STOP 값 중 하나를 입력해주시기 바랍니다.")
    private String productStatus;

    @Min(value = 100, message = "정가는 100원 이상의 값을 입력해주시기 바랍니다.")
    private Integer normalAmount;

    @Min(value = 100, message = "판매는 100원 이상의 값을 입력해주시기 바랍니다.")
    private Integer saleAmount;

    @NotBlank(message = "상품 카테고리는 필수 입력 값입니다.")
    @Enum(enumClass = ProductCategory.class, ignoreCase = true, message = "BOOK, CLOTH 값 중 하나를 입력해주시기 바랍니다.")
    private String category;

    private LocalDate saleStartDate;

    private LocalDate saleEndDate;

    private Integer quantity;

  }

  public Product toEntity() {
    return Product.builder()
        .id(product.id)
        .name(product.name)
        .productStatus(ProductStatus.fromValue(product.productStatus))
        .category(ProductCategory.fromValue(product.category))
        .normalAmount(product.normalAmount)
        .saleAmount(product.saleAmount)
        .saleStartDate(product.saleStartDate)
        .saleEndDate(product.saleEndDate)
        .quantity(product.quantity)
        .build();
  }

}
