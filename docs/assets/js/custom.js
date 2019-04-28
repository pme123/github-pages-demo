(function ($) {

    $(document).ready(function () {
        'use strict';

        /* selector */
        var postHeader = '.e-content > h1, .e-content > h2, .e-content > h3, .e-content > h4';

        $(postHeader).filter('[id]').each(function () {
            var header = $(this),
                headerID = header.attr('id'),
                anchorClass = 'header-link',
                anchorIcon = '<i class="fa fa-link" aria-hidden="true"></i>';

            if (headerID) {
                header.prepend($('<a />').addClass(anchorClass).attr({ 'href': '#' + headerID, 'aria-hidden': 'true' }).html(anchorIcon));
            }

            return this;
        });
      });
}(jQuery));