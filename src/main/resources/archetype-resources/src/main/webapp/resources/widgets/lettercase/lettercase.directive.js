(function () {
  'use strict';

  angular
    .module('lettercase.module', [])
    .directive('toCase', toCase);

  function toCase() {
    var directive = {
      restrict: 'A',
      require: 'ngModel',
      link: link,
      scope: {
        caseType: '@caseType',
      },
    };
    return directive;

    function link(scope, element, attr, ctrl) {
      function fromUser(value) {
        if (value === undefined)
          value = '';
        var transformedInput = value;
        switch (scope.caseType) {
          case 'U':
            transformedInput = value.toUpperCase();
            break;
          case 'L':
            transformedInput = value.toUpperCase();
            break;
        }
        if (transformedInput !== value) {
          ctrl.$setViewValue(transformedInput);
          ctrl.$render();
        }

        return transformedInput;
      }

      ctrl.$parsers.push(fromUser);
    }
  }
}());
