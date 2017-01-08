<html> 
<body> 
    <div>
    <table border="1">
    <#list tasks as task>
    <tr>     
    <td>${task.id}</td>
   <td>${task.name}</td>
   <td>${task.assignee}</td>
   <td>${task.createTime?date}</td>
     <td><button onclick="deal(${task.id})" >办理</button></td>
    </tr>
    
    </#list>
    </table>
    <script>
     function deal(taskId)
     {
           document.location.href="/act/taskinfo/"+taskId

     }
    
    </script>
    </div>
</body> 
</html>