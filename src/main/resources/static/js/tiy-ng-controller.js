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
                        }
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data =(");
                    });
        }

        $scope.submitDragDropMove = function () {
            console.log("Submitting move from drag/drop interface...");
            var color1 = document.getElementById("firstBlank").className.substr(4);
            var color2 = document.getElementById("secondBlank").className.substr(4);
            var color3 = document.getElementById("thirdBlank").className.substr(4);
            var color4 = document.getElementById("fourthBlank").className.substr(4);
            $scope.newMove[0] = color1;
            $scope.newMove[1] = color2;
            $scope.newMove[2] = color3;
            $scope.newMove[3] = color4;

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
        };

        $scope.firstSpotOnDrop = function(event) {
            console.log("First spot - Drop received.");
            var divHtmlString = event.toElement.outerHTML;
            //Substring representing the class of the draggable
            var classSubstring = divHtmlString.substr(divHtmlString.indexOf("peg"), divHtmlString.indexOf("ui") - 13);
            document.getElementById("firstBlank").className = classSubstring;
        }
        $scope.secondSpotOnDrop = function(event) {
            console.log("Second spot - Drop received.");
            var divHtmlString = event.toElement.outerHTML;
            //Substring representing the class of the draggable
            var classSubstring = divHtmlString.substr(divHtmlString.indexOf("peg"), divHtmlString.indexOf("ui") - 13);
            document.getElementById("secondBlank").className = classSubstring;
        }
        $scope.thirdSpotOnDrop = function(event) {
            console.log("Third spot - Drop received.");
            var divHtmlString = event.toElement.outerHTML;
            //Substring representing the class of the draggable
            var classSubstring = divHtmlString.substr(divHtmlString.indexOf("peg"), divHtmlString.indexOf("ui") - 13);
            document.getElementById("thirdBlank").className = classSubstring;
        }
        $scope.fourthSpotOnDrop = function(event) {
            console.log("Fourth spot - Drop received.");
            var divHtmlString = event.toElement.outerHTML;
            //Substring representing the class of the draggable
            var classSubstring = divHtmlString.substr(divHtmlString.indexOf("peg"), divHtmlString.indexOf("ui") - 13);
            document.getElementById("fourthBlank").className = classSubstring;
        }
        //var randomNumberBetween0and19 = Math.floor(Math.random() * 20);
        var colorNames = [];
        colorNames[0] = "Red";
        colorNames[1] = "Orange";
        colorNames[2] = "Yellow";
        colorNames[3] = "Green";
        colorNames[4] = "Blue";
        colorNames[5] = "Purple";
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

