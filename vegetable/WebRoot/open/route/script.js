var olview = new ol.View({
    center: [-5681968.124542255, -3482094.286934089],
    zoom: 10,
    minZoom: 2,
    maxZoom: 20
});

var sourceFeatures = new ol.source.Vector(),
    layerFeatures = new ol.layer.Vector({
        source: sourceFeatures
    });

var lineString = new ol.geom.LineString([]);
var layerRoute = new ol.layer.Vector({
    source: new ol.source.Vector({
        features: [
            new ol.Feature({ geometry: lineString })
        ]
    }),
    style: [
        new ol.style.Style({
            stroke: new ol.style.Stroke({
                width: 3,
                color: [40, 40, 40, 1],
                lineDash: [.1, 5]
            }),
            zIndex: 2
        })
    ],
    updateWhileAnimating: true
});

var map = new ol.Map({
    target: 'map',
    loadTilesWhileAnimating: true,
    loadTilesWhileInteracting: true,
    view: olview,
    renderer: 'canvas',
    layers: [
        new ol.layer.Tile({
            style: 'Aerial',
            source: new ol.source.MapQuest({
                layer: 'osm'
            }),
            opacity: .8
        }),
        layerRoute, layerFeatures
    ]
});

var markerEl = document.getElementById('geo-marker');
var marker = new ol.Overlay({
    positioning: 'center-center',
    offset: [0, 0],
    element: markerEl,
    stopEvent: false
});
map.addOverlay(marker);

var fill = new ol.style.Fill({color:[255,255,255,1]}),
    stroke = new ol.style.Stroke({color:[0,0,0,1]}),
    style_parada = [
        new ol.style.Style({
            image: new ol.style.Icon({
                scale: .7, opacity: 1,
                rotateWithView: false, anchor: [0.5, 1],
                anchorXUnits: 'fraction', anchorYUnits: 'fraction',
                src: '//raw.githubusercontent.com/jonataswalker/map-utils/master/images/marker.png'
            }), zIndex: 5
        }),
        new ol.style.Style({
            image: new ol.style.Circle({
                radius: 6, fill: fill, stroke: stroke
            }), zIndex: 4
        })
    ];

window.onload = function(){
    loadAnimation();
};
function createFeature(data){
    var feature, hint_address, hint_address_overlay;
   // var i=1;
    data.forEach(function(row){
        //console.log(i++);
        feature = new ol.Feature({
            desc: row.desc,
            geometry: new ol.geom.Point(row.lonlat)
        });
        feature.setStyle(style_parada);
        sourceFeatures.addFeature(feature);
        
        hint_address = document.createElement('div');
        hint_address.className = 'hint-address hint--always hint--left';
        hint_address.setAttribute('data-hint', row.endereco);
        
        hint_address_overlay = new ol.Overlay({
            element: hint_address,
            offset: [-5, -5],
            position: row.lonlat,
            positioning: 'bottom-left'
        });
        map.addOverlay(hint_address_overlay);
        
    });
}
var coords, intervalId, interval = 100, i = 0;
function loadAnimation(){
    reqwest({
        url: 'route.json',
        type: 'json'
    }).then(function (resp) {
        //console.info(resp[1].rota);
        
        coords = resp[1].rota;
        coords.map(function(row){
            lineString.appendCoordinate(row[0]);
        });
        
        createFeature(resp);
        intervalId = setInterval(animate, interval);
        
    
    }).fail(function (err, msg) {
        console.info(err, msg);
    });
    
}

function animate(){
    marker.setPosition(coords[i][0]);
    markerEl.setAttribute('data-hint', coords[i][1] + ' Km');
    i++;
    
    if(i == coords.length){
        i = 0;
    }
}


