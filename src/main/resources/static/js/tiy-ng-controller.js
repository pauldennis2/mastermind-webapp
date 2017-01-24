angular.module('TIYAngularApp', [])
   .controller('SampleController', function($scope, $http) {
        var numColors = 6;

        $scope.numGuesses = 10;

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
        $scope.settings = {};

        $scope.colorArray = [];
        var colorArrayIndex = 0;
        $scope.results = [];
        var resultsIndex = 0;
        var duplesAllowed = false;

        $scope.saveSettings = function() {

        };


        $scope.simpleSubmit = function() {
            if (resultsIndex < $scope.numGuesses) {
                console.log("About to submit the following move (simple): " + JSON.stringify($scope.newMove));
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
                            if (response.data.spotsRight == 4) {
                                alert("You correctly guessed the pattern. You win!");
                            }
                            resultsIndex++;
                            $scope.getBoard();
                        },
                        function errorCallback(response) {
                            console.log("Unable to get data");
                        });

            }

             else {
                alert("You have exceeded your limit of " + $scope.numGuesses + " guesses. (You lost)");
             }
        };
    });
