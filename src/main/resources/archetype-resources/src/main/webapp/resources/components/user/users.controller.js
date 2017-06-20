(function() {
  'use strict';

  angular
    .module('user.module')
    .controller('usersController', usersController);

  usersController.$inject = ['$window'];

  function usersController($window) {
    var vm = this;
    vm.searchFilters = $window.searchFilters;

  }
})();