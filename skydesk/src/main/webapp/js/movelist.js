;(function($){
	$.fn.dragsort = function(options) {
		if (options == "destroy") {
			$(this.selector).trigger("dragsort-uninit");
			return;
		}

		var opts = $.extend({}, $.fn.dragsort.defaults, options);
		var lists = [];
		var list = null, lastPos = null;

		this.each(function(i, cont) {

			//if list container is table, the browser automatically wraps rows in tbody if not specified so change list container to tbody so that children returns rows as user expected
			if ($(cont).is("table") && $(cont).children().size() == 1 && $(cont).children().is("tbody"))
				cont = $(cont).children().get(0);

			var newList = {
				draggedItem: null,
				placeHolderItem: null,
				pos: null,
				offset: null,
				offsetLimit: null,
				scroll: null,
				container: cont,

				init: function() {
					//set options to default values if not set
					opts.tagName = $(this.container).children().size() == 0 ? "li" : $(this.container).children().get(0).tagName.toLowerCase();
					if (opts.itemSelector == "")
						opts.itemSelector = opts.tagName;
					if (opts.dragSelector == "")
						opts.dragSelector = opts.tagName;
					if (opts.placeHolderTemplate == "")
						opts.placeHolderTemplate = "<" + opts.tagName + ">&nbsp;</" + opts.tagName + ">";

					//listidx allows reference back to correct list variable instance
					$(this.container).attr("data-listidx", i).mousedown(this.grabItem).bind("dragsort-uninit", this.uninit);
					this.styleDragHandlers(true);
				},

				uninit: function() {
					var list = lists[$(this).attr("data-listidx")];
					$(list.container).unbind("mousedown", list.grabItem).unbind("dragsort-uninit");
					list.styleDragHandlers(false);
				},

				getItems: function() {
					return $(this.container).children(opts.itemSelector);
				},

				styleDragHandlers: function(cursor) {
					this.getItems().map(function() { return $(this).is(opts.dragSelector) ? this : $(this).find(opts.dragSelector).get(); }).css("cursor", cursor ? "pointer" : "");
				},

				grabItem: function(e) {
					var list = lists[$(this).attr("data-listidx")];
					var item = $(e.target).closest("[data-listidx] > " + opts.tagName).get(0);
					var insideMoveableItem = list.getItems().filter(function() { return this == item; }).size() > 0;

					//if not left click or if clicked on excluded element (e.g. text box) or not a moveable list item return
					if (e.which != 1 || $(e.target).is(opts.dragSelectorExclude) || $(e.target).closest(opts.dragSelectorExclude).size() > 0 || !insideMoveableItem)
						return;

					//prevents selection, stops issue on Fx where dragging hyperlink doesn't work and on IE where it triggers mousemove even though mouse hasn't moved,
					//does also stop being able to click text boxes hence dragging on text boxes by default is disabled in dragSelectorExclude
					e.preventDefault();

					//change cursor to move while dragging
					var dragHandle = e.target;
					while (!$(dragHandle).is(opts.dragSelector)) {
						if (dragHandle == this) return;
						dragHandle = dragHandle.parentNode;
					}
					$(dragHandle).attr("data-cursor", $(dragHandle).css("cursor"));
					$(dragHandle).css("cursor", "move");

					//on mousedown wait for movement of mouse before triggering dragsort script (dragStart) to allow clicking of hyperlinks to work
					var listElem = this;
					var trigger = function() {
						list.dragStart.call(listElem, e);
						$(list.container).unbind("mousemove", trigger);
					};
					$(list.container).mousemove(trigger).mouseup(function() { $(list.container).unbind("mousemove", trigger); $(dragHandle).css("cursor", $(dragHandle).attr("data-cursor")); });
				},

				dragStart: function(e) {
					if (list != null && list.draggedItem != null)
						list.dropItem();

					list = lists[$(this).attr("data-listidx")];
					list.draggedItem = $(e.target).closest("[data-listidx] > " + opts.tagName)

					//record current position so on dragend we know if the dragged item changed position or not, not using getItems to allow dragsort to restore dragged item to original location in relation to fixed items
					list.draggedItem.attr("data-origpos", $(this).attr("data-listidx") + "-" + $(list.container).children().index(list.draggedItem));

					//calculate mouse offset relative to draggedItem
					var mt = parseInt(list.draggedItem.css("marginTop"));
					var ml = parseInt(list.draggedItem.css("marginLeft"));
					list.offset = list.draggedItem.offset();
					list.offset.top = e.pageY - list.offset.top + (isNaN(mt) ? 0 : mt) - 1;
					list.offset.left = e.pageX - list.offset.left + (isNaN(ml) ? 0 : ml) - 1;

					//calculate box the dragged item can't be dragged outside of
					if (!opts.dragBetween) {
						var containerHeight = $(list.container).outerHeight() == 0 ? Math.max(1, Math.round(0.5 + list.getItems().size() * list.draggedItem.outerWidth() / $(list.container).outerWidth())) * list.draggedItem.outerHeight() : $(list.container).outerHeight();
						list.offsetLimit = $(list.container).offset();
						list.offsetLimit.right = list.offsetLimit.left + $(list.container).outerWidth() - list.draggedItem.outerWidth();
						list.offsetLimit.bottom = list.offsetLimit.top + containerHeight - list.draggedItem.outerHeight();
					}

					//create placeholder item
					var h = list.draggedItem.height();
					var w = list.draggedItem.width();
					if (opts.tagName == "tr") {
						list.draggedItem.children().each(function() { $(this).width($(this).width()); });
						list.placeHolderItem = list.draggedItem.clone().attr("data-placeholder", true);
						list.draggedItem.after(list.placeHolderItem);
						list.placeHolderItem.children().each(function() { $(this).css({ borderWidth:0, width: $(this).width() + 1, height: $(this).height() + 1 }).html("&nbsp;"); });
					} else {
						list.draggedItem.after(opts.placeHolderTemplate);
						list.placeHolderItem = list.draggedItem.next().css({ height: h, width: w }).attr("data-placeholder", true);
					}

					if (opts.tagName == "td") {
						var listTable = list.draggedItem.closest("table").get(0);
						$("<table id='" + listTable.id + "' style='border-width: 0px;' class='dragSortItem " + listTable.className + "'><tr></tr></table>").appendTo("body").children().append(list.draggedItem);
					}

					//style draggedItem while dragging
					var orig = list.draggedItem.attr("style");
					list.draggedItem.attr("data-origstyle", orig ? orig : "");
					list.draggedItem.css({ position: "absolute", opacity: 0.8, "z-index": 999, height: h, width: w });

					//auto-scroll setup
					list.scroll = { moveX: 0, moveY: 0, maxX: $(document).width() - $(window).width(), maxY: $(document).height() - $(window).height() };
					list.scroll.scrollY = window.setInterval(function() {
						if (opts.scrollContainer != window) {
							$(opts.scrollContainer).scrollTop($(opts.scrollContainer).scrollTop() + list.scroll.moveY);
							return;
						}
						var t = $(opts.scrollContainer).scrollTop();
						if (list.scroll.moveY > 0 && t < list.scroll.maxY || list.scroll.moveY < 0 && t > 0) {
							$(opts.scrollContainer).scrollTop(t + list.scroll.moveY);
							list.draggedItem.css("top", list.draggedItem.offset().top + list.scroll.moveY + 1);
						}
					}, 10);
					list.scroll.scrollX = window.setInterval(function() {
						if (opts.scrollContainer != window) {
							$(opts.scrollContainer).scrollLeft($(opts.scrollContainer).scrollLeft() + list.scroll.moveX);
							return;
						}
						var l = $(opts.scrollContainer).scrollLeft();
						if (list.scroll.moveX > 0 && l < list.scroll.maxX || list.scroll.moveX < 0 && l > 0) {
							$(opts.scrollContainer).scrollLeft(l + list.scroll.moveX);
							list.draggedItem.css("left", list.draggedItem.offset().left + list.scroll.moveX + 1);
						}
					}, 10);

					//misc
					$(lists).each(function(i, l) { l.createDropTargets(); l.buildPositionTable(); });
					list.setPos(e.pageX, e.pageY);
					$(document).bind("mousemove", list.swapItems);
					$(document).bind("mouseup", list.dropItem);
					if (opts.scrollContainer != window)
						$(window).bind("wheel", list.wheel);
				},

				//set position of draggedItem
				setPos: function(x, y) { 
					//remove mouse offset so mouse cursor remains in same place on draggedItem instead of top left corner
					var top = y - this.offset.top;
					var left = x - this.offset.left;

					//limit top, left to within box draggedItem can't be dragged outside of
					if (!opts.dragBetween) {
						top = Math.min(this.offsetLimit.bottom, Math.max(top, this.offsetLimit.top));
						left = Math.min(this.offsetLimit.right, Math.max(left, this.offsetLimit.left));
					}

					//adjust top & left calculations to parent offset
					var parent = this.draggedItem.offsetParent().not("body").offset(); //offsetParent returns body even when it's static, if not static offset is only factoring margin
					if (parent != null) {
						top -= parent.top;
						left -= parent.left;
					}

					//set x or y auto-scroll amount
					if (opts.scrollContainer == window) {
						y -= $(window).scrollTop();
						x -= $(window).scrollLeft();
						y = Math.max(0, y - $(window).height() + 5) + Math.min(0, y - 5);
						x = Math.max(0, x - $(window).width() + 5) + Math.min(0, x - 5);
					} else {
						var cont = $(opts.scrollContainer);
						var offset = cont.offset();
						y = Math.max(0, y - cont.height() - offset.top) + Math.min(0, y - offset.top);
						x = Math.max(0, x - cont.width() - offset.left) + Math.min(0, x - offset.left);
					}
					
					list.scroll.moveX = x == 0 ? 0 : x * opts.scrollSpeed / Math.abs(x);
					list.scroll.moveY = y == 0 ? 0 : y * opts.scrollSpeed / Math.abs(y);

					//move draggedItem to new mouse cursor location
					this.draggedItem.css({ top: top, left: left });
				},

				//if scroll container is a div allow mouse wheel to scroll div instead of window when mouse is hovering over
				wheel: function(e) {
					if (list && opts.scrollContainer != window) {
						var cont = $(opts.scrollContainer);
						var offset = cont.offset();
						e = e.originalEvent;
						if (e.clientX > offset.left && e.clientX < offset.left + cont.width() && e.clientY > offset.top && e.clientY < offset.top + cont.height()) {
							var deltaY = (e.deltaMode == 0 ? 1 : 10) * e.deltaY;
							cont.scrollTop(cont.scrollTop() + deltaY);
							e.preventDefault();
						}
					}
				},

				//build a table recording all the positions of the moveable list items
				buildPositionTable: function() {
					var pos = [];
					this.getItems().not([list.draggedItem[0], list.placeHolderItem[0]]).each(function(i) {
						var loc = $(this).offset();
						loc.right = loc.left + $(this).outerWidth();
						loc.bottom = loc.top + $(this).outerHeight();
						loc.elm = this;
						pos[i] = loc;
					});
					this.pos = pos;
				},

				dropItem: function() {
					if (list.draggedItem == null)
						return;

					//list.draggedItem.attr("style", "") doesn't work on IE8 and jQuery 1.5 or lower
					//list.draggedItem.removeAttr("style") doesn't work on chrome and jQuery 1.6 (works jQuery 1.5 or lower)
					var orig = list.draggedItem.attr("data-origstyle");
					list.draggedItem.attr("style", orig);
					if (orig == "")
						list.draggedItem.removeAttr("style");
					list.draggedItem.removeAttr("data-origstyle");

					list.styleDragHandlers(true);

					list.placeHolderItem.before(list.draggedItem);
					list.placeHolderItem.remove();

					$("[data-droptarget], .dragSortItem").remove();

					window.clearInterval(list.scroll.scrollY);
					window.clearInterval(list.scroll.scrollX);

					//if position changed call dragEnd
					if (list.draggedItem.attr("data-origpos") != $(lists).index(list) + "-" + $(list.container).children().index(list.draggedItem))
						if (opts.dragEnd.apply(list.draggedItem) == false) { //if dragEnd returns false revert order
							var pos = list.draggedItem.attr("data-origpos").split('-');
							var nextItem = $(lists[pos[0]].container).children().not(list.draggedItem).eq(pos[1]);
							if (nextItem.size() > 0)
								nextItem.before(list.draggedItem);
							else if (pos[1] == 0) //was the only item in list
								$(lists[pos[0]].container).prepend(list.draggedItem);
							else //was the last item in list
								$(lists[pos[0]].container).append(list.draggedItem);
						}
					list.draggedItem.removeAttr("data-origpos");

					list.draggedItem = null;
					$(document).unbind("mousemove", list.swapItems);
					$(document).unbind("mouseup", list.dropItem);
					if (opts.scrollContainer != window)
						$(window).unbind("wheel", list.wheel);
					return false;
				},

				//swap the draggedItem (represented visually by placeholder) with the list item the it has been dragged on top of
				swapItems: function(e) {
					if (list.draggedItem == null)
						return false;

					//move draggedItem to mouse location
					list.setPos(e.pageX, e.pageY);

					//retrieve list and item position mouse cursor is over
					var ei = list.findPos(e.pageX, e.pageY);
					var nlist = list;
					for (var i = 0; ei == -1 && opts.dragBetween && i < lists.length; i++) {
						ei = lists[i].findPos(e.pageX, e.pageY);
						nlist = lists[i];
					}

					//if not over another moveable list item return
					if (ei == -1)
						return false;

					//save fixed items locations
					var children = function() { return $(nlist.container).children().not(nlist.draggedItem); };
					var fixed = children().not(opts.itemSelector).each(function(i) { this.idx = children().index(this); });

					//if moving draggedItem up or left place placeHolder before list item the dragged item is hovering over otherwise place it after
					if (lastPos == null || lastPos.top > list.draggedItem.offset().top || lastPos.left > list.draggedItem.offset().left)
						$(nlist.pos[ei].elm).before(list.placeHolderItem);
					else
						$(nlist.pos[ei].elm).after(list.placeHolderItem);

					//restore fixed items location
					fixed.each(function() {
						var elm = children().eq(this.idx).get(0);
						if (this != elm && children().index(this) < this.idx)
							$(this).insertAfter(elm);
						else if (this != elm)
							$(this).insertBefore(elm);
					});

					//misc
					$(lists).each(function(i, l) { l.createDropTargets(); l.buildPositionTable(); });
					lastPos = list.draggedItem.offset();
					return false;
				},

				//returns the index of the list item the mouse is over
				findPos: function(x, y) {
					for (var i = 0; i < this.pos.length; i++) {
						if (this.pos[i].left < x && this.pos[i].right > x && this.pos[i].top < y && this.pos[i].bottom > y)
							return i;
					}
					return -1;
				},

				//create drop targets which are placeholders at the end of other lists to allow dragging straight to the last position
				createDropTargets: function() {
					if (!opts.dragBetween)
						return;

					$(lists).each(function() {
						var ph = $(this.container).find("[data-placeholder]");
						var dt = $(this.container).find("[data-droptarget]");
						if (ph.size() > 0 && dt.size() > 0)
							dt.remove();
						else if (ph.size() == 0 && dt.size() == 0) {
							if (opts.tagName == "td")
								$(opts.placeHolderTemplate).attr("data-droptarget", true).appendTo(this.container);
							else
								//list.placeHolderItem.clone().removeAttr("data-placeholder") crashes in IE7 and jquery 1.5.1 (doesn't in jquery 1.4.2 or IE8)
								$(this.container).append(list.placeHolderItem.removeAttr("data-placeholder").clone().attr("data-droptarget", true));
							
							list.placeHolderItem.attr("data-placeholder", true);
						}
					});
				}
			};

			newList.init();
			lists.push(newList);
		});

		return this;
	};

	$.fn.dragsort.defaults = {
		itemSelector: "",
		dragSelector: "",
		dragSelectorExclude: "input, textarea",
		dragEnd: function() { },
		dragBetween: false,
		placeHolderTemplate: "",
		scrollContainer: window,
		scrollSpeed: 5
	};
	var setlistbox = function(options){
		var hzlistbox = options.hzlistbox;
		var showlistbox = options.showlistbox;
		var hzlist = options.hzlist;
		var showlist = options.showlist;
		var hzlistbox = options.hzlistbox;
		var showlistbox = options.showlistbox;
		for(var i in hzlist){
			var $li = $('<li></li>');
				$li.data('listitem',hzlist[i]);
			var  cls = "";
			var showable = hzlist[i].showable;
			var disabled = hzlist[i].disabled;
			if(typeof(showable)=="string")
				showable = eval(showable);
			else
				showable = true;
			if(!showable)
				continue;
			if(hzlist[i].hzbj==-1)
				cls="no_hz";
			else if(hzlist[i].hzbj==-2)
				cls="only_hz";
			if(disabled)
				cls +=" disabled_hz checked";
			var dis = "";
			if(disabled)
				dis = "disabled checked";
			$li.addClass(cls);
			var lihtml = '<div class="skylistbox_left hzcheckbox"><input type="checkbox"  '+dis+'/></div>'
				+'<div class="skylistbox_left hzname">'+hzlist[i].hzname+'</div>';
			$li.html(lihtml);
			$(hzlistbox).append($li);
		}
		for(var i in showlist){
			var $li = $('<li></li>');
			$li.data('listitem',hzlist[i]);
			var lihtml = '<div class="skylistbox_left"><input type="checkbox"/></div>'
				+'<div class="skylistbox_left">'+showlist[i].hzname+'</div>'
				+'<div class="skylistbox_right">'
				+'<label>升序<input type="radio" name="skylistbox_order'+i+'" value="asc"/></label>'
				+'<label>降序<input type="radio" name="skylistbox_order'+i+'" value="desc" /></label>'
				+'</div>';
			$li.html(lihtml);
			$(showlistbox).append($li);
		}
	}
	$.fn.skyListBox = function(options,param,param1){
		if(typeof options === "string")
			return $.fn.skyListBox.methods[options](this,param,param1);
		return this.each(function(){
			if(options){
				var defaultset={
						hzlistbox: null,
						showlistbox: null,
						checkbox: true,
						orderRadio: false,
						hzlist: null,
						showlist: null
				};
				var opts=$.extend({},defaultset,options);
				$(this).data('skylistbox',opts);
				setlistbox(opts);
				var $this = $(this);
				var $hzlistbox = $(opts.hzlistbox);
				var $showlistbox = $(opts.showlistbox);
				$showlistbox.delegate('label','mouseup',function(){
					var checked = $(this).find('input').prop('checked');
					if(checked)
						$(this).find('input').removeProp('checked');
				});
				$this.delegate('input[type=checkbox]','click',function(e){
					e.stopPropagation(); 
					var $this = $(this);
					if($this.prop('checked'))
						$this.parent().parent().addClass('checked');
					else
						$this.parent().parent().removeClass('checked');
				});
				$this.delegate("li","click",function(e){
					var $this = $(this);
					var $ck = $this.find('input[type="checkbox"]');
					if($ck.prop('checked')){
						$ck.removeProp('checked');
						$this.removeClass('checked');
					}
					else{
						$ck.prop('checked',true);
						$this.addClass('checked');
					}
				});
				$hzlistbox.dragsort({ dragSelector: "li", dragBetween: false, dragEnd: function(){},scrollContainer: opts.hzlistbox, placeHolderTemplate: "" });
				$showlistbox.dragsort({ dragSelector: "li", dragBetween: false, dragEnd:  function(){},scrollContainer: opts.showlistbox, placeHolderTemplate: "" });
			}
		});
	}
	
	$.fn.movelist = function(options){
		if(typeof options === "string")
			return $.fn.movelist.methods[options](this);
		return this.each(function(){
			if(options){
				var preset={
						boxl:null,
						boxr:null,
						boxlrX:null,
						boxon:null,
						multiselect:false,
						idclass:true,
						boxlan:null,
						boxran:null,
						boxtan:null,
						boxban:null,
						boxalllan:null,
						boxallran:null,
						boxalltan:null,
						boxallban:null,
						leftobj: {},
						rightobj: {}
				};
				var outside=$.extend(true,preset,options);
				$(this).data('moveoptions',outside);
				if(outside.idclass){
					idclass="."
				}else{
					idclass="#"
				};
				var lobj = outside.leftobj;
				var robj = outside.rightobj;
				var boxl = outside.boxl;
				var boxr = outside.boxr;
				resetbox(boxl,lobj);
				resetbox(boxr,robj);
				$(this).find(outside.boxlrX).on("click",function(){
					$(this).addClass(outside.boxon).siblings().removeClass(outside.boxon);
				});
				$(outside.boxl).mouseover(function(){
					$(outside.boxl).find(outside.boxlrX).on("dblclick",function(){
						$(this).appendTo(outside.boxr).siblings().removeClass(outside.boxon);
					});
				});
				$(outside.boxr).mouseover(function(){
					$(outside.boxr).find(outside.boxlrX).on("dblclick",function(){
						$(this).appendTo(outside.boxl).siblings().removeClass(outside.boxon);
					});
				});
				function left(){
					var isno=$(outside.boxr).find(outside.boxlrX).hasClass(outside.boxon);
					var find=$(outside.boxr).children(idclass+outside.boxon);
					if(isno){
						find.appendTo(outside.boxl).siblings().removeClass(outside.boxon);
					}else{
						//alert("请选择一个！");
					};
				};
				function right(){
					var isno=$(outside.boxl).find(outside.boxlrX).hasClass(outside.boxon);
					var find=$(outside.boxl).children(idclass+outside.boxon);
					if(isno){
						find.appendTo(outside.boxr).siblings().removeClass(outside.boxon);
					}else{
						//alert("请选择一个！");
					};
				};
				function top(){
					var isno=$(outside.boxr).find(outside.boxlrX).hasClass(outside.boxon);
					var index=$(outside.boxr).children(idclass+outside.boxon).index();
					var top=$(outside.boxr).children(outside.boxlrx).eq(index);
					if(isno){
						if(index>0){
							$(outside.boxr).children(outside.boxlrx).eq(index-1).before(top);
						}else{
							//alert("已经是第一个了！！")
						};
					}else{
						//alert("请选择一个！");
					};
				};
				function bottom(){
					var isno=$(outside.boxr).find(outside.boxlrX).hasClass(outside.boxon);
					var len=$(outside.boxr).children(outside.boxlrX).length;
					var index=$(outside.boxr).children(idclass+outside.boxon).index();
					var top=$(outside.boxr).children(outside.boxlrx).eq(index);
					if(isno){
						if(index+1<=len){	
							$(outside.boxr).children(outside.boxlrx).eq(index+1).after(top);
							
						}else{
							//alert("已经是最后一个了！！")
						};
					}else{
						//alert("请选择一个！");
					};
				};
				$(outside.boxlan).on("click",function(){
					left();
				});
				$(outside.boxran).on("click",function(){
					right();
				});
				$(outside.boxtan).on("click",function(){
					top();
				});
				$(outside.boxban).on("click",function(){
					bottom();
				});
				function allleft(){
					$(outside.boxr).find(outside.boxlrX).appendTo(outside.boxl);
				};
				function allright(){
					$(outside.boxl).find(outside.boxlrX).appendTo(outside.boxr);
				};
				function alltop(){
					var isno=$(outside.boxr).find(outside.boxlrX).hasClass(outside.boxon);
					var index=$(outside.boxr).children(idclass+outside.boxon).index();
					var top=$(outside.boxr).children(outside.boxlrx).eq(index);
					if(isno){
						if(index>0){
							$(outside.boxr).prepend(top);
							
						}else{
							//alert("当前已经是首个了！！")
						};
					}else{
						//alert("请选择一个！");
					}
				};
				function allbottom(){
					var isno=$(outside.boxr).find(outside.boxlrX).hasClass(outside.boxon);
					var len=$(outside.boxr).children(outside.boxlrX).length;
					var index=$(outside.boxr).children(idclass+outside.boxon).index();
					var top=$(outside.boxr).children(outside.boxlrx).eq(index);
					if(isno){
						if(index+1<len){	
							$(outside.boxr).append(top);
							
						}else{
							//alert("当前已经是最后了！！")
						};
					}else{
						//alert("请选择一个！");
					};
				};
				$(outside.boxalllan).on("click",function(){
					allleft();
				});
				$(outside.boxallran).on("click",function(){
					allright();
				});
				$(outside.boxalltan).on("click",function(){
					alltop();
				});
				$(outside.boxallban).on("click",function(){
					allbottom();
				});
			
			}
		});
	};

	$.fn.movelist.methods = {
		getlobj: function(jq){
			var options = $.data(jq[0],'moveoptions');
			return getlobj(option.boxl);
		},
		getrobj: function(jq){
			var options = $.data(jq[0],'moveoptions');
			return getrobj(options.boxr);
		},
		resetbox: function(jq,newlobj,newrobj){
			var options = $.data(jq[0],'moveoptions');
			resetbox(option.boxl,newlobj);
			resetbox(option.boxr,newrobj);
		}
	};
	/**
	 * listselbox
	 * 
	 */

	$.fn.listselbox = function(options){
		if(typeof options === "string")
			return $.fn.listselbox.methods[options](this);
		options = options||{};
		return this.each(function(){
			var opts;
			opts=$.extend(listb.options,options);
			listb.options = opts;
		});
	}
	
	$.fn.listselbox.defaultoptions = {
			box: "",
			allcheckbtn: "",
			upbtn: "",
			downbtn: "",
			onSelect: function(){},
			onCheck: function(){}
	};
	var gethzname = function(boxl){
		var lis = $(boxl).find('li');
		var obj = {};
		if(lis.length>0){
			for(var i=0;i<lis.length;i++){
				var key = $(lis[i]).data('key');
				var value = $(lis[i]).data('value');
				obj[key] = value;
			};
		}
		return obj;
	};
	$.fn.skyListBox.methods = {
			gethzname: function(jq){
				var options = $.data(jq[0],'skylistbox');
				var lis = $(options.hzlistbox).find('li.checked');
				var hzstr = "";
				if(lis.length>0){
					for(var i=0;i<lis.length;i++){
						var item =  $(lis[i]).data('listitem');
						var hzbj = item.hzbj;
						if(hzbj==1)
							hzstr += $(lis[i]).text()+"+";
					};
				}
				if(hzstr.length>0)
					hzstr = hzstr.substring(0,hzstr.length-1)
				return hzstr;
			},
			gethzsjname: function(jq){
				var options = $.data(jq[0],'skylistbox');
				var lis = $(options.hzlistbox).find('li.checked');
				var hzstr = "";
				if(lis.length>0){
					for(var i=0;i<lis.length;i++){
						var item =  $(lis[i]).data('listitem');
						var hzbj = item.hzbj;
						if(hzbj!=1)
							hzstr += $(lis[i]).text()+"+";
					};
				}
				if(hzstr.length>0)
					hzstr = hzstr.substring(0,hzstr.length-1)
				return hzstr;
			},
			gethzcolumns: function(jq,showhxcm){
				var options = $.data(jq[0],'skylistbox');
				var lis = $(jq[0]).find('li.checked');
				var columns = [];
				if(lis.length>0){
					for(var i=0;i<lis.length;i++){
						var col = $(lis[i]).data('listitem').col;
						var hzname = $(lis[i]).data('listitem').hzname;
						if(hzname=="尺码"&&showhxcm){
							continue;
						}else if((hzname=="数量"||hzname=="订货数"||hzname=="盈亏数")&&showhxcm){
							columns = columns.concat(getsizecolum());
							col.title = hzname + "合计";
						}else if((hzname=="数量"||hzname=="订货数"||hzname=="盈亏数")&&!showhxcm){
							col.title = hzname;
						}
						if(typeof(col) == "object" && col.constructor == Object){
							setColStyle(col);
							columns.push(col);
						}else if(typeof(col) == "object" && col.constructor == Array){
							for(var c=0;c<col.length;c++){
								setColStyle(col[c]);
							}
							columns = columns.concat(col);
						}
					};
				}
				return columns;
			},
			gethzlist: function(jq,ishz,showhxcm){
				var options = $.data(jq[0],'skylistbox');
				var lis = $(jq[0]).find('li.checked');
				if(ishz)
					lis = $(jq[0]).find('li.checked:not(.no_hz)');
				var list = {};
				var fieldlist = "";
				var grouplist = "";
				var sumlist = "";
				var calcfield = "";
				if(lis.length>0){
					for(var i=0;i<lis.length;i++){
						var item =  $(lis[i]).data('listitem');
						var hzbj = item.hzbj;
						var col = item.col;
						var hzname = item.hzname;
						if(typeof(col) == "object" && col.constructor == Object){
							if(ishz==false){
								fieldlist += col.mxfieldli+",";
							}else{
								if(hzname=="尺码" && showhxcm) continue;
								if(hzbj<=0){
									if(col.calcfield){
										calcfield += col.calcfield+",";
									}else if(col.hzfieldli){
										fieldlist += col.hzfieldli+",";
									}else
										fieldlist += col.mxfieldli+",";
								}else if(hzbj==1){
									if(col.hzfieldli)
										grouplist += col.hzfieldli+",";
									else
										grouplist += col.mxfieldli+",";
								}
							}
							if(col.sumlist)
								sumlist += col.sumlist+",";
							
						}
						else if(typeof(col) == "object" && col.constructor == Array){
							for(var c=0;c<col.length;c++){
								if(ishz==false){
									fieldlist += col[c].mxfieldli+",";
								}else{
									if(hzbj<=0){
										if(!col[c].calcfield){
											calcfield += col[c].calcfield+",";
										}else if(col[c].hzfieldli)
											fieldlist += col[c].hzfieldli+",";
										else
											fieldlist += col[c].mxfieldli+",";
											
									}else if(hzbj==1){
										if(col[c].hzfieldli)
											grouplist += col[c].hzfieldli+",";
										else
											grouplist += col[c].mxfieldli+",";
									}
								}
								if(col.sumlist)
									sumlist += col[c].sumlist+",";
							}
						}
					};
				}
				if(fieldlist.length>0)
					fieldlist = fieldlist.substring(0,fieldlist.length-1);
				if(grouplist.length>0){
					grouplist = grouplist.substring(0,grouplist.length-1);
					if(ishz&&grouplist.indexOf("a.noteno")>=0&&grouplist.indexOf("to_char(a.notedate,'yyyy-mm-dd') as notedate")>=0){
						grouplist = grouplist.replace("to_char(a.notedate,'yyyy-mm-dd') as notedate","a.notedate");
					}
				}
				if(calcfield.length>0){
					calcfield = calcfield.substring(0,calcfield.length-1);
				}
				if(sumlist.length>0)
					sumlist = sumlist.substring(0,sumlist.length-1);
				list = {
					fieldlist: fieldlist,
					grouplist: grouplist,
					calcfield: calcfield,
					sumlist: sumlist
				}
				return list;
			},
			selectAllhz: function(jq){
				var options = $.data(jq[0],'skylistbox');
				var $hzbox = $(options.hzlistbox);
				var ishz = $hzbox.data('ishz');
				if(!ishz){
					$hzbox.find('li:not(.only_hz) input[type="checkbox"]').prop('checked',true);
					$hzbox.find('li:not(.only_hz)').addClass('checked');
				}
				else{
					$hzbox.find('li:not(.no_hz) input[type="checkbox"]').prop('checked',true);
					$hzbox.find('li:not(.no_hz)').addClass('checked');
				}
			},
			unSelectAllhz: function(jq,ishz){
				var options = $.data(jq[0],'skylistbox');
				var $hzbox = $(options.hzlistbox);
				var ishz = $hzbox.data('ishz');
				if(!ishz){
					$hzbox.find('li:not(.disabled_hz) input[type="checkbox"]').removeProp('checked');
					$hzbox.find('li:not(.disabled_hz)').removeClass('checked');
				}else{
					$hzbox.find('li:not(.disabled_hz) input[type="checkbox"]').removeProp('checked');
					$hzbox.find('li:not(.disabled_hz)').removeClass('checked');
				}
			},
			selectAllhzsj: function(jq){
				
			},
			unSelectAllhzsj: function(jq){
				
			},
	}
	function setColStyle(col){
		var formatter = col.formatter;
		var styler = col.styler;
		col.formatter = eval(formatter);
		col.styler = eval(styler);
	}
})(jQuery);


/**调用参数**/
/**
boxl:null,//左边大盒子
boxr:null,//右边大盒子
boxlrX:null,//移动小盒子
boxon:null,//点击添加属性
multiselect:false,//是否启用多选默认是false,启用时true;
idclass:true,//添加的属性是否为class//true=class; false=id;
boxlan:null,//单个向左移动按钮
boxran:null,//单个向右移动按钮
boxtan:null,//单个向上移动按钮
boxban:null,//单个向下移动按钮
boxalllan:null,//批量向左移动按钮
boxallran:null,//批量向右移动按钮
boxalltan:null,//移动第一个按钮
boxallban:null,//移动最后一个按钮
leftobj: {},//左边默认填写的列表
rightobj: {} //右边默认的列表
**/