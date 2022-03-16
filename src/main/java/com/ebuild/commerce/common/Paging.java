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
  private int totalCount;
  private boolean hasNext;
  private boolean hasPrev;
  private int lastPage;
  private int firstPage;

  @Builder
  public Paging(int page, int size, int range, int totalCount, boolean hasNext, boolean hasPrev,
      int lastPage, int firstPage) {
    this.page = page;
    this.size = size;
    this.range = range;
    this.totalCount = totalCount;
    this.hasNext = hasNext;
    this.hasPrev = hasPrev;
    this.lastPage = lastPage;
    this.firstPage = firstPage;
  }

  public static Paging of(int page, int size, int range, int totalCount){
    return Paging.builder()
        .page(page)
        .range(range)
        .size(size)
        .totalCount(totalCount)
        .build();
  }

  public int offset(){
    return (page - 1) * size + 1;
  }

  public boolean isHasNext() {
    throwTotalCountException();
    return (totalCount / size) > (firstPage + range);
  }

  public boolean isHasPrev() {
    throwTotalCountException();
    return (totalCount / size) < (firstPage);
  }

  public int getLastPage() {
    throwTotalCountException();
    return isHasNext() ? this.firstPage + range : page;
  }

  public int getFirstPage() {
    return ((page - 1) / range) * range + 1;
  }

  private void throwTotalCountException(){
    if( this.totalCount == 0 )
      throw new PagingDoNotHaveTotalCountException();
  }

}
