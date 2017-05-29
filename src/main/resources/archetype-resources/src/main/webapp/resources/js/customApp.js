$(function () {
  $('.navbar-expand-toggle').click(function () {
    $('.app-container').toggleClass('expanded');
    return $('.navbar-expand-toggle').toggleClass('fa-rotate-90');
  });

  return $('.navbar-right-expand-toggle').click(function () {
    $('.navbar-right').toggleClass('expanded');
    return $('.navbar-right-expand-toggle').toggleClass('fa-rotate-90');
  });
});

$(function(){
  var sPath = window.location.pathname;
  var sSearch = window.location.search;

  $('a[href="'+ sPath + sSearch + '"]').closest('li').addClass('active');
  $('a[href="'+ sPath + sSearch + '"]').parents('.panel-collapse').addClass('in');

});

$(function() {
  // Hide/Show the sideBar according the mediaQuery.
  var mq = window.matchMedia( "(max-width: 1300px)" );
  var sideBar = document.getElementById("app-container");
  if (sideBar){
    if (mq.matches) {
      console.log('Is less then 1300px');
      sideBar.classList ? sideBar.classList.remove('expanded') : sideBar.className -= 'expanded';
    } else {
        console.log('Is more then 1300px');
        sideBar.classList ? sideBar.classList.add('expanded') : sideBar.className += 'expanded';
    }
  }
});

$(function() {
  $('li.navroot').click(function () {
    if (!$('.app-container').hasClass('expanded')){
      $('.app-container').addClass('expanded');
      $('.navbar-expand-toggle').toggleClass('fa-rotate-90');
    }
  });
});