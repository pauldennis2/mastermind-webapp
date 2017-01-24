angular.module('TIYAngularApp', [])
   .controller('GameController', function($scope, $http) {

        getBoard = function() {
            console.log("Getting data");

            $http.get("/board.json")
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");
                        $scope.guesses = response.data;
                        for (i = 0; i < resultsIndex; i++) {
                            $scope.guessIndexArray[i] = i;
                        }
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data");
                    });
        };

        getSettings = function() {
            console.log("Getting settings");

            $http.get("/get-settings.json")
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");
                        $scope.settings = response.data;
                        for (i = 0; i < $scope.settings.boardWidth; i++) {
                            console.log("index = " + i);
                            $scope.testArray[i] = i;
                        }
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data =(");
                    });
        }

        $scope.submitMove = function() {
            if (resultsIndex < $scope.settings.numGuesses) {
                console.log("About to submit the following move: " + JSON.stringify($scope.newMove));
                $scope.colorArray[colorArrayIndex] = [];
                for (i = 0; i < $scope.settings.boardWidth; i++) {
                    $scope.colorArray[colorArrayIndex][i] = "peg " + $scope.newMove[i];
                }
                colorArrayIndex++;
                $http.post("/submit-move.json", $scope.newMove)//Here is our problem (interaction with MRC line 34
                    .then(
                        function successCallback(response) {
                            console.log(response.data);
                            console.log("Adding simple data to scope");
                            //$scope.board = response.data;
                            $scope.results[resultsIndex] = {};
                            $scope.results[resultsIndex] = response.data;
                            if (response.data.spotsRight == $scope.settings.boardWidth) {
                                alert("You correctly guessed the pattern. You win!");
                            }
                            resultsIndex++;
                            getBoard();
                        },
                        function errorCallback(response) {
                            console.log("Unable to get data");
                        });
             }

             else {
                alert("You have exceeded your limit of " + $scope.settings.numGuesses + " guesses. (You lost)");
             }
        };

        var colorArrayIndex = 0;
        var resultsIndex = 0;



        console.log("*************** Initializing AngularJS Controller ***************");
        //$scope.newMove = {};
        $scope.newMove = [];
        $scope.userGuess = [];
        $scope.userRequestContainer = {};
        $scope.userGuessArray = [];
        $scope.settings = {};
        $scope.colorArray = [];
        $scope.results = [];
        $scope.testArray = [];
        $scope.guessIndexArray = [];

        getSettings();
    })
    .controller('ConfigController', function($scope, $http) {
        $scope.saveSettings = function() {
            console.log("Saving settings");
            $http.post("save-settings.json", $scope.settings)
                .then(
                    function successCallback(response) {
                        console.log("Successfully saved settings");
                    },
                    function errorCallback(response) {
                        console.log("Some kind of error. Sadface");
                    });
        };
        $scope.settings = {};
    });
