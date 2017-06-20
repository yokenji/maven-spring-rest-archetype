(function () {
  'use strict';

  var gulp = require('gulp');
  var _ = require('gulp-load-plugins')();
  var fs = require('fs');
  var mainBowerFiles = require('main-bower-files');
  var config = require('./config.js');
  var runSequence = require('run-sequence');
  var exists = require('path-exists').sync;

  var jsSrc = './WEB-INF/thymeleaf/fragments/scriptLoaders.html';
  var cssSrc = './WEB-INF/thymeleaf/fragments/styleLoaders.html';

  //fill this in with the correct url
  var appNameUrl = process.argv[3];
  var ENV = 'dev';

  if (_.util.env._[0] === 'prod') {
    ENV = 'prod';
  }

  if (_.util.env._[0] === 'test') {
    ENV = 'test';
  }

  gulp.task('default', [ENV]);

  var bowerJsFiles = mainBowerFiles({ filter: ["**/*.js"] }, { paths: { bowerDirectory: './resources/bower_components' } });
  var bowerCssFiles = mainBowerFiles({ filter: ["**/*.css"] }, { paths: { bowerDirectory: './resources/bower_components' } });

  var js = gulp.src(['./resources/js/**/*.js', './resources/components/**/*.*.js']).pipe(_.angularFilesort ());

  var bowerJsWithMin = bowerJsFiles.map( function(path, index, arr) {
    var newPath = path.replace(/.([^.]+)$/g, '.min.$1');
    return exists( newPath ) ? newPath : path;
  });

  var bowerCssWithMin = bowerCssFiles.map( function(path, index, arr) {
    var newPath = path.replace(/.([^.]+)$/g, '.min.$1');
    return exists( newPath ) ? newPath : path;
  });

  //concat all bower components
  gulp.task("concat-vendor-js", function(){
    bowerJsFiles.push('./resources/theme/lib/js/jquery.min.js')
    return gulp.src(bowerJsFiles)
            .pipe(_.ngAnnotate())
            .pipe(_.concat("vendor.bundle.js"))
            .pipe(gulp.dest('./resources/dist/js'));
  });

//concat all app app module components
  gulp.task("concat-app-components", function(){
    return gulp.src(['./resources/components/**/*.*.js'])
              .pipe(_.angularFilesort ())
              .pipe(_.ngAnnotate())
              .pipe(_.concat('components.bundle.js'))
              .pipe(gulp.dest('./resources/dist/js'));
  });

  //concat all js files(excluding module components)
  gulp.task('concat-app-js', function(){
    return gulp.src(['!./resources/theme/lib/js/jquery.min.js', './resources/js/**/*.js', './resources/widgets/**/*.js', './resources/theme/lib/js/*.js', './resources/interceptors/*.js'])
               .pipe(_.ngAnnotate())
               .pipe(_.angularFilesort ())
               .pipe(_.concat('app.bundle.js'))
               .pipe(gulp.dest('./resources/dist/js'));
  });

  //concat all bower css files 
  gulp.task("concat-vendor-css", function(){
    return gulp.src(bowerCssWithMin)
            .pipe(_.concat('vendor.bundle.css'))
            .pipe(gulp.dest('./resources/dist/styles'));
  });

  //move fontawesome font files
  gulp.task("move-fonts", function(){
    return gulp.src('./resources/theme/lib/fonts/*.*')
              .pipe(gulp.dest('./resources/dist/fonts'));
  });

  //move img files to dist folder
  gulp.task("move-img", function(){
    return gulp.src('./resources/img/*.*')
            .pipe(gulp.dest('./resources/dist/img'));
  });

  //concat all theme css files 
  gulp.task("concat-app-css", function(){
    return gulp.src(['./resources/theme/lib/css/*.css', './resources/theme/css/*.css'])
            .pipe(_.concat('app.bundle.css'))
            .pipe(gulp.dest('./resources/dist/styles'));
  });

  //concat all user-made css files 
  gulp.task("concat-user-css", function(){
    return gulp.src(['./resources/css/*.css'])
            .pipe(_.concat('user.bundle.css'))
            .pipe(gulp.dest('./resources/dist/styles'));
  });

  //uglify all js files for production
  gulp.task('uglify', function(){
    return gulp.src(['./resources/dist/js/*.*.js'])
            .pipe(_.uglify({ mangle : { except: ['$super', '$', 'exports'] }, 
                            compress : {drop_console: true, drop_debugger: true}
                          })
                 )
            .pipe(gulp.dest('./resources/dist/js'));
  });

  //Inject the concat js bundles in the scriptloader file
  gulp.task('inject-bundles-js', function () {
    return gulp.src(jsSrc)
      .pipe(_.inject(gulp.src('EMPTY'), { empty: true }))

      .pipe(_.inject(gulp.src('./resources/dist/js/*.js', { read: false })
                    .pipe(_.naturalSort('desc')),{transform: function (filepath) {return setFilePath(filepath, "javascript");}}))

      .pipe(gulp.dest('./WEB-INF/thymeleaf/fragments'));
  });

  //inject the concat css bundles in the styleloader file
  gulp.task('inject-bundles-css', function () {
    return gulp.src(cssSrc)

      .pipe(_.inject(gulp.src('EMPTY'), { empty: true }))

      .pipe(_.inject(gulp.src('./resources/dist/styles/*.css', { read: false }), {transform: function (filepath) {return setFilePath(filepath, "css");}}))

      .pipe(gulp.dest('./WEB-INF/thymeleaf/fragments'));
  });

  function setFilePath(filepath, type) {
    if (type == 'javascript') {
      return '<script type="text/javascript" th:src="@{' + filepath + '}"></script>';
    }
    else {
      return '<link rel="stylesheet" type="text/css" th:href="@{' + filepath + '}"/>';
    }
  }

  //Inject all javascript files from the components, bower_components and js folder under resources in the scriptLoader file
  gulp.task('inject-all-js', function () {
    return gulp.src(jsSrc)

      .pipe(_.inject(gulp.src('EMPTY'), { empty: true }))

      .pipe(_.inject(gulp.src(bowerJsWithMin, { read: false }), {transform: function (filepath) {return setFilePath(filepath, "javascript");}}))

      .pipe(_.inject(gulp.src('./resources/components/**/*.*.js')
                    .pipe(_.angularFilesort ()), { starttag: "<!-- inject:components: {{ext}} -->", transform: function (filepath) {return setFilePath(filepath, "javascript");} }))

      .pipe(_.inject(gulp.src(['./resources/theme/lib/js/jquery.min.js', './resources/js/**/*.js', './resources/widgets/**/*.js', './resources/theme/lib/js/*.js', './resources/interceptors/*.js'], { read: false }),{ starttag: "<!-- inject:scripts:{{ext}} -->", transform: function (filepath) {return setFilePath(filepath, "javascript");}}))

      .pipe(gulp.dest('./WEB-INF/thymeleaf/fragments'));
  });

  //Inject all css files from under resources in the styleloader file
  gulp.task('inject-all-css', function () {
    return gulp.src(cssSrc)
      .pipe(_.inject(gulp.src('EMPTY'), { empty: true }))

      .pipe(_.inject(gulp.src(['./resources/css/*.css'], { read: false }), {starttag: "<!-- inject:app:{{ext}} -->", transform: function (filepath) {return setFilePath(filepath, "css");}}))
      .pipe(_.inject(gulp.src(['./resources/theme/lib/css/*.css'], { read: false }), {starttag: "<!-- inject:theme-lib:{{ext}} -->", transform: function (filepath) {return setFilePath(filepath, "css");}}))
      .pipe(_.inject(gulp.src(['./resources/theme/css/*.css'], { read: false }), {starttag: "<!-- inject:theme:{{ext}} -->", transform: function (filepath) {return setFilePath(filepath, "css");}}))

      .pipe(_.inject(gulp.src(bowerCssWithMin, { read: false }), { transform: function (filepath) {return setFilePath(filepath, "css");}}))

      .pipe(gulp.dest('./WEB-INF/thymeleaf/fragments'));
  });

  //turn config.js file in json file
  var makeJson = function (env, filePath) {
    fs.writeFileSync(filePath,
      JSON.stringify(env));
  };

  //generate config module in /js folder
  gulp.task('ng-config', function () {
    makeJson(config[ENV], './config.json');
    return gulp.src('./config.json')
      .pipe(
        _.ngConfig('config', 
        {
          constants: config[ENV],
          createModule: true
        })
      )
      .pipe(gulp.dest('./resources/js/'));
  });

  gulp.task('clean', function () {
    return gulp.src('./resources/dist', {read : false})
            .pipe(_.clean());
  });

  //watch folders for changes
  gulp.task('watch-dev', function () {
    gulp.watch(['./bower_components/**/*.js'], ['inject-all-js']);
    gulp.watch(['./bower_components/**/*.css'], ['inject-all-css']);
    gulp.watch(['./resources/components/**/*.*.js'], ['inject-all-js']);
    gulp.watch(['./resources/js/*.js'], ['inject-all-js']);
  });

  //add watch-dev for watcher task after inject-all-css
  gulp.task('dev', function(callback){
    runSequence('ng-config', 'inject-all-js', 'inject-all-css', callback);
  });

  gulp.task('prod', function(callback){
    runSequence('clean', 'ng-config', 'move-fonts', 'move-img', 'concat-vendor-js','concat-vendor-css', 'concat-user-css', 'concat-app-css', 'concat-app-js', 'concat-app-components', 'uglify', 'inject-bundles-js', 'inject-bundles-css', callback);
  });

  gulp.task('test', function(callback){
    runSequence('ng-config', 'inject-all-js', 'inject-all-css', callback);
  });

})();
