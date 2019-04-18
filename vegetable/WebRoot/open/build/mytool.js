String.prototype.replaceAll = function(s1,s2){
　　return this.replace(new RegExp(s1,"gm"),s2);
　　}
function ChangeToDFM()
	{
		var du = document.getElementById("input_du").value;
		var str1 = du.split(".");
		var du1 = str1[0];
		var tp = "0."+str1[1]
		var tp = String(tp*60);		//这里进行了强制类型转换
		var str2 = tp.split(".");
		var fen =str2[0];
		tp = "0."+str2[1];
		tp = tp*60;
		var miao = tp;
		document.getElementById("calculated_DFM").innerHTML = du1+"°"+fen+"'"+miao+"\"";
	}
	
	function ChangeToDu()
	{
		var d = document.getElementById("input_dfm1").value;
		var f = document.getElementById("input_dfm2").value;
		var m = document.getElementById("input_dfm3").value;
		
		var f = parseFloat(f) + parseFloat(m/60);
		var du = parseFloat(f/60) + parseFloat(d);
		document.getElementById("calculated_du").innerHTML = du;
	}          
            function numberToDegree(value) {  
                ///<summary>将度转换成为度分秒</summary>      
                value = Math.abs(value);  
                var v1 = Math.floor(value);//度  
                var v2 = Math.floor((value - v1) * 60);//分  
                var v3 = Math.round((value - v1) * 3600 % 60);//秒  
                return v1 + '°' + v2 + '\'' + v3 + '"';  
            };                
            function degreeToNumber(value)  
            { ///<summary>度分秒转换成为度</summary>  
                var du = value.split("°")[0];  
                var fen = value.split("°")[1].split("'")[0];  
                var miao = value.split("°")[1].split("'")[1].split('"')[0];    
                //return Math.abs(du) + "." + (Math.abs(fen)/60 + Math.abs(miao)/3600);                     
  				return Math.abs(du) + (Math.abs(fen)/60 + Math.abs(miao)/3600); 
            }
            function degreeConvert(L,B){
            	return "["+degreeToNumber(L)+","+degreeToNumber(B)+"]";
            }
 //            alert("度转化成为度分秒,这个会靠成精度丢失 从第三位小数开始丢失" +DegreeConvertBack( formatDegree("121.31123456")));  
 
  //显示灰色 jQuery 遮罩层
function showBg() {
	var bh = $("body").height();
	var bw = $("body").width();
	$("#fullbg").css({
	height:bh,
	width:bw,
	display:"block"
	});
	//$("#dialog").show();
	$("#dialog").slideDown();
}
//关闭灰色 jQuery 遮罩
function closeBg() {
	//$("#fullbg,#dialog").hide();
	$("#fullbg,#dialog").slideUp();
} 


$.extend({
    /**
     * 调用方法： var timerArr = $.blinkTitle.show();
     *     $.blinkTitle.clear(timerArr);
     */
    blinkTitle : {
      show : function(divId) { //有新消息时在title处闪烁提示
        var step=0, _title = document.title;
        var timer = setInterval(function() {
          step++;
          if (step==3) {step=1};
          if (step==1) {$("#"+divId).hide();};
          if (step==2) {$("#"+divId).show();};
        }, 400);
        return [timer];
      },
      /**
       * @param timerArr[0], timer标记
       * @param timerArr[1], 初始的title文本内容
       */
      clear : function(timer,divId) {
      //去除闪烁提示，恢复初始title文本
        if(timer) {
          clearInterval(timer); 
          $("#"+divId).show();
        };
      }
    }
  });