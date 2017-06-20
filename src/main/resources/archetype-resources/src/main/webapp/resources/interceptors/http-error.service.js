(function () {
  'use strict';

  angular
    .module('httpError.module', [])
    .factory('httpErrorInterceptorService', httpErrorInterceptorService)
    .config(loadInterceptor);

  httpErrorInterceptorService.$inject = ['$log', '$q'];

  function httpErrorInterceptorService($log, $q) {
    return {
      request: getRequest,
      response: getResponse,
      responseError: getResponseError
    };

    function getRequest(config) {
      return config;
    }

    function getResponse(response) {
      return response;
    }

    function getResponseError(rejection) {
      $log.error("@ An error has occurred.\nHTTP error: " + rejection.status + " (" + rejection.statusText + ")")
      if (rejection.status <= 0) {
        window.location = "noresponse.html";
        return;
      }
      return $q.reject(rejection);
    }
  }

  function loadInterceptor($httpProvider) {
    $httpProvider.interceptors.push('httpErrorInterceptorService')
  }

}());