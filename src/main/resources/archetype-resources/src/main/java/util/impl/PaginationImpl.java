package ${package}.util.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import ${package}.util.Pagination;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Service
public class PaginationImpl implements Pagination {

  private static final Logger log = LogManager.getLogger(PaginationImpl.class);

  /* (non-Javadoc)
   * @see ${package}.service.util.Pagination\#start(java.lang.Integer, java.lang.Integer)
   */
  @Override
  public int start(Integer currentPage, Integer toDisplay) {
    log.debug("Calculate the start page: currentPage: " + currentPage + ", TotalItems to Display: " + toDisplay);
    return (currentPage == null) ? 0 : (currentPage -1) * (toDisplay == null ? 0 : toDisplay);
  }

  /* (non-Javadoc)
   * @see ${package}.service.util.Pagination\#maxPages(java.lang.Integer, java.lang.Long)
   */
  @Override
  public int maxPages(Integer toDisplay, Long totalItems) {
    log.debug("Calculate the total pages: TotalItems To Display: " + toDisplay + ", TotalItems: " + totalItems);
    float nrOfPages = (float)( (totalItems == null ? 0.0 : (float)totalItems) / (toDisplay == null ? 0.0 : (float)toDisplay));
    int total = (int) ( ((nrOfPages > (int)nrOfPages) || nrOfPages == 0.0) ? (nrOfPages + 1) : nrOfPages );
    return total;
  }

}
