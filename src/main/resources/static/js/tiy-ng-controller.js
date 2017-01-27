angular.module('TIYAngularApp', ['ngDragDrop', 'ui.bootstrap'])
   .controller('GameController', function($scope, $http, $q) {

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
                            $scope.dynamicBlanks[i] = i;
                        }
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data =(");
                    });
        }

        $scope.submitDynamicDragDrop = function () {
            var colorsInSpots = [];
            for (i = 0; i < $scope.settings.boardWidth; i++) {
                colorsInSpots[i] = document.getElementById("blank" + i).className.substr(4);
                console.log("colorsInSpots[" + i + "] = " + colorsInSpots[i]);
                $scope.newMove[i] = colorsInSpots[i];
            }

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
                            $scope.blackPinsArray[resultsIndex] = [];
                            $scope.grayPinsArray[resultsIndex] = [];

                            for (i = 0; i < response.data.spotsRight; i++) {
                                $scope.blackPinsArray[resultsIndex][i] = i;
                            }
                            for (i = 0; i < response.data.colorsRight; i++) {
                                $scope.grayPinsArray[resultsIndex][i] = i;
                            }

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

        }

        $scope.testDrop = function(event) {
            var divHtmlString = event.target.outerHTML;
            var idSubstring = divHtmlString.substr(divHtmlString.indexOf("id") + 4, divHtmlString.indexOf("class") - 11);

            var classDivHtmlString = event.toElement.outerHTML;
            var classSubstring = classDivHtmlString.substr(classDivHtmlString.indexOf("peg"), classDivHtmlString.indexOf("ui") - 13);
            console.log(classSubstring)
            console.log(idSubstring);
            document.getElementById(idSubstring).className = classSubstring;
        }

        var colorValues =  [];
        var randomNumber;

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
        $scope.blackPinsArray = [];
        $scope.grayPinsArray = [];

        $scope.dynamicBlanks = [];

        getSettings();
    })
    .controller('ConfigController', function($scope, $http) {
        $scope.saveSettings = function() {
            console.log("Saving settings");

            // check settings here and display an alert box if incorrect

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

