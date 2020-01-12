/*!
  * Bolg main JS.
 */
"use strict";
//# sourceURL=main.js
 
// DOM 加载完再执行
$(function() {
	var _pageSize; // 存储用于搜索
	// 根据用户名、页面索引、页面大小获取用户列表
	function getUersByName(pageIndex, pageSize) {
		 $.ajax({ 
			 url: "/users", 
			 contentType : 'application/json',
			 data:{
				 "async":true, 
				 "pageIndex":pageIndex,
				 "pageSize":pageSize,
				 "name":$("#searchName").val()
			 },
			 success: function(data){
				 $("#mainContainer").html(data);
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	}
	//查询
	$(".queryButton").click(function(){
		//alert($(".custom-select").val());
		$("input[name='pageSize']").val($(".custom-select").val());
		//alert($("a.active").attr("pageIndex"));
		$("input[name='pageIndex']").val($("a.active").attr("pageIndex"));
		$("form[name='query_form']").submit();
	});
	//重置
	$(".restButton").click(function(){
		//alert($(".custom-select").val());
		$(".query-form input[type='text']").val("");
		$(".query-form input[type='number']").val("");
	});
	//分页查询
	$(".tbpage-item").click(function(){
		//alert($(this).attr('class'));
		var $class = $(this).attr('class');
		//包含active 是当前页
		//indexOf方法返回一个整数值,$class中的字符位置是从0开始的。如果没有找到子字符串，则返回 -1
		if($class.indexOf("active")!=-1){
			//alert($(this).attr('class'));
			return ;
		}else{//否则不包含active， 不是当前页
			//alert($(this).attr('pageIndex'));
			var $index = $(this).attr('pageIndex');
			var $totalPages = $("#totalPages").val();
			//alert($index);
			//alert($totalPages);
			if($index <= -1 || $index >= $totalPages){
				return ;
			}else{
				$("input[name='pageSize']").val($(".custom-select").val());
				$("input[name='pageIndex']").val($index);
				$("form[name='query_form']").submit(); //提交
			}
		}
	});

	// 刪除文件
	$(".blog-delete-file").click(function () { 
		// 获取 CSRF Token 		
//		var csrfToken = $("meta[name='_csrf']").attr("content");
//		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		var affirm=confirm("确认删除吗？");
		if(affirm == true){
			$.ajax({ 
				 url: "/" + $(this).attr("fileId") , 
				 type: 'DELETE', 
//				 beforeSend: function(request) {
//	                 request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token 
//	             },
				 success: function(data){
					 location.reload();
//					 if (data.success) {
//						 alert('de')
//						 // 从新刷新主界面
//						 //getUersByName(0, _pageSize);
//					 } else {
//						 alert('de2')
//						 //toastr.error(data.message);
//					 }
			     },
			     error : function() {
			    	 toastr.error("error!");
			     }
			 });
		}
	});
});