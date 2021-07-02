let currLoc = {};
function success(pos) {
    let crd = pos.coords;
    currLoc = {
        x: crd.longitude,
        y: crd.latitude,
        _lat: crd.latitude,
        _lng: crd.longitude
    };
    //naver map api [s]
    let opts = {
        center: new naver.maps.LatLng(crd.latitude, crd.longitude),
        zoom: 15,
        mapTypeControl: true
    };

    let map = new naver.maps.Map("map", opts);

    let infoWindow = new naver.maps.InfoWindow({
        anchorSkew: true
    });

    map.setCursor('pointer');

    function searchCoordinateToAddress(latlng) {
        infoWindow.close();

        naver.maps.Service.reverseGeocode({
            coords: latlng,
            orders: [
                naver.maps.Service.OrderType.ADDR,
                naver.maps.Service.OrderType.ROAD_ADDR
            ].join(',')
        }, function(status, response) {
            if (status === naver.maps.Service.Status.ERROR) {
                if (!latlng) {
                    return alert('ReverseGeocode Error, Please check latlng');
                }
                if (latlng.toString) {
                    return alert('ReverseGeocode Error, latlng:' + latlng.toString());
                }
                if (latlng.x && latlng.y) {
                    return alert('ReverseGeocode Error, x:' + latlng.x + ', y:' + latlng.y);
                }
                return alert('ReverseGeocode Error, Please check latlng');
            }

            let address = response.v2.address,
                htmlAddresses = [];

            if (address.jibunAddress !== '') {
                htmlAddresses.push('[지번 주소] ' + address.jibunAddress);
            }

            if (address.roadAddress !== '') {
                htmlAddresses.push('[도로명 주소] ' + address.roadAddress);
                document.getElementById('locationAddr').value = address.roadAddress;
            } else {
                document.getElementById('locationAddr').value = address.jibunAddress;
            }

            infoWindow.setContent([
                '<div style="padding:10px !important;min-width:200px !important;line-height:150% !important;">',
                //'<h4 style="margin-top:5px;">검색 좌표</h4><br />',
                htmlAddresses.join('<br />'),
                '</div>'
            ].join('\n'));

            infoWindow.open(map, latlng);
        });
    }

    function searchAddressToCoordinate(address) {
        naver.maps.Service.geocode({
            query: address
        }, function(status, response) {
            if (status === naver.maps.Service.Status.ERROR) {
                if (!address) {
                    return alert('Geocode Error, Please check address');
                }
                return alert('Geocode Error, address:' + address);
            }

            if (response.v2.meta.totalCount === 0) {
                return alert('No result.');
            }

            let htmlAddresses = [],
                item = response.v2.addresses[0],
                point = new naver.maps.Point(item.x, item.y);

            if (item.roadAddress) {
                htmlAddresses.push('[도로명 주소] ' + item.roadAddress);
            }

            if (item.jibunAddress) {
                htmlAddresses.push('[지번 주소] ' + item.jibunAddress);
            }

            //if (item.englishAddress) {
            //  htmlAddresses.push('[영문명 주소] ' + item.englishAddress);
            //}

            infoWindow.setContent([
                '<div style="padding:10px !important;min-width:200px !important;line-height:150% !important;">',
                //  '<h4 style="margin-top:5px;">검색 주소 : '+ address +'</h4><br />',
                htmlAddresses.join('<br />'),
                '</div>'
            ].join('\n'));

            map.setCenter(point);
            infoWindow.open(map, point);
        });
    }

    function initGeocoder() {
        if (!map.isStyleMapReady) {
            return;
        }

        map.addListener('click', function(e) {
            searchCoordinateToAddress(e.coord);
        });

        $('#address').on('keydown', function(e) {
            var keyCode = e.which;

            if (keyCode === 13) { // Enter Key
                searchAddressToCoordinate($('#address').val());
            }
        });

        $('#submit').on('click', function(e) {
            e.preventDefault();

            searchAddressToCoordinate($('#address').val());
        });
        //current location for the first map view page
        searchCoordinateToAddress(currLoc);
        //searchAddressToCoordinate('');
    }

    naver.maps.onJSContentLoaded = initGeocoder;
    naver.maps.Event.once(map, 'init_stylemap', initGeocoder);
    //[e]
}


function error(err) {
    console.warn('ERROR(' + err.code + '): ' + err.message);
};

const options = {
    enableHighAccuracy: true,
    timeout: 5000,
    maximumAge: 0
};

// invoke this api here
function initCurrentLocation() {
    if (!navigator.geolocation) {
        console.log('지도API가 실행불가능한 브라우저입니다.');
    } else {
        navigator.geolocation.getCurrentPosition(success, error, options);
    }
};
