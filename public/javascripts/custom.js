$(document).ready(function() {

      // =====================================================
      // Back link mimics browser back functionality
      // =====================================================
      // store referrer value to cater for IE - https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/10474810/  */
      var docReferrer = document.referrer
      // prevent resubmit warning
      if (window.history && window.history.replaceState && typeof window.history.replaceState === 'function') {
        window.history.replaceState(null, null, window.location.href);
      }
      $('#back-link').on('click', function(e){
        e.preventDefault();
        window.history.back();
      })


      // ------------------------------------
      // Introduce direct skip link control, to work around voiceover failing of hash links
      // https://bugs.webkit.org/show_bug.cgi?id=179011
      // https://axesslab.com/skip-links/
      // ------------------------------------
      $('.skiplink').click(function(e) {
          e.preventDefault();
          $(':header:first').attr('tabindex', '-1').focus();
      });

});

// dynamically re-position nuance divs before footer for accessibility
$(window).on("load", function() {

    var waitForEl = function(selector, callback, count) {
        if (jQuery(selector).length) {
        callback();
        } else {
        setTimeout(function() {
          if(!count) {
            count=0;
          }
          count++;
          console.log("count: " + count);
          if(count<3) {
            waitForEl(selector,callback,count);
          } else {return;}
        }, 1000);
      }
    }

    waitForEl("#inqChatStage", function() {
        $('#footer').appendTo(document.body);
    });

});
