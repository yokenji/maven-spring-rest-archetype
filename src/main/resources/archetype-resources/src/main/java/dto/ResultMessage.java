package ${package}.dto;

import java.util.List;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 * 
 * T - the type of which the resultMessage consists.
 */
public class ResultMessage<T> {

  private Integer currentPage;
  private Integer totalPages;
  private List<T> results;
  

  /**
   * Default constructor.
   * 
   * @param Integer currentPage
   * @param Integer totalPages
   * @param List<T> results
   */
  public ResultMessage(Integer currentPage, Integer totalPages, List<T> results) {
    this.currentPage = currentPage;
    this.totalPages = totalPages;
    this.results = results;
  }

  /**
   * Get the currentPage
   *
   * @return Integer
   */
  public Integer getCurrentPage() {
    return currentPage;
  }

  /**
   * Set the currentPage 
   *
   * @param Integer currentPage
   */
  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  /**
   * Get the totalPages
   *
   * @return Integer
   */
  public Integer getTotalPages() {
    return totalPages;
  }

  /**
   * Set the totalPages 
   *
   * @param Integer totalPages
   */
  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  /**
   * Get the results
   *
   * @return List<T>
   */
  public List<T> getResults() {
    return results;
  }

  /**
   * Set the results 
   *
   * @param List<T> results
   */
  public void setResults(List<T> results) {
    this.results = results;
  }

}
