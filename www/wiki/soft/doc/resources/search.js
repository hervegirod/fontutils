var tags = null;
var paths = new Object();
var noResult = {l: "No results found"};
var articleLevel = 0;
var searchLimit = -1;
var searchItems = 0;
var alwaysShow = false;

function setSearchLimit(limit) {
   searchLimit = limit;
}

$(function () {
    $.widget( "custom.catcomplete", $.ui.autocomplete, {
	   alwaysShow: alwaysShow,       
      _create: function() {
        this._super();
        this.widget().menu( "option", "items", "> :not(.ui-autocomplete-category)" );
      },
      _renderMenu: function( ul, items ) {
        var that = this;
        searchItems = 0;
		  if (items.length >= 1) {
          $.each( items, function( index, item ) {
            var li;
            searchItems++;
            if (searchLimit === -1 || searchItems <= searchLimit) {
              li = that._renderItemData( ul, item );
            }
          });
		  }
      }  
    });   
   var loc = window.location.href;
   var split = loc.split("/");
   if (split.length > 3) {
      if (split[split.length - 2] === "articles") {
         articleLevel = 1;
      } else if (split.length > 4 && split[split.length - 3] === "articles") {
         articleLevel = 2;
      }
   }
   var data = getArticles();
   tags = new Array(data.length);
   for (i = 0; i < data.length; i++) {
      var name = data[i].name;
      var url = data[i].url;
      tags[i] = name;
      paths[name] = url;
   }
   $("#tags").catcomplete({
      source: tags,   
      select: function (event, ui) {
        if (ui.item.label !== null) {
           var path = paths[ui.item.label];
           if (path !== null) {
             if (articleLevel === 0) {
               window.location.href = path;
             } else if (articleLevel === 1) {
               window.location.href = "../" + path;
             } else {
               window.location.href = "../.." + path;
             }
          }
        }
      }
   });
   $.extend($.ui.autocomplete.prototype, {
      _renderItem: function (ul, item) {
         var searchMask = this.element.val();
         var regEx = new RegExp(searchMask, "ig");
         var replaceMask = "<b style=\"font-weight:bold;text-decoration:underline;\">$&</b>";
         var html = item.label.replace(regEx, replaceMask);

         return $("<li></li>")
                 .data("item.autocomplete", item)
                 .append($("<a></a>").html(html))
                 .appendTo(ul);
      }
   });
});
