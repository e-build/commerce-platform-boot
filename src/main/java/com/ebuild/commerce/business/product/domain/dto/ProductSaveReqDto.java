package com.ebuild.commerce.business.product.domain.dto;

import com.ebuild.commerce.business.product.domain.entity.Category;
import com.ebuild.commerce.business.product.domain.entity.ProductStatus;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.product.repository.JpaCategoryRepository;
import com.ebuild.commerce.common.validation.Enum;
import com.ebuild.commerce.common.validation.NotEmptyCollection;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "회사번호는 필수 입력 값입니다.")
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

    @NotEmptyCollection(message = "상품 카테고리는 필수 입력 값입니다.")
    private List<Long> categoryIdList;

    private List<Category> categoryList;

    private LocalDate saleStartDate;

    private LocalDate saleEndDate;

    private Integer quantity;

  }

  public void convertCategoryEntity(JpaCategoryRepository jpaCategoryRepository){
    this.getProduct().categoryList = jpaCategoryRepository.findByIdIn(this.getProduct().getCategoryIdList());
  }

  public Product toEntity() {
    return Product.builder()
        .name(this.getProduct().getName())
        .productStatus(ProductStatus.fromValue(this.getProduct().getProductStatus()))
        .normalAmount(this.getProduct().getNormalAmount())
        .saleAmount(this.getProduct().getSaleAmount())
        .saleStartDate(this.getProduct().getSaleStartDate())
        .saleEndDate(this.getProduct().getSaleEndDate())
        .quantity(this.getProduct().getQuantity())
        .build();
  }

}
