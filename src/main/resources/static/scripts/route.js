function initMap() {
    let locations = ${stopLocations};
    let center = {lat: locations[0].latitude, lng: location[0].longitude};
    let map = new google.maps.Map(document.getElementById('routeMap'), { zoom: 10, center: center});

    for (i = 0; i < locations.length; i++){
        let marker = new google.maps.Marker({
            position: new google.maps.LatLng(locations[i].latitude, locations[i].longitude), 
            map: map
        }); 
    }
}