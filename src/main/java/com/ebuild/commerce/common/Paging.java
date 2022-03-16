package com.ebuild.commerce.common;

import com.ebuild.commerce.exception.PagingDoNotHaveTotalCountException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Paging {

  private int page;
  private int size = 10;
  private int range = 5;
  private long totalCount = -1;

  @Builder
  public Paging(int page, int size, int range, int totalCount) {
    this.page = page;
    this.size = size;
    this.range = range;
    this.totalCount = totalCount;
  }

  public static Paging of(int page, int size, int range, int totalCount) {
    return Paging.builder()
        .page(page)
        .range(range)
        .size(size)
        .totalCount(totalCount)
        .build();
  }

  public int offset() {
    return (page - 1) * size + 1;
  }

  public boolean isHasNext() {
    throwTotalCountException();
    return (totalCount / size) > (getFirstPage() + range - 1);
  }

  public boolean isHasPrev() {
    throwTotalCountException();
    return getFirstPage() != 1;
  }

  public int getLastPage() {
    throwTotalCountException();
    return isHasNext() ? getFirstPage() + range - 1 : (int) (totalCount / size) + 1;
  }

  public int getFirstPage() {
    return ((page - 1) / range) * range + 1;
  }

  private void throwTotalCountException() {
    if (this.totalCount == -1) {
      throw new PagingDoNotHaveTotalCountException();
    }
  }

}
