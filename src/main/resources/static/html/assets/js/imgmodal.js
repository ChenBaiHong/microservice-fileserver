
/*$('.bh-button').click(function(){
  $('.pop-up').addClass('open');
});
*/
$('.blog-view-file').click(function(){
	// 获取图片id
	var url= "/view/" + $(this).attr("fileId");
	//ajax 请求二进制流 图片 文件 XMLHttpRequest 请求并处理二进制流数据
	readStreamImg (url)
});
$('.pop-up .close').click(function(){
  $('.pop-up').removeClass('open');
});

//ajax 请求二进制流 图片 文件 XMLHttpRequest 请求并处理二进制流数据
function readStreamImg(url){
	var xhr = new XMLHttpRequest();    
    xhr.open("get", url, true);
    xhr.responseType = "blob";
    xhr.onload = function() {
        if (this.status == 200) {
            var blob = this.response;
            var img = document.createElement("img");
            img.onload = function(e) {
              window.URL.revokeObjectURL(img.src); 
            };
            img.src = window.URL.createObjectURL(blob);
			$("#faviconimg").attr("src" , img.src);
			// $("#faviconimg").html(img);
			$('.pop-up').addClass('open');
        }
    }
    xhr.send();
}

