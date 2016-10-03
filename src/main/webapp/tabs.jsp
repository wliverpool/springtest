<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jquery Ui Tab</title>
<link rel="stylesheet" href="css/jquery-ui-1.10.1.custom.min.css">
<link rel="stylesheet" href="css/jqueryuistyle.css">
<style>
#dialog label, #dialog input {
	display: block;
}
#dialog label {
	margin-top: 0.5em;
}
#dialog input, #dialog textarea {
	width: 95%;
}
#tabs {
	margin-top: 1em;
}
#tabs li .ui-icon-close {
	float: left;
	margin: 0.4em 0.2em 0 0;
	cursor: pointer;
}
#add_tab {
	cursor: pointer;
}
</style>
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.1.custom.min.js"></script>
<script>
	$(function() {
		var tabTitle = $( "#tab_title" ),tabUrlTitle = $( "#tab_url_title" ),
		tabContent = $( "#tab_content" ),tabUrlContent = $( "#tab_url" ),
		tabTemplate = "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span></li>",
		tabUrlTemplate = "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span></li>",
		tabCounter = 2;
		var tabs = $("#tabs").tabs({
			beforeLoad : function(event, ui) {//加载失败
				ui.jqXHR.fail(function() {
					ui.panel.html("Couldn't load this tab. We'll try to fix this as soon as possible. If this wouldn't be a demo.");
				});
			},
			//event : "mouseover",//切换选项卡的事件，默认为'click'
			collapsible : true//可折叠tab
		});
		tabs.find( ".ui-tabs-nav" ).sortable({
			axis: "x",
			stop: function() {
		  		tabs.tabs( "refresh" );
			}
		});
		//点击add tab按钮后显示的模式对话框
		var dialog = $("#dialog").dialog({
			autoOpen : false,
			modal : true,
			buttons : {
				Add : function() {
					//调用新增tab的函数
					addTab();
					$(this).dialog("close");
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			},
			close : function() {
				form[0].reset();
			}
		});
		var urldialog = $("#urldialog").dialog({
			autoOpen : false,
			modal : true,
			buttons : {
				Add : function() {
					//调用新增tab的函数
					addURLTab();
					$(this).dialog("close");
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			},
			close : function() {
				form1[0].reset();
			}
		});
		//添加tab的form,在关闭或者提交add tab的dialog时触发addTab();函数
	    var form = dialog.find("form").on("submit", function(event) {
			addTab();
			dialog.dialog("close");
			event.preventDefault();
	    });
	    var form1 = urldialog.find("form").on("submit", function(event) {
			addTab();
			dialog.dialog("close");
			event.preventDefault();
	    });
		//新增tab函数
		function addTab() {
			//设置tab的label属性
			var label = tabTitle.val() || "Tab " + tabCounter;
			var id = "tabs-" + tabCounter;
			var li = $( tabTemplate.replace( /#\{href\}/g, "#" + id ).replace( /#\{label\}/g, label ));
			var tabContentHtml = tabContent.val() || "Tab " + tabCounter + " content.";
			//在现有tab控件最后追加一个tab页
			tabs.find( ".ui-tabs-nav" ).append( li );
			tabs.append( "<div id='" + id + "'><p>" + tabContentHtml + "</p></div>" );
			//刷行整个tab控件
			tabs.tabs("refresh");
			//增加tab页的个数
			tabCounter++;
		}
		function addURLTab() {
			//设置tab的label属性
			var label = tabUrlTitle.val() || "Tab " + tabCounter;
			var url = tabUrlContent.val();
			var id = "tabs-" + tabCounter;
			var li = $( tabUrlTemplate.replace( /#\{href\}/g, url ).replace( /#\{label\}/g, label ));
			alert(li.href);
			var tabContentHtml = tabContent.val() || "Tab " + tabCounter + " content.";
			//在现有tab控件最后追加一个tab页
			tabs.find( ".ui-tabs-nav" ).append( li );
			tabs.append( "<div id='ui-" + id + "'></div>" );
			//刷行整个tab控件
			tabs.tabs("refresh");
			//增加tab页的个数
			tabCounter++;
		}
		//点击add_tab按钮时弹出add tab的dialog
		$( "#add_tab" ).button().on( "click", function() {
			dialog.dialog("open");
		});
		$( "#add_url_tab" ).button().on( "click", function() {
			urldialog.dialog("open");
		});
		//点击tab页的关闭图标触发的事件,从整个tab控件中移除当前tab页
		tabs.on("click","span.ui-icon-close", function() {
			var panelId = $( this ).closest( "li" ).remove().attr( "aria-controls" );
			$( "#" + panelId ).remove();
			tabs.tabs("refresh");
		});
		tabs.on("keyup", function( event ) {
			if ( event.altKey && event.keyCode === $.ui.keyCode.BACKSPACE ) {
				var panelId = tabs.find( ".ui-tabs-active" ).remove().attr( "aria-controls" );
				$( "#" + panelId ).remove();
				tabs.tabs("refresh");
			}
		});
	});
</script>
</head>
<body>
	<div id="dialog" title="Tab data">
		<form>
			<fieldset class="ui-helper-reset">
				<label for="tab_title">Title</label> 
				<input type="text" name="tab_title" id="tab_title" value="Tab Title" class="ui-widget-content ui-corner-all"> 
				<label for="tab_content">Content</label>
				<textarea name="tab_content" id="tab_content" class="ui-widget-content ui-corner-all">Tab content</textarea>
			</fieldset>
		</form>
	</div>
	<div id="urldialog" title="url tab data">
		<form>
			<fieldset class="ui-helper-reset">
				<label for="tab_url_title">Title</label> 
				<input type="text" name="tab_url_title" id="tab_url_title" value="Tab Title" class="ui-widget-content ui-corner-all"> 
				<label for="tab_url">Tab url</label>
				<textarea name="tab_url" id="tab_url" class="ui-widget-content ui-corner-all">url</textarea>
			</fieldset>
		</form>
	</div>
	<button id="add_tab">Add Tab</button>
	<button id="add_url_tab">Add URL Tab</button>
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">Nunc tincidunt</a></li>
			<li><a href="lazyload1.jsp">Proin dolor</a></li>
			<li><a href="lazyload.jsp">Aenean lacinia</a></li>
		</ul>
		<div id="tabs-1">
			<p>Proin elit arcu, rutrum commodo, vehicula tempus, commodo a,
				risus. Curabitur nec arcu. Donec sollicitudin mi sit amet mauris.
				Nam elementum quam ullamcorper ante. Etiam aliquet massa et lorem.
				Mauris dapibus lacus auctor risus. Aenean tempor ullamcorper leo.
				Vivamus sed magna quis ligula eleifend adipiscing. Duis orci.
				Aliquam sodales tortor vitae ipsum. Aliquam nulla. Duis aliquam
				molestie erat. Ut et mauris vel pede varius sollicitudin. Sed ut
				dolor nec orci tincidunt interdum. Phasellus ipsum. Nunc tristique
				tempus lectus.</p>

		</div>
	</div>
</body>
</html>