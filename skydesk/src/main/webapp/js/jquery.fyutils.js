(function($) {
	var ms = {
		init: function(obj, args) {
			return (function() {
				if (args.autorefresh) {
					args.timer = setInterval(function() {
						var $dialog = $('.easyui-dialog');
						var b = true;
						$dialog.each(function() {
							var bool = $(this).parent().is(":hidden"); //是否有弹窗；
							if (!bool) {
								b = false;
								return false;
							}
						});
						if (b) obj.find('span.refresh').click();
					},
					60000);
				}
				ms.fillHtml(obj, args);
				ms.bindEvent(obj, args);
			})();
		},
		//填充html
		fillHtml: function(obj, options) {
			return (function() {
				obj.empty();
				var args = $.extend($(obj).data('pageArgs'), options);
				$(obj).data('pageArgs', args);
				//自动刷新
				if (args.autorefresh) {
					var selhtml = '<span class="autorefresh">自动' + '<select>' + '<option value="0">不</option>' + '<option value="30">30s</option>' + '<option value="60">1m</option>' + '<option value="120">2m</option>' + '<option value="300">5m</option>' + '</select>刷新</span>';
					obj.append(selhtml);
					obj.find('span select').val(args.refreshtime);
				}
				//上一页
				obj.append('<span class="refresh">刷新</span>');
				if (args.current > 1) {
					obj.append('<a href="javascript:;" class="prevPage">上一页</a>');
				} else {
					obj.remove('.prevPage');
					obj.append('<span class="disabled">上一页</span>');
				}
				//中间页码
				if (args.current != 1 && args.current >= 4 && args.pageCount != 4) {
					obj.append('<a href="javascript:;" class="tcdNumber">' + 1 + '</a>');
				}
				if (args.current - 2 > 2 && args.current <= args.pageCount && args.pageCount > 5) {
					obj.append('<span>...</span>');
				}
				var start = args.current - 2,
				end = args.current + 2;
				if ((start > 1 && args.current < 4) || args.current == 1) {
					end++;
				}
				if (args.current > args.pageCount - 4 && args.current >= args.pageCount) {
					start--;
				}
				for (; start <= end; start++) {
					if (start <= args.pageCount && start >= 1) {
						if (start != args.current) {
							obj.append('<a href="javascript:;" class="tcdNumber">' + start + '</a>');
						} else {
							obj.append('<span class="current">' + start + '</span>');
						}
					}
				}
				if (args.current + 2 < args.pageCount - 1 && args.current >= 1 && args.pageCount > 5) {
					obj.append('<span>...</span>');
				}
				if (args.current != args.pageCount && args.current < args.pageCount - 2 && args.pageCount != 4) {
					obj.append('<a href="javascript:;" class="tcdNumber">' + args.pageCount + '</a>');
				}
				//下一页
				if (args.current < args.pageCount) {
					obj.append('<a href="javascript:;" class="nextPage">下一页</a>');
				} else {
					obj.remove('.nextPage');
					obj.append('<span class="disabled">下一页</span>');
				}
				obj.append('<span class="topage">第<input type="text" class="topagetext" value="' + args.current + '">页</span>');
			})();
		},
		//绑定事件
		bindEvent: function(obj, args) {
			return (function() {
				obj.on("click", "a.tcdNumber",
				function() {
					var current = parseInt($(this).text());
					var pc = obj.find('a.tcdNumber').length + 1;
					ms.fillHtml(obj, {
						current: current,
						pageCount: pc
					});
					//					ms.fillHtml(obj,{current:current,pageCount:args.pageCount});
					if (typeof(args.backFn) == "function") {
						args.backFn(current);
					}
				});
				//上一页
				obj.on("click", "a.prevPage",
				function() {
					var current = parseInt(obj.children("span.current").text());
					var pc = obj.find('a.tcdNumber').length + 1;
					ms.fillHtml(obj, {
						current: current - 1,
						pageCount: pc
					});
					//					ms.fillHtml(obj,{current:current-1,pageCount:args.pageCount});
					if (typeof(args.backFn) == "function") {
						args.backFn(current - 1);
					}
					//					var current = obj.children("span.current");
					//					var prev = current.prev();
					//					prev.click();
				});
				//下一页
				obj.on("click", "a.nextPage",
				function() {
					var current = parseInt(obj.children("span.current").text());
					var pc = obj.find('a.tcdNumber').length + 1;
					ms.fillHtml(obj, {
						current: current + 1,
						pageCount: pc
					});
					//					ms.fillHtml(obj,{current:current+1,pageCount:args.pageCount});
					if (typeof(args.backFn) == "function") {
						args.backFn(current + 1);
					}
					//					var current = obj.children("span.current");
					//					var next = current.next();
					//					next.click();
				});
				//刷新
				obj.on("click", "span.refresh",
				function() {
					var current = parseInt(obj.children("span.current").text());
					current = isNaN(current) ? 1 : current;
					if (typeof(args.backFn) == "function") {
						args.backFn(current);
					}
				});
				if (args.autorefresh) {
					obj.on("change", "span select",
					function() {
						var timer = obj.data('pageArgs').timer;
						obj.data('pageArgs').refreshtime = this.value;
						var t = obj.data('pageArgs').refreshtime * 1000;
						clearInterval(timer);
						if (t != 0) {
							obj.find('span.refresh').click();
							obj.data('pageArgs').timer = setInterval(function() {
								var $dialog = $('.easyui-dialog');
								var b = true;
								$dialog.each(function() {
									var bool = $(this).parent().is(":hidden"); //是否有弹窗；
									if (!bool) {
										b = false;
										return false;
									}
								});
								if (b) obj.find('span.refresh').click();
							},
							t);
						}
					});
				}
				//跳转
				obj.on("keyup", "span.topage .topagetext",
				function(e) {
					this.value = this.value.replace(/[^\d]/g, "");
					var pg = Number(this.value);
					var pgcount = 1;
					var pgcount1 = Number(obj.find('.tcdNumber').last().html());
					var pgcount2 = Number(obj.find('.current').html());
					if (isNaN(pgcount1)) {
						pgcount = pgcount2;
					} else {
						pgcount = pgcount1;
					}
					if (pg > pgcount) {
						this.value = pgcount;
						pg = pgcount;
					}
					if (e.which == 13) {
						if (typeof(args.backFn) == "function") {
							args.backFn(pg);
						}
					}

				}).on("change", "span.topage .topagetext",
				function() {
					var pg = Number(this.value);
					if (typeof(args.backFn) == "function") {
						args.backFn(pg);
					}
				});
			})();
		}
	}
	$.fn.createPage = function(options) {
		var args = $.extend({
			autorefresh: false,
			pageCount: 10,
			current: 1,
			refreshtime: 60,
			backFn: function() {},
			refresh: function() {}
		},
		options);
		ms.init(this, args);
	}
	$.fn.setPage = function(options) {
		ms.fillHtml(this, options);
	}
	$.fn.refreshPage = function() {
		var obj = $(this);
		obj.find('span.refresh').click();
	}
	$.fn.getPage = function() {
		var obj = $(this);
		var page = Number(obj.find('span.current').html());
		if (isNaN(page)) return 1;
		else return page;
	}

	$.fn.dragsort = function(options) {
		if (options == "destroy") {
			$(this.selector).trigger("dragsort-uninit");
			return;
		}

		var opts = $.extend({},
		$.fn.dragsort.defaults, options);
		var lists = [];
		var list = null,
		lastPos = null;

		this.each(function(i, cont) {

			//if list container is table, the browser automatically wraps rows in tbody if not specified so change list container to tbody so that children returns rows as user expected
			if ($(cont).is("table") && $(cont).children().size() == 1 && $(cont).children().is("tbody")) cont = $(cont).children().get(0);

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
					opts.tagName = $(this.container).children().size() == 0 ? "li": $(this.container).children().get(0).tagName.toLowerCase();
					if (opts.itemSelector == "") opts.itemSelector = opts.tagName;
					if (opts.dragSelector == "") opts.dragSelector = opts.tagName;
					if (opts.placeHolderTemplate == "") opts.placeHolderTemplate = "<" + opts.tagName + ">&nbsp;</" + opts.tagName + ">";

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
					this.getItems().map(function() {
						return $(this).is(opts.dragSelector) ? this: $(this).find(opts.dragSelector).get();
					}).css("cursor", cursor ? "pointer": "");
				},

				grabItem: function(e) {
					var list = lists[$(this).attr("data-listidx")];
					var item = $(e.target).closest("[data-listidx] > " + opts.tagName).get(0);
					var insideMoveableItem = list.getItems().filter(function() {
						return this == item;
					}).size() > 0;

					//if not left click or if clicked on excluded element (e.g. text box) or not a moveable list item return
					if (e.which != 1 || $(e.target).is(opts.dragSelectorExclude) || $(e.target).closest(opts.dragSelectorExclude).size() > 0 || !insideMoveableItem) return;

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
					$(list.container).mousemove(trigger).mouseup(function() {
						$(list.container).unbind("mousemove", trigger);
						$(dragHandle).css("cursor", $(dragHandle).attr("data-cursor"));
					});
				},

				dragStart: function(e) {
					if (list != null && list.draggedItem != null) list.dropItem();

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
						list.draggedItem.children().each(function() {
							$(this).width($(this).width());
						});
						list.placeHolderItem = list.draggedItem.clone().attr("data-placeholder", true);
						list.draggedItem.after(list.placeHolderItem);
						list.placeHolderItem.children().each(function() {
							$(this).css({
								borderWidth: 0,
								width: $(this).width() + 1,
								height: $(this).height() + 1
							}).html("&nbsp;");
						});
					} else {
						list.draggedItem.after(opts.placeHolderTemplate);
						list.placeHolderItem = list.draggedItem.next().css({
							height: h,
							width: w
						}).attr("data-placeholder", true);
					}

					if (opts.tagName == "td") {
						var listTable = list.draggedItem.closest("table").get(0);
						$("<table id='" + listTable.id + "' style='border-width: 0px;' class='dragSortItem " + listTable.className + "'><tr></tr></table>").appendTo("body").children().append(list.draggedItem);
					}

					//style draggedItem while dragging
					var orig = list.draggedItem.attr("style");
					list.draggedItem.attr("data-origstyle", orig ? orig: "");
					list.draggedItem.css({
						position: "absolute",
						opacity: 0.8,
						"z-index": 999,
						height: h,
						width: w
					});

					//auto-scroll setup
					list.scroll = {
						moveX: 0,
						moveY: 0,
						maxX: $(document).width() - $(window).width(),
						maxY: $(document).height() - $(window).height()
					};
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
					},
					10);
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
					},
					10);

					//misc
					$(lists).each(function(i, l) {
						l.createDropTargets();
						l.buildPositionTable();
					});
					list.setPos(e.pageX, e.pageY);
					$(document).bind("mousemove", list.swapItems);
					$(document).bind("mouseup", list.dropItem);
					if (opts.scrollContainer != window) $(window).bind("wheel", list.wheel);
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
					this.draggedItem.css({
						top: top,
						left: left
					});
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
					if (list.draggedItem == null) return;

					//list.draggedItem.attr("style", "") doesn't work on IE8 and jQuery 1.5 or lower
					//list.draggedItem.removeAttr("style") doesn't work on chrome and jQuery 1.6 (works jQuery 1.5 or lower)
					var orig = list.draggedItem.attr("data-origstyle");
					list.draggedItem.attr("style", orig);
					if (orig == "") list.draggedItem.removeAttr("style");
					list.draggedItem.removeAttr("data-origstyle");

					list.styleDragHandlers(true);

					list.placeHolderItem.before(list.draggedItem);
					list.placeHolderItem.remove();

					$("[data-droptarget], .dragSortItem").remove();

					window.clearInterval(list.scroll.scrollY);
					window.clearInterval(list.scroll.scrollX);

					//if position changed call dragEnd
					if (list.draggedItem.attr("data-origpos") != $(lists).index(list) + "-" + $(list.container).children().index(list.draggedItem)) if (opts.dragEnd.apply(list.draggedItem) == false) { //if dragEnd returns false revert order
						var pos = list.draggedItem.attr("data-origpos").split('-');
						var nextItem = $(lists[pos[0]].container).children().not(list.draggedItem).eq(pos[1]);
						if (nextItem.size() > 0) nextItem.before(list.draggedItem);
						else if (pos[1] == 0) //was the only item in list
						$(lists[pos[0]].container).prepend(list.draggedItem);
						else //was the last item in list
						$(lists[pos[0]].container).append(list.draggedItem);
					}
					list.draggedItem.removeAttr("data-origpos");

					list.draggedItem = null;
					$(document).unbind("mousemove", list.swapItems);
					$(document).unbind("mouseup", list.dropItem);
					if (opts.scrollContainer != window) $(window).unbind("wheel", list.wheel);
					return false;
				},

				//swap the draggedItem (represented visually by placeholder) with the list item the it has been dragged on top of
				swapItems: function(e) {
					if (list.draggedItem == null) return false;

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
					if (ei == -1) return false;

					//save fixed items locations
					var children = function() {
						return $(nlist.container).children().not(nlist.draggedItem);
					};
					var fixed = children().not(opts.itemSelector).each(function(i) {
						this.idx = children().index(this);
					});

					//if moving draggedItem up or left place placeHolder before list item the dragged item is hovering over otherwise place it after
					if (lastPos == null || lastPos.top > list.draggedItem.offset().top || lastPos.left > list.draggedItem.offset().left) $(nlist.pos[ei].elm).before(list.placeHolderItem);
					else $(nlist.pos[ei].elm).after(list.placeHolderItem);

					//restore fixed items location
					fixed.each(function() {
						var elm = children().eq(this.idx).get(0);
						if (this != elm && children().index(this) < this.idx) $(this).insertAfter(elm);
						else if (this != elm) $(this).insertBefore(elm);
					});

					//misc
					$(lists).each(function(i, l) {
						l.createDropTargets();
						l.buildPositionTable();
					});
					lastPos = list.draggedItem.offset();
					return false;
				},

				//returns the index of the list item the mouse is over
				findPos: function(x, y) {
					for (var i = 0; i < this.pos.length; i++) {
						if (this.pos[i].left < x && this.pos[i].right > x && this.pos[i].top < y && this.pos[i].bottom > y) return i;
					}
					return - 1;
				},

				//create drop targets which are placeholders at the end of other lists to allow dragging straight to the last position
				createDropTargets: function() {
					if (!opts.dragBetween) return;

					$(lists).each(function() {
						var ph = $(this.container).find("[data-placeholder]");
						var dt = $(this.container).find("[data-droptarget]");
						if (ph.size() > 0 && dt.size() > 0) dt.remove();
						else if (ph.size() == 0 && dt.size() == 0) {
							if (opts.tagName == "td") $(opts.placeHolderTemplate).attr("data-droptarget", true).appendTo(this.container);
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
		dragEnd: function() {},
		dragBetween: false,
		placeHolderTemplate: "",
		scrollContainer: window,
		scrollSpeed: 5
	};
})(jQuery);
/*! http://mths.be/placeholder v2.0.8 by @mathias */
(function(window, document, $) {
	// Opera Mini v7 doesn’t support placeholder although its DOM seems to indicate so
	var isOperaMini = Object.prototype.toString.call(window.operamini) == '[object OperaMini]';
	var isInputSupported = 'placeholder' in document.createElement('input') && !isOperaMini;
	var isTextareaSupported = 'placeholder' in document.createElement('textarea') && !isOperaMini;
	var prototype = $.fn;
	var valHooks = $.valHooks;
	var propHooks = $.propHooks;
	var hooks;
	var placeholder;

	if (isInputSupported && isTextareaSupported) {

		placeholder = prototype.placeholder = function() {
			return this;
		};

		placeholder.input = placeholder.textarea = true;

	} else {

		placeholder = prototype.placeholder = function() {
			var $this = this;
			$this.filter((isInputSupported ? 'textarea': ':input') + '[placeholder]').not('.placeholder').on({
				'focus.placeholder': clearPlaceholder,
				'blur.placeholder': setPlaceholder
			}).data('placeholder-enabled', true).trigger('blur.placeholder');
			return $this;
		};

		placeholder.input = isInputSupported;
		placeholder.textarea = isTextareaSupported;

		hooks = {
			'get': function(element) {
				var $element = $(element);

				var $passwordInput = $element.data('placeholder-password');
				if ($passwordInput) {
					return $passwordInput[0].value;
				}

				return $element.data('placeholder-enabled') && $element.hasClass('placeholder') ? '': element.value;
			},
			'set': function(element, value) {
				var $element = $(element);

				var $passwordInput = $element.data('placeholder-password');
				if ($passwordInput) {
					return $passwordInput[0].value = value;
				}

				if (!$element.data('placeholder-enabled')) {
					return element.value = value;
				}
				if (value == '') {
					element.value = value;
					// Issue #56: Setting the placeholder causes problems if the element continues to have focus.
					if (element != safeActiveElement()) {
						// We can't use `triggerHandler` here because of dummy text/password inputs :(
						setPlaceholder.call(element);
					}
				} else if ($element.hasClass('placeholder')) {
					$element.removeClass('placeholder'); //j s赋值变化
					clearPlaceholder.call(element, true, value) || (element.value = value);
				} else {
					element.value = value;
				}
				// `set` can not return `undefined`; see http://jsapi.info/jquery/1.7.1/val#L2363
				return $element;
			}
		};

		if (!isInputSupported) {
			valHooks.input = hooks;
			propHooks.value = hooks;
		}
		if (!isTextareaSupported) {
			valHooks.textarea = hooks;
			propHooks.value = hooks;
		}

		$(function() {
			// Look for forms
			$(document).delegate('form', 'submit.placeholder',
			function() {
				// Clear the placeholder values so they don't get submitted
				var $inputs = $('.placeholder', this).each(clearPlaceholder);
				setTimeout(function() {
					$inputs.each(setPlaceholder);
				},
				10);
			});
			$('input[type=text]').placeholder();
		});

		// Clear placeholder values upon page reload
		$(window).bind('beforeunload.placeholder',
		function() {
			$('.placeholder').each(function() {
				this.value = '';
			});
		});

	}

	function args(elem) {
		// Return an object of element attributes
		var newAttrs = {};
		var rinlinejQuery = /^jQuery\d+$/;
		$.each(elem.attributes,
		function(i, attr) {
			if (attr.specified && !rinlinejQuery.test(attr.name)) {
				newAttrs[attr.name] = attr.value;
			}
		});
		return newAttrs;
	}

	function clearPlaceholder(event, value) {
		var input = this;
		var $input = $(input);
		if (input.value == $input.attr('placeholder') && $input.hasClass('placeholder')) {
			if ($input.data('placeholder-password')) {
				$input = $input.hide().next().show().attr('id', $input.removeAttr('id').data('placeholder-id'));
				// If `clearPlaceholder` was called from `$.valHooks.input.set`
				if (event === true) {
					return $input[0].value = value;
				}
				$input.focus();
			} else {
				$input.removeClass('placeholder');
				input.value = '';
				$input.val('');
				input == safeActiveElement() && input.select();
			}
		}
	}

	function setPlaceholder() {
		var $replacement;
		var input = this;
		var $input = $(input);
		var id = this.id;
		if (input.value == '' && $input.attr("readonly") != "readonly") {
			if (input.type == 'password') {
				if (!$input.data('placeholder-textinput')) {
					try {
						$replacement = $input.clone().attr({
							'type': 'text'
						});
					} catch(e) {
						$replacement = $('<input>').attr($.extend(args(this), {
							'type': 'text'
						}));
					}
					$replacement.removeAttr('name').data({
						'placeholder-password': $input,
						'placeholder-id': id
					}).bind('focus.placeholder', clearPlaceholder);
					$input.data({
						'placeholder-textinput': $replacement,
						'placeholder-id': id
					}).before($replacement);
				}
				$input = $input.removeAttr('id').hide().prev().attr('id', id).show();
				// Note: `$input[0] != input` now!
			}
			if (!$input.hasClass(placeholder)) {
				$input.addClass('placeholder');
				$input[0].value = $input.attr('placeholder');
			}
		} else {
			if ($input.hasClass(placeholder) && $input.value != $input.attr('placeholder')) {
				$input.removeClass('placeholder').removeClass('placeholder');
			}
		}
	}

	function safeActiveElement() {
		// Avoid IE9 `document.activeElement` of death
		// https://github.com/mathiasbynens/jquery-placeholder/pull/99
		try {
			return document.activeElement;
		} catch(exception) {}
	}
} (this, document, jQuery));

/*
 * jQuery resize event - v1.1 - 3/14/2010
 * http://benalman.com/projects/jquery-resize-plugin/
 * 
 * Copyright (c) 2010 "Cowboy" Ben Alman
 * Dual licensed under the MIT and GPL licenses.
 * http://benalman.com/about/license/
 * if(!r){r={w:q.width(),h:q.height()}}
 */
(function($, h, c) {
	var a = $([]),
	e = $.resize = $.extend($.resize, {}),
	i,
	k = "setTimeout",
	j = "resize",
	d = j + "-special-event",
	b = "delay",
	f = "throttleWindow";
	e[b] = 250;
	e[f] = true;
	$.event.special[j] = {
		setup: function() {
			if (!e[f] && this[k]) {
				return false
			}
			var l = $(this);
			a = a.add(l);
			$.data(this, d, {
				w: l.width(),
				h: l.height()
			});
			if (a.length === 1) {
				g()
			}
		},
		teardown: function() {
			if (!e[f] && this[k]) {
				return false
			}
			var l = $(this);
			a = a.not(l);
			l.removeData(d);
			if (!a.length) {
				clearTimeout(i)
			}
		},
		add: function(l) {
			if (!e[f] && this[k]) {
				return false
			}
			var n;
			function m(s, o, p) {
				var q = $(this),
				r = $.data(this, d);
				if(r){
					r.w = o !== c ? o: q.width();
					r.h = p !== c ? p: q.height();
					n.apply(this, arguments)
				}else{
					r = {};
					r.w = q.width();
					r.h = q.height();
					n.apply(this, arguments)
				}
			}
			if ($.isFunction(l)) {
				n = l;
				return m
			} else {
				n = l.handler;
				l.handler = m
			}
		}
	};
	function g() {
		i = h[k](function() {
			a.each(function() {
				var n = $(this),
				m = n.width(),
				l = n.height(),
				o = $.data(this, d);
				if (m !== o.w || l !== o.h) {
					n.trigger(j, [o.w = m, o.h = l])
				}
			});
			g()
		},
		e[b])
	}
})(jQuery, this);
/*!
 * jquery.base64.js 0.0.3 - https://github.com/yckart/jquery.base64.js
 * Makes Base64 en & -decoding simpler as it is.
 *
 * Based upon: https://gist.github.com/Yaffle/1284012
 *
 * Copyright (c) 2012 Yannick Albert (http://yckart.com)
 * Licensed under the MIT license (http://www.opensource.org/licenses/mit-license.php).
 * 2013/02/10
 **/
;(function($) {
    var b64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",
        a256 = '',
        r64 = [256],
        r256 = [256],
        i = 0;

    var UTF8 = {

        /**
         * Encode multi-byte Unicode string into utf-8 multiple single-byte characters
         * (BMP / basic multilingual plane only)
         *
         * Chars in range U+0080 - U+07FF are encoded in 2 chars, U+0800 - U+FFFF in 3 chars
         *
         * @param {String} strUni Unicode string to be encoded as UTF-8
         * @returns {String} encoded string
         */
        encode: function(strUni) {
            // use regular expressions & String.replace callback function for better efficiency
            // than procedural approaches
            var strUtf = strUni.replace(/[\u0080-\u07ff]/g, // U+0080 - U+07FF => 2 bytes 110yyyyy, 10zzzzzz
            function(c) {
                var cc = c.charCodeAt(0);
                return String.fromCharCode(0xc0 | cc >> 6, 0x80 | cc & 0x3f);
            })
            .replace(/[\u0800-\uffff]/g, // U+0800 - U+FFFF => 3 bytes 1110xxxx, 10yyyyyy, 10zzzzzz
            function(c) {
                var cc = c.charCodeAt(0);
                return String.fromCharCode(0xe0 | cc >> 12, 0x80 | cc >> 6 & 0x3F, 0x80 | cc & 0x3f);
            });
            return strUtf;
        },

        /**
         * Decode utf-8 encoded string back into multi-byte Unicode characters
         *
         * @param {String} strUtf UTF-8 string to be decoded back to Unicode
         * @returns {String} decoded string
         */
        decode: function(strUtf) {
            // note: decode 3-byte chars first as decoded 2-byte strings could appear to be 3-byte char!
            var strUni = strUtf.replace(/[\u00e0-\u00ef][\u0080-\u00bf][\u0080-\u00bf]/g, // 3-byte chars
            function(c) { // (note parentheses for precence)
                var cc = ((c.charCodeAt(0) & 0x0f) << 12) | ((c.charCodeAt(1) & 0x3f) << 6) | (c.charCodeAt(2) & 0x3f);
                return String.fromCharCode(cc);
            })
            .replace(/[\u00c0-\u00df][\u0080-\u00bf]/g, // 2-byte chars
            function(c) { // (note parentheses for precence)
                var cc = (c.charCodeAt(0) & 0x1f) << 6 | c.charCodeAt(1) & 0x3f;
                return String.fromCharCode(cc);
            });
            return strUni;
        }
    };

    while(i < 256) {
        var c = String.fromCharCode(i);
        a256 += c;
        r256[i] = i;
        r64[i] = b64.indexOf(c);
        ++i;
    }

    function code(s, discard, alpha, beta, w1, w2) {
        s = String(s);
        var buffer = 0,
            i = 0,
            length = s.length,
            result = '',
            bitsInBuffer = 0;

        while(i < length) {
            var c = s.charCodeAt(i);
            c = c < 256 ? alpha[c] : -1;

            buffer = (buffer << w1) + c;
            bitsInBuffer += w1;

            while(bitsInBuffer >= w2) {
                bitsInBuffer -= w2;
                var tmp = buffer >> bitsInBuffer;
                result += beta.charAt(tmp);
                buffer ^= tmp << bitsInBuffer;
            }
            ++i;
        }
        if(!discard && bitsInBuffer > 0) result += beta.charAt(buffer << (w2 - bitsInBuffer));
        return result;
    }

    var Plugin = $.base64 = function(dir, input, encode) {
            return input ? Plugin[dir](input, encode) : dir ? null : this;
        };

    Plugin.btoa = Plugin.encode = function(plain, utf8encode) {
        plain = Plugin.raw === false || Plugin.utf8encode || utf8encode ? UTF8.encode(plain) : plain;
        plain = code(plain, false, r256, b64, 8, 6);
        return plain + '===='.slice((plain.length % 4) || 4);
    };

    Plugin.atob = Plugin.decode = function(coded, utf8decode) {
        coded = String(coded).split('=');
        var i = coded.length;
        do {--i;
            coded[i] = code(coded[i], true, r64, a256, 6, 8);
        } while (i > 0);
        coded = coded.join('');
        return Plugin.raw === false || Plugin.utf8decode || utf8decode ? UTF8.decode(coded) : coded;
    };
}(jQuery));
window.console = window.console || (function() {
    var c = {};
    c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile = c.clear = c.exception = c.trace = c.assert = function() {}
    ;
    return c;
})();
//日期格式化
Date.prototype.Format = function(fmt) {
	var o = {
		"M+": this.getMonth() + 1,
		"d+": this.getDate(),
		"h+": this.getHours(),
		"m+": this.getMinutes(),
		"s+": this.getSeconds(),
		"q+": Math.floor((this.getMonth() + 3) / 3),
		"S": this.getMilliseconds()
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o) if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt
}
function getCookie(c_name) {
		if (document.cookie.length > 0) {
			var c_start = document.cookie.indexOf(c_name + "=");
			if (c_start != -1) {
				c_start = c_start + c_name.length + 1;
				var c_end = document.cookie.indexOf(";", c_start);
				if (c_end == -1)
					c_end = document.cookie.length;
				var res  = decodeURI(document.cookie.substring(c_start, c_end)).replace(/ /g,"").replace(/\"/g,"");
				return res=="\"\""?"":res;
			}
		}
		return "";
	}

	function getCookieObj(c_name){
		var res = {};
		try{
			if (document.cookie.length > 0) {
				var c_start = document.cookie.indexOf(c_name + "=");
				if (c_start != -1) {
					c_start = c_start + c_name.length + 1;
					var c_end = document.cookie.indexOf(";", c_start);
					if (c_end == -1)
						c_end = document.cookie.length;
					var resstr = document.cookie.substring(c_start, c_end).replace(/\"/g,"");
					res = $.parseJSON($.base64.decode(resstr,true));
				}
			}
		}catch(e){
			 res = {};
		}
		return res;
	}
	function getValuebyName(name){
		var value = skyuser[name];
		if(value)
			return value;
		return "";
	}
	function setCookie(c_name, value, expiredays){
		var exdate = new Date();
		exdate.setDate(exdate.getDate() + expiredays);
		document.cookie = c_name + "=" + encodeURI(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString()) + ";path=/";  
	}

	function checkCookie(c_name) {
		var c_value = getCookie(c_name);
		if (c_value && c_value != undefined && c_value!=null && c_value!='' && c_value!='undefined')
			return true;
		else {
			return false;
		}
	}

	function clearCookie(c_name){
		c_value = getCookie(c_name);
		if (c_value && c_value != undefined && c_value!=null && c_value!='' && c_value!='undefined')
			setCookie(c_name, "", "0");
		else {
		}
	}

	function clearAllCookie(){ 
		var keys=document.cookie.match(/[^ =;]+(?=\=)/g); 
		if (keys) { 
			for (var i = keys.length; i--;) 
				document.cookie=keys[i]+'=0;expires=' + new Date(0).toUTCString()+ ";path=/";  
		} 
	} 
	function clearAllCookiebyPath(path){ 
		var keys=document.cookie.match(/[^ =;]+(?=\=)/g); 
		if (keys) { 
			for (var i = keys.length; i--;) 
				document.cookie=keys[i]+'=0;expires=' + new Date(0).toUTCString()+ ";path=" + path;  
		} 
	} 
	function valideData(data,showErrMsg) {
		if(alerthide) alerthide();
		if (data.syserror != undefined) {
			var date = new Date();
			var hours = date.getHours();
			var m = date.getMinutes();
			var errmsg = "";
			if (hours >=2 && hours<4 || (hours==4 && m <= 30))
				errmsg = "02:00-04:30为系统维护时间，请稍后登录重试！";
			else errmsg = data.syserror;
			var st = setTimeout(function(){
				top.window.location.href = "/skydesk/loginservlet?doservlet=logoff";
			},30000);
			$.messager.alert('警告',errmsg,"error",function(){
				top.window.location.href = "/skydesk/loginservlet?doservlet=logoff";
				clearTimeout(st);
			});
			return false
		} else if (data.result != undefined) {
			var res = Number(data.result);
			if(data.warning!=undefined&&data.warning.length>0) alerttext(data.warning);
			if (res > 0) {
				return true
			} else {
				if(showErrMsg!=false) 
					alerttext(data.msg);
				setTimeout(function() {
					return false
				},
				2000)
			}
		} else if (data.total != undefined) {
			return true
		}
	}
	/**
	 * 设置与获取较短的参数
	 */
	var setparam = function(obj) {
		var userid = Number(getValuebyName("EPID")).toString();
		var usection = "CS" + userid + "^" + obj.pgid;
		$.ajax({
			type: "POST",
			url: "/skydesk/fyjerp",
			data: {
				erpser: "writeparameter",
				usection: usection,
				usymbol: obj.usymbol,
				uvalue: obj.uvalue
			},
			dataType: 'json',
			success: function(data) {
				if (valideData(data)) {}
			}
		})
	};
	var getparam = function(obj) {
		var userid = Number(getValuebyName("EPID")).toString();
		var usection = "CS" + userid + "^" + obj.pgid;
		var uvalue = "";
		$.ajax({
			type: "POST",
			url: "/skydesk/fyjerp",
			data: {
				erpser: "getparameter",
				usection: usection,
				usymbol: obj.usymbol
			},
			async: false,
			dataType: 'json',
			success: function(data) {
				try {
					if (valideData(data)) {
						var total = data.total;
						if (total > 0) {
							var rows = data.rows;
							uvalue = rows[0].UVALUE
						}
					}
				} catch(e) {
					console.error(e.message)
				}
			}
		});
		return uvalue
	};
	/**
	 * 设置与获取超长的参数
	 */
	var setparamx = function(obj) {
		var userid = Number(getValuebyName("EPID")).toString();
		var usection = "CS" + userid + "^" + obj.pgid;
		$.ajax({
			type: "POST",
			url: "/skydesk/fyjerp",
			data: {
				erpser: "writeparameterx",
				usection: usection,
				usymbol: obj.usymbol,
				uvalue: obj.uvalue
			},
			dataType: 'json',
			success: function(data) {
				if (valideData(data)) {}
			}
		})
	};
	var getparamx = function(obj) {
		var userid = Number(getValuebyName("EPID")).toString();
		var usection = "CS" + userid + "^" + obj.pgid;
		var uvalue = "";
		$.ajax({
			type: "POST",
			url: "/skydesk/fyjerp",
			data: {
				erpser: "getparameterx",
				usection: usection,
				usymbol: obj.usymbol
			},
			async: false,
			dataType: 'json',
			success: function(data) {
				try {
					if (valideData(data)) {
						var total = data.total;
						if (total > 0) {
							var rows = data.rows;
							uvalue = rows[0].UVALUE
						}
					}
				} catch(e) {
					console.error(e.message)
				}
			}
		});
		return uvalue
	};
	//获取程序json值
	var getfilejson = function(key) {
		var url = "/skydesk/jsonfiles/" + key;
		var resjson = null;
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			async: false,
			url: url,
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {},
			dataType: 'json',
			success: function(data) {
				resjson = data;
			}
		});
		return resjson;
	}
	//获取程序json值
	var getjsonfile = function(progid, key) {
		var url = "/skydesk/jsonfiles/" + key;
		var resjson = null;
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			async: false,
			url: url,
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {},
			dataType: 'json',
			success: function(data) {
				resjson = data[progid];
			}
		});
		return resjson;
	}
var amchartcolors = [
	"#1ba8b5",
	"#ffc95f",
	"#34b2e4",
	"#ff9934",
	"#e44856",
	"#64d0da",
	"#065381",
	"#0174b8"
];