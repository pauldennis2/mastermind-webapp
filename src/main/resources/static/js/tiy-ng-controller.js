angular.module('TIYAngularApp', [])
   .controller('SampleController', function($scope, $http) {
        //alert("waiting for user input you slowpoke");
        console.log("Sample controller running");
        $scope.getBoard = function() {
            console.log("Getting data");

            $http.get("/board.json")
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");
                        $scope.guesses = response.data;
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data");
                    });
        };
        $scope.newMove = {};
        $scope.userGuess = [];
        $scope.userRequestContainer = {};
        $scope.userGuessArray = [];

        $scope.colorArray = [];
        var colorArrayIndex = 0;

        $scope.results = [];
        var resultsIndex = 0;
        $scope.simpleSubmit = function() {
            console.log("About to submit the following move (simple): " + JSON.stringify($scope.newMove));
            console.log("Setting color to " + $scope.newMove.first);
            $scope.colorArray[colorArrayIndex] = {};
            $scope.colorArray[colorArrayIndex].color1 = "peg " + $scope.newMove.first;
            $scope.colorArray[colorArrayIndex].color2 = "peg " + $scope.newMove.second;
            $scope.colorArray[colorArrayIndex].color3 = "peg " + $scope.newMove.third;
            $scope.colorArray[colorArrayIndex].color4 = "peg " + $scope.newMove.fourth;
            colorArrayIndex++;
            $http.post("/simple-submit.json", $scope.newMove)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding simple data to scope");
                        //$scope.board = response.data;
                        $scope.results[resultsIndex] = {};
                        $scope.results[resultsIndex] = response.data;
                        resultsIndex++;
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data");
                    });
        };
    });
