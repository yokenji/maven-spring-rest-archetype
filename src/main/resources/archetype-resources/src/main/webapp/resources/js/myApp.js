(function () {
  'use strict';

  angular
    .module('myApp', [
      'ui.bootstrap',
      'config',
      'angular-loading-bar',
      'ngAnimate',
      'pascalprecht.translate',
      'ngSanitize',
      'user.module',
      //'lettercase.module',
      'moment-picker',
      'ui.select',
      'toastr',
      'httpError.module',
      //'decimal-input.module',
      //'numeric-input.module,
      'password-popover.module'
    ])
    .config(languageConfig)
    .controller('languageController', languageController)
    .controller('clockController', clockController)
    .config(toastrConfig);

  function toastrConfig(toastrConfig) {
    angular.extend(toastrConfig, {
      autoDismiss: false,
      containerId: 'toast-container',
      maxOpened: 0,
      newestOnTop: true,
      positionClass: 'toast-bottom-full-width',
      preventDuplicates: false,
      preventOpenDuplicates: false,
      target: 'body'
    });
  }

  languageConfig.$inject = ['$translateProvider', 'EnvironmentConfig'];

  function languageConfig($translateProvider, EnvironmentConfig) {
    $translateProvider.useStaticFilesLoader({
      prefix: EnvironmentConfig.api + '/resources/i18n/locale-',
      suffix: '.json',
    });
    $translateProvider.registerAvailableLanguageKeys(['en', 'nl', 'fr'], {
      'en_*': 'en',
      'nl_*': 'nl',
      'fr_*': 'fr',
    });
    $translateProvider.fallbackLanguage('en');
    $translateProvider.determinePreferredLanguage();
    $translateProvider.useSanitizeValueStrategy('escape');
  }

  languageController.$inject = ['$translate', 'userService'];

  function languageController($translate, userService) {
    var vm = this;
    vm.languages = ['NL', 'EN', 'FR'];
    vm.selectedLanguage;

    vm.changeLanguage = function (key) {
      $translate.use(key.toLowerCase());
    };
  }

  clockController.$inject = ['$timeout'];

  function clockController($timeout) {
    var vm = this;
    vm.date = moment();
    vm.time = vm.date.format('HH:mm');
    vm.day = vm.date.format('MMMM DD');
    vm.year = vm.date.format('YYYY');

    var setDate = function () {
      vm.time = moment().format('HH:mm');
      vm.day = moment().format('MMMM DD');
      $timeout(setDate, 1000);
    };

    $timeout(setDate, 1000);
  }
})();
