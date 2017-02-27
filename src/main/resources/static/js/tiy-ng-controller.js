angular.module('TIYAngularApp', ['ngDragDrop', 'ui.bootstrap'])
   .controller('GameController', function($scope, $http, $q) {
    //recompiled
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
                        /*if ($scope.settings.numColors == 5) {

                            document.getElementById("purple_menu").outerHTML = '<div id="purple_menu" class="peg PurpleDisable"></div>';
                        }*/
                        for (i = 0; i < 6; i++) {
                            if (i >= $scope.settings.numColors) {
                                document.getElementById(menuNames[i]).outerHTML = '<div id="' + menuNames[i] + '" class ="peg ' + disableNames[i] + '"></div>';

                                //<div id="purple_menu" class="peg " purpledisable"=""></div>
                            }
                        }

                        for (i = 0; i < $scope.settings.boardWidth; i++) {
                            $scope.testArray[i] = i;
                            $scope.dynamicBlanks[i] = i;
                        }
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data =(");
                    });
        }

        var menuNames = [];
        menuNames[0] = "red_menu";
        menuNames[1] = "orange_menu";
        menuNames[2] = "yellow_menu";
        menuNames[3] = "green_menu";
        menuNames[4] = "blue_menu";
        menuNames[5] = "purple_menu";

        var disableNames = [];
        disableNames[2] = "YellowDisable";
        disableNames[3] = "GreenDisable";
        disableNames[4] = "BlueDisable";
        disableNames[5] = "PurpleDisable";

        $scope.submitDynamicDragDrop = function () {
            var colorsInSpots = [];
            var emptyFlag = false;

            for (i = 0; i < $scope.settings.boardWidth; i++) {
                if (document.getElementById("blank" + i).className.substr(4) == "blank ui-droppable") {
                    emptyFlag = true;
                }
            }
            if (!emptyFlag) {
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
                 } //end if (resultsIndex < $scope.settings.numGuesses)

                 else {
                    alert("You have exceeded your limit of " + $scope.settings.numGuesses + " guesses. (You lost)");
                 }
             } //end if (!empty flag)
            else {
                alert("No moves can be blank. Please fill in all moves and re-submit.");
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
            if (($scope.settings.numColors < $scope.settings.boardWidth) && !$scope.settings.duplesAllowed) {
                alert("Warning: setting duplicate colors to be allowed. (Cannot create a board of size *" +
                $scope.settings.boardWidth + "* with only *" + $scope.settings.numColors + "* unique colors.)");
                $scope.settings.duplesAllowed = true;
            }
            $http.post("save-settings.json", $scope.settings)
                .then(
                    function successCallback(response) {
                        console.log("Successfully saved settings");
                        if ($scope.settings.numColors < $scope.settings.boardWidth) {
                            console.log($scope.settings.duplesAllowed);

                        }
                    },
                    function errorCallback(response) {
                        console.log("Some kind of error. Sadface");
                    });
        };

        $scope.settings = {};
        $scope.settings.numColors = 6;
        $scope.settings.numGuesses = 10;
        $scope.settings.boardWidth = 4;
        $scope.settings.duplesAllowed = false;
        console.log("ConfigController up and running.")
    });

