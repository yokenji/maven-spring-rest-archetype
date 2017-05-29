(function() {
  'use strict';

  angular
    .module('numeric-input.module', [])
    .directive('numericInput', numericInput);

  function numericInput() {
    var directive = {
        require: 'ngModel',
        link: link
    };
    return directive;

    function link(scope, element, attr, ngModelCtrl) {
      function fromUser(text) {
        var transformedInput = text.replace(/[^0-9]/g, '');
        console.log(transformedInput);
        if(transformedInput !== text) {
            ngModelCtrl.$setViewValue(transformedInput);
            ngModelCtrl.$render();
        }
        return transformedInput;
      }
      ngModelCtrl.$parsers.push(fromUser);
    }

  }

})();