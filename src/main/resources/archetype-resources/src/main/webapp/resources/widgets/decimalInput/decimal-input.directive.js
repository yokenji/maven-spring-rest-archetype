(function() {
  'use strict';

  angular
    .module('decimal-input.module', [])
    .directive('decimalInput', decimalInput);

  function decimalInput() {
    var directive = {
        require: 'ngModel',
        link: link
    };
    return directive;

    function link(scope, element, attr, ngModelCtrl) {
      function fromUser(text) {
        var transformedInput = text.replace(/[^0-9\.,]/g, ''); 
        transformedInput = transformedInput.replace(',', '.');

        if (transformedInput.split('.').length > 2 || transformedInput.startsWith('.')){
          transformedInput = transformedInput.replaceAt(transformedInput.lastIndexOf('.'),'');
        }

        if(transformedInput !== text) {
            ngModelCtrl.$setViewValue(transformedInput);
            ngModelCtrl.$render();
        }
        return transformedInput;
      }
      ngModelCtrl.$parsers.push(fromUser);
    }
  }
}());