(function() {
  'use strict';

  angular
    .module('password-popover.module')
    .factory('passwordService', passwordService);

  function passwordService() {
    return {
      isValid: isValid
    };

    function isValid(value) {
      var patternOneNumber = /(\d+)/;
      var patternOnUpperLower = /((?=.*[a-z])(?=.*[A-Z]))/;
      var patternLength = /.{6,20}$/;

      if (value == undefined)
        value = '';

      return {
        'min_six_characters' : patternLength.test(value),
        'one_upper_lower' : patternOnUpperLower.test(value),
        'one_number' : patternOneNumber.test(value)
      };
    }
  }
})();