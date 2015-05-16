var MT = MT || {};
// arrow module -> requires Leaflet!
// https://github.com/meteotest/leaflet-arrows

MT.arrows = (function() {
  /**
   * private functions and attributes to make code more save
   */
  var config = {
    stretchFactor: 1, // should the distance be stretched?
    arrowheadLength: 4, // in km
    arrowheadClosingLine: false, // should a closing third line be drawn?
    arrowheadDegree: 140, // degree of arrowhead
    clickableWidth: 10, // defines the width in pixels of the "phantom" path to capture click events on a line
    validator: function(pointData) {
      if (typeof pointData !== 'undefined') {
        return typeof pointData[config.nameOfDegreeProperty] !== "undefined" &&
                typeof pointData[config.nameOfDistanceProperty] !== "undefined" &&
                !isNaN(pointData[config.nameOfDistanceProperty]) &&
                !isNaN(pointData[config.nameOfDistanceProperty]);
      }
      return false;
    }, // validator is a callback function that takes the data object of the current point and returns whether it is 'valid'. Invalid arrows will be drawn gray
    nameOfDegreeProperty: 'dd',
    nameOfDistanceProperty: 'ff',
    colorInvalidPoint: '#777',
    circleRadiusInvalidPoint: 1000, // Radius of the circle to display missing or '0'-value
    pathOptions: {
      color : '#333',
      opacity : 0.9,
      fillOpacity : 0.9,
      weight : 2,
      smoothFactor : 0
    }
  };

  function calculateEndPoint(latlng, dist, degree) {
    /*
     * http://www.codeguru.com/cpp/cpp/algorithms/article.php/c5115/Geographic-Distance-and-Azimuth-Calculations.htm
     */
    var distance = dist * config.stretchFactor;
    var R = 6378.137, // earth radius in kmmeters
      d2r = L.LatLng.DEG_TO_RAD, // degree 2 radius
      r2d = L.LatLng.RAD_TO_DEG, //

      bearing = degree * d2r;
    distance = distance / R;
    var a = Math.acos(Math.cos(distance) *
      Math.cos((90 - latlng.lat) * d2r) +
      Math.sin((90 - latlng.lat) * d2r) *
      Math.sin(distance) *
      Math.cos(bearing));
    var B = Math.asin(Math.sin(distance) * Math.sin(bearing) / Math.sin(a));
    return new L.LatLng(90 - a * r2d, B * r2d + latlng.lng);
  }

  function calculateArrowArray(latlng, degree) {
    // calculates the Array for the arrow

    // latlng is the position, where the arrow is added
    // degree is the degree of the line

    if (latlng.length !== undefined) {
      latlng = new L.LatLng(latlng);
    }
    var firstEdge = calculateEndPoint(latlng, config.arrowheadLength, degree -
      config.arrowheadDegree);
    var arr = [firstEdge, latlng,
      calculateEndPoint(latlng, config.arrowheadLength, degree + config.arrowheadDegree)
    ];

    if (config.arrowheadClosingLine) {
      arr.push(firstEdge);
    }
    return arr;
  }

  /**
   * public properties
   */
  return {
    /**
     * Takes an object as parameter and sets the configuration accordingly.
     * Preserves keys in the base configruation, that aren't assigned
     */
    setConfiguration: function(customConfig) {
      if (customConfig !== undefined) {
        for (var id in customConfig) {
          if (customConfig.hasOwnProperty(id)) {
            config[id] = customConfig[id];
          }
        }
      }
    },

    getStretchFactor: function() {
      return config.stretchFactor;
    },

    /**
     * data: object containing information for arrows
     * {
     *    key : {
     *    nameOfDegreeProperty* : Number (degree)
     *    nameOfDistanceProperty* :Number (km)
     *    [[nameOfColorProperty* : Any -> will be applied to the 'colorScheme' function]] opt
     *    }
     *  }
     *
     * options : object for customizing path and data handling
     *   **important**  define the names of the attributes in data map,
     *    which hold the information on length, degree and the parameter
     *    for to supply the colorScheme
     * {
     *    nameOfDegreeProperty :
     *      String eg. 'deg' -> name of Property in data objects containing
     *      the degree value
     *    nameOfDistanceProperty :
     *      String 'length'-> name of Property in data objects containing
     *      the distance value
     *    nameOfColorProperty : String 'value' -> name of Property in data
     *    objects containing the value, which should be supplied to the
     *    colorScheme function
     *    isWindDegree : boolean (is Degree value direction of wind? -> degree - 180
     *  }
     */
    makeArrowLayer: function(data, options, colorScheme) {
      // options: nameOfLayer, isWindDegree, nameOfDegreeProperty,
      // nameOfDistanceProperty, pathOptions, popupContent

      this.setConfiguration(options);

      var pointPathOption = {
        stroke: false,
        fillOpacity: 0.8,
      };

      var allArrows = [];
      for (var dataId in data) {
        var entity = data[dataId];
        if (typeof entity.lat !== "undefined" &&
            typeof entity.lon !== "undefined") {
          var startPoint = new L.LatLng(entity.lat, entity.lon);

          var distance, degree;
          var pathOption = config.pathOptions;

          // is current arrow valid according to the validator callback?
          // change color if not
          if (typeof config.validator !== 'function' || config.validator(entity)) {
            pathOption.color = typeof colorScheme === "function" ? colorScheme(entity) : options.color;
            degree = options.isWindDegree ? entity[options.nameOfDegreeProperty] - 180 : entity[options.nameOfDegreeProperty];
            distance = parseFloat(entity[options.nameOfDistanceProperty]);
          } else {
            pathOption.color = config.colorInvalidPoint;
            distance = 0;
            degree = 0;
          }

          // if distance or degree is 0  then draw a point instead of an arrow
          if (distance === 0 || degree === 0) {
            pointPathOption.color = pathOption.color;
            var circle = L.circle(startPoint, options.circleRadiusInvalidPoint, pointPathOption);

            if (typeof options.popupContent === 'function') {
              circle.bindPopup(options.popupContent(entity, dataId));
            }
            allArrows.push(circle);

          } else {
            var theLine = [startPoint, calculateEndPoint(startPoint, distance, degree)];
            var theArrow = calculateArrowArray(theLine[1], degree);

            var backgroundPathOption = L.MultiPolyline.extend(true, {}, pathOption);
            backgroundPathOption.opacity = 0;
            backgroundPathOption.weight = config.clickableWidth;

            var backgroundMulitpolyline = new L.MultiPolyline([theLine, theArrow],
              backgroundPathOption);
            var multipolyline = new L.MultiPolyline([theLine, theArrow], pathOption);

            if (typeof options.popupContent === 'function') {
              backgroundMulitpolyline.bindPopup(options.popupContent(entity, dataId));
            }

            allArrows.push(multipolyline);
            allArrows.push(backgroundMulitpolyline);
          }
        }
      }

      var lg = L.featureGroup(allArrows);

      return {
        arrowLayer: lg,
        layerName: options.nameOfLayer
      };
    }
  };
})();
