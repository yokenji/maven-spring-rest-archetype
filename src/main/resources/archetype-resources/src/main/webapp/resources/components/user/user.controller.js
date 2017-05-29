(function () {
  'use strict';

  angular
    .module('user.module')
    .controller('userController', userController);

  userController.$inject = ['$window', 'EnvironmentConfig', 'passwordService'];

  function userController($window, EnvironmentConfig, passwordService) {
    var vm = this;
    vm.password = '';

    vm.genPass = function () {
      vm.password = generatePassword(12, false);
      vm.validate(vm.password);
    };

    vm.cancel = function () {
      var url = EnvironmentConfig.api + '/users';
      $window.location.href = url;
    };

    vm.min_six_characters = false;
    vm.one_upper_lower = false;
    vm.one_number = false;

    vm.validate = function (value) {
      var validators = passwordService.isValid(value);

      vm.min_six_characters = validators.min_six_characters;
      vm.one_upper_lower = validators.one_upper_lower;
      vm.one_number = validators.one_number;
    };
  }
}());
