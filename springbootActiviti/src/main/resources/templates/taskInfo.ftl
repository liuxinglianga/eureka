<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<script src="http://cdn.static.runoob.com/libs/angular.js/1.4.6/angular.min.js"></script>
</head>
<body>
<form id="applyForm" action="http://localhost:9999/act/taskDeal/${taskId}" method="post">

    ${html} 
    
  </form>  
   <div ng-app="myApp" ng-controller="startCtrl">
    <button ng-click="aplly()"> 提交</button>
     </div>
     
</body> 
<script>
  var app = angular.module('myApp',[]);
  app.controller('startCtrl',function($scope)
  {
  $scope.aplly=function()
  {
  document.getElementById("applyForm").submit();
  }
  })
 
</script>
</html>