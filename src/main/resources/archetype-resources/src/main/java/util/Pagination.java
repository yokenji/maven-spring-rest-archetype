package ${package}.util;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public interface Pagination {

  /**
   * Calculate the start page.
   * 
   * @param Integer currentPage
   * @param Integer toDisplay
   * @return int
   */
  public int start(Integer currentPage, Integer toDisplay);

  /**
   * Calculate the total pages.
   * 
   * @param Integer toDisplay
   * @param Long totalItems
   * @return int
   */
  public int maxPages(Integer toDisplay, Long totalItems);

}
