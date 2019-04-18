/**
 * shift+鼠标 选框放大拉框交互
 * alt+shift+鼠标 鼠标拖拽进行缩放和旋转地图
 * 右上角全屏按钮 
 * 农机信息获取 machineInfo_cn.hebau.wb.action.RdayAction.action  
 */
var map; 
var featureSelected=null;
var featureSelectedStyle=null;
var center =ol.proj.transform([115.45691666666667,38.92202777777778], 'EPSG:4326', 'EPSG:3857');//东北角;
var nowDaddress = null;
var historyIndex = 0;
//var center =ol.proj.transform([115.34367800,38.45852885], 'EPSG:4326', 'EPSG:3857');//东北角;
//alert(center);
//------------------------核心----------------------------
var defaults = {
    //url: '/ss/{z}/{x}/{y}.jpg',
	//url: 'http://211.68.180.9:9999/ss/{z}/{x}/{y}.jpg',
	skyUrl: 'http://211.68.183.51/gg/{z}/{x}/{y}.jpg',
	ggUrl: 'http://211.68.183.51/gg/{z}/{x}/{y}.jpg',
    mapExtent: [-20037508.3427892, -20037508.3427892, 20037508.3427892,20037508.3427892],
    mapMinZoom: 2,
    mapMaxZoom: 19,
    attribution: new ol.Attribution({
        html: 'Tiles © HebauMap'
    }),
    tilePixelRatio: 1,
    fromProject:"EPSG:102100", //"EPSG:4326"经纬度,//"EPSG:102100",//'EPSG:3857'球面
    toProject: "EPSG:3857"//toProject: "EPSG:4326"
};
//------------------------上0核心----------------------------
//图源
var source = new ol.source.XYZ({
			attributions :defaults.attribution,
			url : defaults.skyUrl,
			tilePixelRatio :defaults.tilePixelRatio,
			minZoom : defaults.mapMinZoom,
			maxZoom : defaults.mapMaxZoom
		});
var layer = new ol.layer.Tile({
				extent : ol.proj.transformExtent(defaults.mapExtent,defaults.fromProject, defaults.toProject),
				title: '天地图',
                type: 'base',
                visible: false,
				source : source
			});
var sourceGoogle = new ol.source.XYZ({
			attributions :defaults.attribution,
			url:defaults.ggUrl,
			tilePixelRatio :defaults.tilePixelRatio,
			minZoom : defaults.mapMinZoom,
			maxZoom : defaults.mapMaxZoom
		});
var layerGoogle = new ol.layer.Tile({
				extent : ol.proj.transformExtent(defaults.mapExtent,defaults.fromProject, defaults.toProject),
				title: '谷歌',
                type: 'base',
                visible: true,
				source : sourceGoogle
			});
var dragBoxInteraction = new ol.interaction.DragBox({
  condition: ol.events.condition.shiftKeyOnly,
  style: new ol.style.Style({
    stroke: new ol.style.Stroke({
      color: 'red',
      width: 2
    })
  })
});	
var dragRotateAndZoomInteraction=new ol.interaction.DragRotateAndZoom({condition: ol.events.condition.altShiftKeysOnly});
var interactions = ol.interaction.defaults({
			}).extend([dragRotateAndZoomInteraction]);			
var controls= ol.control.defaults({}).extend([
			new ol.control.ScaleLine(),
			new ol.control.FullScreen(),
			new ol.control.LayerSwitcher(),
			new ol.control.OverviewMap({})
		]);	
		
		

//轨迹图层
var vectorLayertrace = new ol.layer.Vector({
			source : new ol.source.Vector()
		});
var styleHeibei = new ol.style.Style({
  fill: new ol.style.Fill({ //矢量图层填充颜色，以及透明度
    color: 'rgba(255, 255, 255, 0)'//rgba 的最后一个变量就是控制透明度的变量，范围是 0~1，0 表示完全透明，1 代表不透明
  }),
  stroke: new ol.style.Stroke({ //边界样式
    color: '#FF3300',
    width: 1
  }),
  text: new ol.style.Text({ //文本样式
    font: '12px Calibri,sans-serif',
    fill: new ol.style.Fill({
      color: '#000'
    }),
    stroke: new ol.style.Stroke({
      color: '#fff',
      width: 3
    })
  })
});
var vectorLayerHebei = new ol.layer.Vector({ //初始化矢量图层
	visible: true,
  	source: new ol.source.Vector({
    projection: 'EPSG:3857',
    //format: new ol.format.GeoJSON(),
    //url: 'data/geojson/china.geojson'   //从文件加载边界等地理信息  里面是gps经纬度单位
    format: new ol.format.KML(),
    url: 'data/kml/hebei.kml'   //从文件加载边界等地理信息  里面是gps经纬度单位
  }),
  style: function(feature, resolution) {
    //styleHeibei.getText().setText(resolution < 5000 ? feature.get('name') : '');  //当放大到1:5000分辨率时，显示国家名字
    return [styleHeibei];
  }
});
var vectorLayerCity = new ol.layer.Vector({ //初始化矢量图层
	title: '城市边界',
	visible: false,
  	source: new ol.source.Vector({
	    projection: 'EPSG:3857',
	    //format: new ol.format.GeoJSON(),
	    //url: 'data/geojson/china.geojson'   //从文件加载边界等地理信息  里面是gps经纬度单位
	    format: new ol.format.KML(),
	    url: 'data/kml/city.kml'   //从文件加载边界等地理信息  里面是gps经纬度单位
	  })
});
////以下 控制city图层显示
/*
	var checkbox = document.querySelector('#visible');
	checkbox.addEventListener('change', function() {
	  var checked = this.checked;
	 // console.log("checked:"+checked);
	  if (checked !== vectorLayerCity.getVisible()) {
	    vectorLayerCity.setVisible(checked);
	  }
	});
	vectorLayerCity.on('change:visible', function() {
	  var visible = this.getVisible();
//	  		console.log("checked:"+visible);
	  if (visible !== checkbox.checked) {
	    checkbox.checked = visible;
	  }
	});
	vectorLayerCity.setVisible(false);
	*/
////以 上 控制city图层显示
////以下农机分布
var clusterStyleOn = new ol.style.Style({
					image : new ol.style.Icon({
								anchor : [0.5,0.5],
								src : 'images/on.png'
							})
				});
var clusterStyleSb = new ol.style.Style({
					image : new ol.style.Icon({
								anchor : [0.5,0.5],
								src : 'images/sb.png'
							})
				});				
var clusterStyleOff = new ol.style.Style({
					image : new ol.style.Icon({
								anchor : [0.5,0.5],
								src : 'images/off.png'
							})
				});	
var clusterStyleStop = new ol.style.Style({
					image : new ol.style.Icon({
								anchor : [0.5,0.5],
								src : 'images/stop.png'
							})
				});	
var clusterStyleSelected = new ol.style.Style({
					image : new ol.style.Icon({
								anchor : [0.5,0.5],
								src : 'images/select2.png'
							})
				});
function styleClusterFunction(feature, resolution) {
	var status = feature.get('status');
	//console.log(state);
//	if (!name) {
//          return [clusterStyle2];
//        }
     if(status==1){
     	return [clusterStyleOn];
     }else if(status==0){
     	return [clusterStyleSb];
     }else if(status==3){
     	return [clusterStyleStop];
     }else{
     	return [clusterStyleOff];
     }        
}
/*----------------点分布---------------------*/
var timestamp=new Date().getTime(); //时间戳  农机分布图层
var clusterLayer = new ol.layer.Vector({//960
    source: new ol.source.Vector({
    		projection: 'EPSG:3857',
			format: new ol.format.GeoJSON(),
			url: '../getAllServlet?t='+timestamp //url: 'data/geojson/cluster.geojson' //所有点地址
  }),
    style:styleClusterFunction
  }); 
/////以上农机分布  
function doZoom(factor) {
	  var resolution = map.getView().getResolution();
	  var zoom = ol.animation.zoom({
	    resolution: resolution,
	    duration:3000
	  });
	  map.beforeRender(zoom);
	  map.getView().setResolution(resolution * factor);	  
}

function doBounce(location) {
		  var bounce = ol.animation.bounce({
		    resolution: map.getView().getResolution() * 2
		  });
		  var pan = ol.animation.pan({
		    source: map.getView().getCenter()
		  });
		  map.beforeRender(bounce);
		  map.beforeRender(pan);
		  map.getView().setCenter(location);
	}
	
$("#eagleEye").click(function(){
	    		doZoom(0.052);
	    		map.getView().setCenter(center);
	}); 
$("#see").click(function(){
	    		doZoom(20);
	    		map.getView().setCenter(center);
	}); 	
	///以下轨迹展示
var coords=[];//存储当前农机最近一天的 所有轨迹点(球面坐标)	
var statuss=[];
var times=[];
function xyToBall(xy){
	var clb = eval("["+xy+"]")
	return ol.proj.transform(clb, 'EPSG:4326','EPSG:3857');
}
var animating = false;
var speed, now,indexgo=0;
var speedInput = document.getElementById('speed');
var startButton = document.getElementById('start-animation');
//var linepoints = new Array(); //行进中的轨迹点
	// 一组样式
	var styles = {
		'route' : new ol.style.Style({
					stroke : new ol.style.Stroke({
								width : 6,
								color : [255, 0, 0, 0.8]
							})
				}),
		'work' : new ol.style.Style({
					stroke : new ol.style.Stroke({
								width : 6,
								color : [255,255,0, 0.9]
							})
				}),
		'now' : new ol.style.Style({
					stroke : new ol.style.Stroke({
								width : 6,
								color : [0, 266, 0, 0.8]
							})
				}),
		'start' : new ol.style.Style({
					image : new ol.style.Icon({
								anchor : [0.5,1],
								src : 'images/trace-start.png'
							})
				}),
		'stop' : new ol.style.Style({
					image : new ol.style.Icon({
								anchor : [0.5,1],
								src : 'images/trace-end.png'
							})
				}),
		'goMarker' : new ol.style.Style({
					image : new ol.style.Circle({
								radius : 10,
								snapToPixel : false,
								fill : new ol.style.Fill({
											color : 'pink'
										}),
								stroke : new ol.style.Stroke({
											color : 'white',
											width : 2
										})
							})
				})
	};

var lineFeature,startMarker,endMarker;
	
	
var moveFeature = function(event) {
         var vectorContext = event.vectorContext;
         var frameState = event.frameState; 
         if (animating) {         	
           var elapsedTime = frameState.time - now;
           // here the trick to increase speed is to jump some indexes
           // on lineString coordinates
           var index = Math.round(speed * elapsedTime / 1000); 
           if (index >= coords.length) {
             stopAnimation(true);             
             return;
           }        
           
           var currentPoint = new ol.geom.Point(coords[index]);
           var feature = new ol.Feature(currentPoint);
           vectorContext.drawFeature(feature, styles.goMarker);   
           map.getView().setCenter(coords[index]);
           //linepoints.push(coords[index]); 
           var linepoints = coords.slice(0,index);
           var currentLine = new ol.geom.LineString(linepoints);
           var lineFeatureNow = new  ol.Feature(currentLine);
           vectorContext.drawFeature(lineFeatureNow, styles.route);
           //lineFeature = lineFeatureNow;
           //lineFeature.setStyle(styles['route']);
         }else{
         	//indexgo = indexgo+index;
         	//console.log("indexgo="+indexgo);         	
         }
         map.render();
     };	
 function startAnimation() {
 	//linepoints = new Array(); //清零开始
         if (animating) {
           stopAnimation(false);           
         } else {
         	//vectorLayer.getSource().removeFeature(lineFeature);
           animating = true;
           now = new Date().getTime();
           speed = speedInput.value;
           startButton.textContent = ' ■ ';
           // hide goMarker
           //goMarker.setStyle(null);
           // just in case you pan somewhere else
           //map.getView().setCenter(center);
           map.on('postcompose',moveFeature);	
           map.render();
         }
       }
   function stopAnimation(ended) {   
	   	//console.log("index="+index);
		vectorLayertrace.getSource().clear();
		   	// 轨迹图层		   	var linepoints = coords.slice(0,indexgo);
			lineFeature = new ol.Feature({
						type : 'route',
						geometry : new ol.geom.LineString(coords)
					});
			lineFeature.setStyle(styles['route']);
		   	vectorLayertrace.getSource().addFeature(lineFeature);
		   	vectorLayertrace.getSource().addFeature(startMarker);
			vectorLayertrace.getSource().addFeature(endMarker);
         	//remove listener
	     	map.un('postcompose', moveFeature);	    
	     animating = false;
	     startButton.textContent = '  ►  '; 
	     
   }
   //startButton.addEventListener('click', startAnimation, false);
//	$("#trace-history").click(function(){
//		
//		 
//         
//		
//		//window.open("../photo/tomato.jpg");
//		/*
//		var daddress =$("#machine-daddress").html();//"AABBCCDA";
//		$("#search_input").val(daddress);
//		historyTrace(daddress);		
//		*/
//	}); 
///////////////////////////////////////////////////实时轨迹	
	var intervalId, interval = 50, i = 0 ,playing=false,nowStartRid=0;
	var lineNowFeatureId=null,coordsNow=[];
$("#trace-now").click(function(){		
		var daddress =$("#machine-daddress").html();//"AABBCCDA";
		nowDaddress = $("#machine-daddress").html();//"AABBCCDA";
		$("#search_input").val(daddress);
		nowTrace(daddress);		
	}); 
$("#traceNowControl").click(function(){	
	if(playing){
		window.clearInterval(intervalId); 
		intervalId=null;
		lineNowFeatureId=null;
		coordsNow=[];
		$("#stop-nowTrace").html('  ►  ');
		playing=false;
		markerEl.setAttribute('data-hint', "暂停中......");
	}else{
		var daddress =$("#machine-daddress").html();//"AABBCCDA";
		nowDaddress = $("#machine-daddress").html();//"AABBCCDA";
		$("#search_input").val(daddress);
		nowTrace(daddress);	
	}
		
}); 
	//实时轨迹运动点
var markerEl = document.getElementById('geo-marker');
var nowMarker = new ol.Overlay({
    positioning: 'center-center',
    offset: [0, 0],
    element: markerEl,
    stopEvent: false
});
function animate(){
    nowMarker.setPosition(coords[i]);
    markerEl.setAttribute('data-hint', i + ' Km');
    //drawLineWithTwoPoints(coords[i],coords[i+1]);
    drawLineWithManyPoints(coords[i],vectorLayertrace);
    map.getView().setCenter(coords[i]);
    i++;
    if(i == coords.length){
    	coordsNow=[];
    	coordsNow.push(coords[0]);
        i = 1;
    }
}
var gogogoCords=[];
function gogogo(){
	//i++;
	$.ajax({
				   type: "POST",
				   //url: "$trace.action",
				   url: "CdateAction_gogoByCdate.action",
				   data: {"daddress":nowDaddress},//"start":nowStartRid,"query":i
				   dataType: "json", 
				   timeout : 3000,//3秒等待超时时间
				   success: function(data){
//				   		if(data.time==0){
//				   			console.log(nowDaddress+"无new轨迹数据！");
//				   			return;
//				   		}	
				   		$.each(data.coords, function(i, item) {
							var coord=xyToBall(eval("["+item.log+","+item.lat+"]"));
							gogogoCords.push(coord);
						});
						nowMarker.setPosition(gogogoCords[gogogoCords.length-1]);
    					markerEl.setAttribute('data-hint', data.time);
    					map.getView().setCenter(gogogoCords[gogogoCords.length-1]);
						drawLineWithNowStartPoint(gogogoCords);						
				   }
				});
	
    }
function nowTrace(daddress){	
			coords=[];//清0
			indexgo=0;//清0 记录行进中的坐标点
			linepoints = new Array(); //清零开始	
			msgOverlay.setPosition(undefined);
	$.ajax({
				   type: "POST",
				   //url: "$trace.action",
				   url: "CdateAction_traceToday.action",
				   data: {"daddress":daddress},
				   dataType: "json", 
				   success: function(data){
				   		if(data.id==0){
				   			alert(daddress+"无当天轨迹数据！");
				   			return;
				   		}				   		
				   		vectorLayertrace.getSource().clear();
				   		nowStartRid=data.id;
				   		$("#traceNowControl").show();
						$("#traceHistory").hide();
						//$("#popup").fadeOut(500,function(){popOverlay.setPosition(undefined);});
						popOverlay.setPosition(undefined);
						$.each(data.coords, function(i, item) {
							var coord=xyToBall(eval("["+item.log+","+item.lat+"]"));
							coords.push(coord);
						});
						//console.log("coords.length="+coords.length);
						map.getView().setZoom(16);
						center=coords[coords.length - 1];//以轨迹结束点为中心
						map.getView().setCenter(center);
											// 开始点
					startMarker = new ol.Feature({
										type : 'start',
										geometry : new ol.geom.Point(coords[0])
									});
					startMarker.setStyle(styles['start']);						
					// 结束点
					endMarker = new ol.Feature({
										type : 'stop',
										geometry : new ol.geom.Point(coords[coords.length - 1])
					});
					endMarker.setStyle(styles['stop']);
					vectorLayertrace.getSource().addFeature(startMarker);
					vectorLayertrace.getSource().addFeature(endMarker);
					
					lineFeature = new ol.Feature({
						type : 'route',
						geometry : new ol.geom.LineString(coords)
					});
					lineFeature.setStyle(styles['route']);
		   			vectorLayertrace.getSource().addFeature(lineFeature);
		   			
					//map.on('postcompose',nowFeature);
					//startAnimation();
					playing=true;
					$("#stop-nowTrace").html('  ■  ');
					//intervalId = setInterval(animate, interval);	
					gogogoCords=[];//初始化
					gogogoCords.push(coords[coords.length - 1]);
					intervalId = setInterval(gogogo, 4000);	
					  
				   }
				});
				
				
}
function drawLineWithNowStartPoint(newCoords){
			if(lineNowFeatureId){
						vectorLayertrace.getSource().removeFeature(lineNowFeatureId);  
			}
		   	lineNowFeatureId = new ol.Feature({
						type : 'now',
						geometry : new ol.geom.LineString(newCoords)
					});
			lineNowFeatureId.setStyle(styles['now']);
			vectorLayertrace.getSource().addFeature(lineNowFeatureId);
			map.render();
}

function drawLineWithTwoPoints(coord1,coord2){
			   		var coordsNow=[];
		   			coordsNow.push(coord1);
		   			coordsNow.push(coord2);
		   			var lineNowFeature = new ol.Feature({
						type : 'now',
						geometry : new ol.geom.LineString(coordsNow)
					});
					lineNowFeature.setStyle(styles['now']);
					vectorLayertrace.getSource().addFeature(lineNowFeature);
}

function drawLineWithManyPoints(coord){
					if(lineNowFeatureId){
						vectorLayertrace.getSource().removeFeature(lineNowFeatureId);  
					}
			   		//var coordsNow=[];
		   			//coordsNow.push(coord1);
		   			coordsNow.push(coord);
		   			lineNowFeatureId = new ol.Feature({
						type : 'now',
						id:'nowFeatureId',
						geometry : new ol.geom.LineString(coordsNow)
					});
					lineNowFeatureId.setStyle(styles['now']);
					vectorLayertrace.getSource().addFeature(lineNowFeatureId);
}
//track.log 经度 track.lat 纬度 track.sta 状态
function drawLineWorkOrStandBy(tracks){
	var workCoords=[];
	var standByCoords=[];
	var startCoord=xyToBall(eval("["+tracks[0].log+","+tracks[0].lat+"]"));
	if(tracks[0].sta==1){
		workCoords.push(startCoord);
	}else{
		standByCoords.push(startCoord);
	}
	
	for(var i=0;i<mcords.length;i++){
		
	}
}
/////////////以上实时轨迹//////////////////////
	function historyTrace(daddress){
		$.getJSON("RdayAction_listRday.action",
			{"daddress":daddress},
			function(data) {
				$("#traceHistory").show(); 
	        	$("#trackList").html("");//清空traceHistory内容
	        	$("#trackList").append("<tr><th>记录起始时间</th><th>记录结束时间</th><th>作业面积</th><th>轨迹点数</th><th>播放</th></tr>");
	        	$.each(data.realdays, function(i, item) {
	        		var rday = item.rdDay;	 
	        		//var playingTr = daddress+rday;
	        		var playingTr = daddress+item.rdStartTime.replaceAll(":","");
	        		var rdStartTime = item.rdStartTime;
	        		var rdEndTime = item.rdEndTime;
	           	 	$("#trackList").append(
	                    "<tr id='"+playingTr+"'><td>" + item.rdStartTime + "</td>" + 
	                    "<td>" + item.rdEndTime    + "</td>" + "<td>" + item.rdArea + "</td>" +
	                    "<td>" + item.rdTotalOutput + "</td><td onclick=\"trackQueryWithStatus('"+rday+"','"+rdStartTime+"','"+rdEndTime+"','"+daddress+"')\"><a href='#'> ► </a></td></tr>");
	            });
	        	$("#trackList").append("<tr><th><input class='workinput wicon mr25' id='inpstart' type='text' placeholder='请选择起始时间' readonly></th><th><input class='datainp' id='inpend' type='text' placeholder='请选择结束时间' readonly></th><th colspan='3'><a href='#' onclick=\"traceQueryByTime('"+daddress+"')\">查询</a></th></tr>");
	        	
	        	var start = {
				    dateCell: '#inpstart',
				    format: 'YYYY-MM-DD hh:mm:ss',
				    minDate: '2016-04-16 23:59:59', //最小日期
					festival:true,
				    maxDate: jeDate.now(0), //设定最大日期为当前日期
				    isTime: true,
				    isinitVal:true, 
				    choosefun: function(datas){
				         end.minDate = datas; //开始日选好后，重置结束日的最小日期
				    }
				};
				var end = {
				    dateCell: '#inpend',
				    format: 'YYYY-MM-DD hh:mm:ss',
				    minDate: '2016-04-16 23:59:59', //设定最小日期
					festival:true,
					isinitVal:true,
				    maxDate: jeDate.now(0), //最大日期
				    isTime: true,
				    choosefun: function(datas){
				         start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
				    }
				};
				jeDate(start);
				jeDate(end);	
				$("#popup").fadeOut(800,function(){popOverlay.setPosition(undefined);});
        });
    
	}
	
	function traceQueryByTime(daddress){
		var startTime = document.getElementById("inpstart").value;
		var endTime = document.getElementById("inpend").value;
		if(startTime==null||""==startTime||endTime==null||""==endTime){
			console.log("--time null");
			return;
		}
		/*			*/
		$.getJSON("RdayAction_traceQueryByTime.action",
			{"daddress":daddress,"startTime":startTime,"endTime":endTime},
			function(data) {
				$("#traceHistory").show();
	        	$("#trackList").html("");//清空traceHistory内容
	        	$("#trackList").append("<tr><th>记录起始时间</th><th>记录结束时间</th><th>作业面积</th><th>轨迹点数</th><th>播放</th></tr>");
	        	$.each(data.realdays, function(i, item) {
	        		var rday = item.rdDay;	 
	        		var playingTr = daddress+item.rdStartTime.replaceAll(":","");
	        		var rdStartTime = item.rdStartTime;
	        		var rdEndTime = item.rdEndTime;
	           	 	$("#trackList").append(
	                    "<tr id='"+playingTr+"'><td>" + item.rdStartTime + "</td>" + 
	                    "<td>" + item.rdEndTime    + "</td>" + "<td>" + item.rdArea + "</td>" +
	                    "<td>" + item.rdTotalOutput + "</td><td onclick=\"trackQueryWithStatus('"+rday+"','"+rdStartTime+"','"+rdEndTime+"','"+daddress+"')\"><a href='#'> ► </a></td></tr>");
	            });
	        	$("#trackList").append("<tr><th><input class='workinput wicon mr25' id='inpstart' type='text' placeholder='请选择起始时间' readonly></th><th><input class='datainp' id='inpend' type='text' placeholder='请选择结束时间' readonly></th><th colspan='3'><a href='#' onclick=\"traceQueryByTime('"+daddress+"')\">查询</a></th></tr>");
	        	
	        	var start = {
				    dateCell: '#inpstart',
				    format: 'YYYY-MM-DD hh:mm:ss',
				    minDate: '2016-04-16 23:59:59', //最小日期
					festival:true,
					isinitVal:true,
				    maxDate: jeDate.now(0), //设定最大日期为当前日期
				    isTime: true,
				    choosefun: function(datas){
				         end.minDate = datas; //开始日选好后，重置结束日的最小日期
				    }
				};
				var end = {
				    dateCell: '#inpend',
				    format: 'YYYY-MM-DD hh:mm:ss',
				    minDate: '2016-04-16 23:59:59', //设定最小日期
					festival:true,
					isinitVal:true,
				    maxDate: jeDate.now(0), //最大日期
				    isTime: true,
				    choosefun: function(datas){
				        start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
				    }
				};
				jeDate(start);
				jeDate(end);
        });
    
	}
	
	var playingTr;
	function trackQuery(rday,rdStartTime,rdEndTime,daddress){
		//console.log(rday+","+daddress);		
			//查询当天作业轨迹
			//var daddress = "AABBCCD1";
			//historyTrace(daddress);
			if(playingTr){
				$("#"+playingTr).removeClass("playingTr"); //移除p元素中值为"high"的class 
			}
			//playingTr = daddress+rday;
			playingTr = daddress+rdStartTime.replaceAll(":","");
			//var playingTrObj = document.getElementById(playingTr);	
			//playingTrObj.setAttribute("class","playingTr"); 			
			$("#"+playingTr).attr("class","playingTr");			
			$("#traceControl").show();				
			coords=[];//清0
			indexgo=0;//清0 记录行进中的坐标点
			linepoints = new Array(); //清零开始
			vectorLayertrace.getSource().clear();
			$.ajax({
				   type: "POST",
				   //url: "$trace.action",
				   url: "CdateAction_traceByTime.action",
				   data: {"daddress":daddress,"rday":rday,"start":rdStartTime,"end":rdEndTime},
				   success: function(msg){
				   		if("]"==msg){
				   			alert("realDate表无当天轨迹数据！");
				   			return;
				   		}
					   	var coordsXY= eval(msg);
					   	for(var i=0;i<coordsXY.length;i++){
							coords[i]=xyToBall(coordsXY[i]);		
						}
						//console.log("coords.length="+coords.length);
						map.getView().setZoom(16);
						//var centerIndex = parseInt(coords.length/2);
						center=coords[0];//以轨迹起点为中心
						map.getView().setCenter(center);
											// 开始点
					startMarker = new ol.Feature({
										type : 'start',
										geometry : new ol.geom.Point(coords[0])
									});
					startMarker.setStyle(styles['start']);						
					// 结束点
					endMarker = new ol.Feature({
										type : 'stop',
										geometry : new ol.geom.Point(coords[coords.length - 1])
					});
					endMarker.setStyle(styles['stop']);
					vectorLayertrace.getSource().addFeature(startMarker);
					vectorLayertrace.getSource().addFeature(endMarker);
					startAnimation();
				   }
				});				
	}
	function trackQueryWithStatus(rday,rdStartTime,rdEndTime,daddress){
		$("#inpstart").val(rdStartTime.replace("T"," "));
		$("#inpend").val(rdEndTime.replace("T"," "));
		//console.log(rday+","+daddress);		
			//查询当天作业轨迹
			//var daddress = "AABBCCD1";
			//historyTrace(daddress);
			if(playingTr){
				$("#"+playingTr).removeClass("playingTr"); //移除p元素中值为"high"的class 
			}
			//playingTr = daddress+rday;
			playingTr = daddress+rdStartTime.replaceAll(":","");
			//var playingTrObj = document.getElementById(playingTr);	
			//playingTrObj.setAttribute("class","playingTr"); 			
			$("#"+playingTr).attr("class","playingTr");							
			traceInit(true);
			$.ajax({
				   type: "POST",
				   //url: "$trace.action",
				   url: "CdateAction_traceByTimeWithStatus.action",
				   data: {"daddress":daddress,"rday":rday,"start":rdStartTime,"end":rdEndTime},
				   dataType: "json", 
				   success: function(data){
				   		if(data.id==0){
				   			alert(daddress+"无当天轨迹数据！");
				   			return;
				   		}	
				   		
				   		nowStartRid=data.id;
				   		$("#traceControl").show();	
						//$("#popup").fadeOut(500,function(){popOverlay.setPosition(undefined);});
						popOverlay.setPosition(undefined);						
						$.each(data.coords, function(i, item) {
							var coord=xyToBall(eval("["+item.log+","+item.lat+"]"));
							coords.push(coord);
							statuss.push(item.sta);
							times.push(item.t)
						});
						//console.log("coords.length="+coords.length);
						map.getView().setZoom(16);
						center=coords[0];//以轨迹起点为中心
						map.getView().setCenter(center);
											// 开始点
					startMarker = new ol.Feature({
										type : 'start',
										geometry : new ol.geom.Point(coords[0])
									});
					startMarker.setStyle(styles['start']);						
					// 结束点
					endMarker = new ol.Feature({
										type : 'stop',
										geometry : new ol.geom.Point(coords[coords.length - 1])
					});
					endMarker.setStyle(styles['stop']);
					vectorLayertrace.getSource().addFeature(startMarker);
					vectorLayertrace.getSource().addFeature(endMarker);
					playing=true;
					//drawLineWithStatus(coords,statuss,vectorLayertrace);	
					$("#start-animation").html('  ||  ');//html('  ■  ');					
					gogogoCords.push(coords[coords.length - 1]);					
					var timer = Math.round(4000/speedInput.value);
					intervalId = setInterval(gogoHistory, timer);					   		
					//console.log(timer);
					//startAnimation();
				   }
				});				
	}
	$("#speed").change(function(){		
		if(playing){
			var timer = Math.round(4000/speedInput.value);
			clearInterval(intervalId);
			intervalId = setInterval(gogoHistory, timer);
			//console.log(timer);
		}
		
	}); 
	$("#start-animation").click(function(){	
		//console.log("start-animation click="+playing);
		if(playing){			
			window.clearInterval(intervalId);
			$("#start-animation").html('  ►  ');//html('  ■  ');
			playing=false;
		}else{
			playing=true;
			$("#start-animation").html('  ||  ');
			var timer = Math.round(4000/speedInput.value);
			clearInterval(intervalId);
			if(historyIndex==0){
				msgOverlay.setPosition(undefined);
			}
			intervalId = setInterval(gogoHistory, timer);
			//console.log(timer);
			
		}
	}); 
	function gogoHistory(){
		
		if(historyIndex==coords.length-1&&playing){
			window.clearInterval(intervalId); 
			$("#start-animation").html('  ►  ');//html('  ■  ');
			historyIndex=0;
			playing=false;
			//msgOverlay.setPosition(undefined);
		}else{
			nowMarker.setPosition(coords[historyIndex+1]);
    		markerEl.setAttribute('data-hint', times[historyIndex+1]);    		
			drawLineWithTwoPointsStatus(coords[historyIndex],coords[historyIndex+1],statuss[historyIndex+1]);
			historyIndex++;
		}	
    }
    function drawLineWithTwoPointsStatus(coord1,coord2,status){
			   		var coordsNow=[];
		   			coordsNow.push(coord1);
		   			coordsNow.push(coord2);
		   			var lineNowFeature = new ol.Feature({
						type : 'now',
						geometry : new ol.geom.LineString(coordsNow)
					});
					if(status==0){
						map.getView().setCenter(coord1);
						map.render();
						lineNowFeature.setStyle(styles['route']);
					}else{
						lineNowFeature.setStyle(styles['work']);
						if(status>1){
							$("#msg-content").html(status);
							msgOverlay.setPosition(coord1);
							console.log(status+","+coord1);
						}
					}
					vectorLayertrace.getSource().addFeature(lineNowFeature);
}
	function drawLineWithStatus(coords,statuss,vectorLayer){
		var lineFeatureTemp;
		for(var i=0;i<coords.length-1;i++){
			var coordsTemp=[];
			coordsTemp.push(coords[i]);
			coordsTemp.push(coords[i+1]);
			lineFeatureTemp = new ol.Feature({
						type : 'route',
						geometry : new ol.geom.LineString(coordsTemp)
			});
			if(statuss[i]==1){
				lineFeatureTemp.setStyle(styles['work']);
			}else{
				lineFeatureTemp.setStyle(styles['route']);
			}
			vectorLayer.getSource().addFeature(lineFeatureTemp);
		}
		
	}
/**
 * 处理用户点击搜索按钮的事件
 * 获取输入，如果为空，什么都不做；如果不为空，首先检查输入，然后发送AJAX请求（使用JQuery）
 */
 function sendQuery(){
 	traceInit(true);
     var query = document.getElementById("search_input").value;      
     map.removeLayer(clusterLayer);
     clusterLayer = new ol.layer.Vector({
	    source: new ol.source.Vector({
	    		projection: 'EPSG:3857',
				format: new ol.format.GeoJSON(),
				url: 'open/CdateAction_machineQuery.action?t='+timestamp+"&query="+query
	  }),
	    style:styleClusterFunction
	  });  
	  map.addLayer(clusterLayer);
	   // 因为是异步加载，所以要采用事件监听的方式来判定是否加载完成
    var listenerKey = clusterLayer.getSource().on('change', function(){
        if (clusterLayer.getSource().getState() === 'ready') {    // 判定是否加载完成
            //document.getElementById('count').innerHTML = vectorLayer.getSource().getFeatures().length;
        	var features = clusterLayer.getSource().getFeatures();
			  	if(features[0]){			  		
			  		if(playing){
						window.clearInterval(intervalId); 
						$("#stop-nowTrace").html('  ►  ');
						playing=false;
					}
					intervalId=null;
					nowStartCoord=null;
					lineNowFeatureId=null;
					coordsNow=[];
					$("#traceNowControl").hide();
					nowMarker.setPosition(undefined);
					vectorLayertrace.getSource().clear();
					
		      		center = features[0].getGeometry().getFlatCoordinates();
			  		doBounce(center);
			  		$("#traceHistory").slideUp(); 
		      		//console.log(center);
			  	}else{
			  		//alert("未检索到与 "+query +" 有关的信息！");
			  		$("#dialog-msg").html("未检索到与<span> "+query +" </span>有关的信息！")
			  		showBg();
			  	}
            clusterLayer.getSource().unByKey(listenerKey); // 注销监听器
        }
    });
 }

	var container = document.getElementById('popup');
	var content = document.getElementById('popup-content');
	var closer = document.getElementById('popup-closer');	
	var popOverlay = new ol.Overlay(/** @type {olx.OverlayOptions} */ ({
	  element: container,
	  positioning: 'bottom-center',
	  autoPan: true,
	  autoPanAnimation: {
	    duration: 300   //当Popup超出地图边界时，为了Popup全部可见，地图移动的速度.单位为毫秒（ms） 
	  }
	}));
	closer.onclick = function() {
	  popOverlay.setPosition(undefined);
	  closer.blur();
	  return false;
	}; 
	
	var msgSend = document.getElementById('msg-send');
	var msgOverlay = new ol.Overlay( ({
	  element: msgSend,
	  positioning: 'bottom-center',
	  autoPan: true,
	  autoPanAnimation: {
	    duration: 300   //当Popup超出地图边界时，为了Popup全部可见，地图移动的速度.单位为毫秒（ms） 
	  }
	}));	
	
$(document).ready(function() {
	/*程序入口*/
	map = new ol.Map({	
		interactions:interactions,
		controls:controls,
		target: 'map',
		layers: [
			/*基础图层，谷歌图层，河北省边界线，4.点信息图层*/
			layer,layerGoogle,vectorLayerHebei,clusterLayer,vectorLayertrace,vectorLayerCity
                
		],
		logo: {src: 'images/map.png', href: 'map.html'},
		view: new ol.View({
			center: center,
			zoom: 7 //显示全省最佳7级 (1920*1200分辨率下)
		})
	});
	
	//var layerSwitcher = new ol.control.LayerSwitcher({
   //     tipLabel: '切换图层' // Optional label for button
   // });
    map.addOverlay(nowMarker);	 
    map.addOverlay(msgOverlay);
	//msgOverlay.setPosition(center);
   // map.addControl(layerSwitcher);	
    //官方提供好的事件，map事件
	map.addEventListener('click', function(evt) {
		if(featureSelected){
			featureSelected.setStyle(featureSelectedStyle);
		}
		var coordinate = evt.coordinate;		
        var pixel = map.getPixelFromCoordinate(coordinate);
        var features = [];
        map.forEachFeatureAtPixel(pixel, function(feature) {
//        	if(feature.get('status')){
//        		feature.setStyle(clusterStyleSelected);
//        	}
        	features.push(feature);
//        	var objeto = feature.getProperties();
//        	if(objeto['status']){//因为有多个图层，读取有state属性的，即图表图层，不要省市分界线图层
//				alert(objeto['name']);
//			}
        });
        if (features.length > 0) {
			  for (var i = 0; i< features.length;i++) {
				if(features[i].get('status')){
					featureSelected=features[i];
					featureSelectedStyle=featureSelected.getStyle();
					featureSelected.setStyle(clusterStyleSelected);
					var daddress = features[i].get('address');
					center = features[i].getGeometry().getFlatCoordinates();
					doBounce(center);		
					//jquary提供的异步获取json数据的方法
					$.getJSON("../InfoServlet",//农机信息获取！！！
						{"daddress":daddress},
						function(data) {
							//console.log(data.minfo);
							$("#machine-photo").attr('src',"../photo/tomato.jpg"); 
							/*
							if(data.minfo.photo!=null&&data.minfo.photo!=""){
								$("#machine-photo").attr('src',"../photo/machine/"+data.minfo.photo); 
							}
							
							if(data.minfo.status==0){
								$("#machine-status-img").attr('src',"images/sb.png");
								$("#machine-status").html("待机");
							}else if(data.minfo.status==1){
								$("#machine-status-img").attr('src',"images/on.png");
								$("#machine-status").html("工作");
							}else if(data.minfo.status==3){
								$("#machine-status-img").attr('src',"images/stop.png");
								$("#machine-status").html("24小时内关机");
							}else{
								$("#machine-status-img").attr('src',"images/off.png");
								$("#machine-status").html("超过24小时未开机");
							}
							*/
							//alert(data.minfo.owner);
							//console.log(data.minfo.owner);
							/*<% System.out.print(data.minfo.owner); %>*/
							$("#machine-owner").html(data.owner+"&nbsp<img src='images/phone.png'/> "+data.phone+"&nbsp"+data.name);							
							//$("#machine-time").html(data.minfo.time);
							//$("#machine-temperature").html(data.minfo.temperature);
							//$("#machine-humidity").html(data.minfo.humidity);
							$("#machine-address").html(data.address);//在小点上附加id信息，已便在点击时发送id，查询到更多信息
//							$("#trace-history").click(function(){
//								window.open("../TraceServlet?daddress"+daddress);
//						            
					
//						         
//								
//								
//							});
						});
					/*
					$.ajax({
					   type: "POST",
					   url: "$detail.action",
					   //url: "machineInfo_cn.hebau.wb.action.RdayAction.action",
					   data: {"daddress":daddress},
					   success: function(msg){
					     //content.innerHTML = msg;
					   }
					});*/
					//container.style.display = 'block';						
					popOverlay.setPosition(center);
					map.addOverlay(popOverlay);
					$("#popup").fadeIn(1000,function(){  });
					break;
				}
			  }
			  
		} 
	});

});

$("#search_button").click(function(){
	sendQuery();	
}); 
$('#search_input').bind('keypress',function(event){
            if(event.keyCode == "13")    
            {
                sendQuery();
            }
        });
$("#search_button").hover(function(){
	//$("#search-tip").show();
	$("#search-tip").animate({opacity:"show", top: "-58"}, "slow");
},function(){
	$("#search-tip").animate({opacity: "hide", top: "-75"}, "fast");
}); 

function traceInit(clearFeature){
	if(clearFeature){
		vectorLayertrace.getSource().clear();
	}
	if(intervalId){
		window.clearInterval(intervalId); 
	}			
	$("#start-animation").html('  ►  ');//html('  ■  ');
	gogogoCords=[];//初始化
	historyIndex=0;
	playing=false;
	msgOverlay.setPosition(undefined);	
	nowMarker.setPosition(undefined);
	coords=[];//清0
	statuss=[];
	times=[];
	indexgo=0;//清0 记录行进中的坐标点
	linepoints = new Array(); //清零开始
}



