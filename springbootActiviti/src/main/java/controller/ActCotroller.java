package controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;
@SpringBootApplication
@RestController
@RequestMapping("/act")
@Api(value = "/helloWorld", description = "Greeting API", position = 1)  

public class ActCotroller {
	// private String fp_leaveType;
	//
	//
	// public String getFp_leaveType() {
	// return fp_leaveType;
	// }
	// public void setFp_leaveType(String fp_leaveType) {
	// this.fp_leaveType = fp_leaveType;
	// }

	@Autowired
	private FormService formService;
	
	@Autowired
	private TaskService taskService;
	@ApiIgnore
	@RequestMapping("/start")
	public String start1(Map<String, Object> model) {
		Object form = formService.getRenderedStartForm("myProcess:3:13958");
		// System.out.println(form);
		model.put("html", form.toString());
		return "start";
	}

	@ApiIgnore
	public String apply(Map<String, Object> model, @PathVariable String name,
			@RequestParam String fp_leaveType,
			@RequestParam String fp_startTime, 
			@RequestParam String fp_endTime,
			@RequestParam String fp_reason) {
		Map<String, String> formProperties = new HashMap<String, String>();
		// System.out.println(form);
		formProperties.put("leaveType", fp_leaveType);
		formProperties.put("startTime", fp_startTime);
		formProperties.put("endTime", fp_endTime);

		formProperties.put("reason", fp_reason);
		formProperties.put("applyUserId", name);
		formService.submitStartFormData(
				"myProcess:3:13958", formProperties);

		//
		// System.out.println(form);
		// model.put("html", form.toString());
		return "success";
	}

	@ApiOperation(value="查询代办", notes="查询当前用户的代办")
	@ApiImplicitParams({@ApiImplicitParam(name = "model", value = "模型", required = true, dataType = "Map<String, Object>"),
    @ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String")})
	
	@RequestMapping(value="/my/{name}" ,method=RequestMethod.GET)
	public String apply(Map<String, Object> model, @PathVariable String name
			) 
	{
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(name)
				.list();
		
		 model.put("tasks", tasks);
		 
		return "my list";
	}
	
	@ApiIgnore
	@RequestMapping("/taskinfo/{taskId}")
	public String showTask(Map<String, Object> model, @PathVariable String taskId
			) 
	{
		Object form =formService.getRenderedTaskForm(taskId);
		model.put("html", form.toString());

		return "taskInfo";
	}
	@ApiIgnore
	@RequestMapping("/taskDeal/{taskId}")
	public String dealTask(Map<String, Object> model, @PathVariable String taskId, 
			HttpServletRequest request
			) 
	{
		Map<String, Object> formProperties = new HashMap<String, Object>();
      Map<String,String[]> params =request.getParameterMap();  
      Iterator it= params.entrySet().iterator();
      while(it.hasNext())
      {
    	Map.Entry<String, String[]> entry= (Map.Entry<String, String[]>) it.next();
    	 
//    	System.out.println(entry.getKey());
    	Object value = entry.getValue();
//    	System.out.println(value);
    	  formProperties.put( entry.getKey(), entry.getValue()[0]);
    	 
      }
      System.out.println(formProperties);
//		formProperties.put("fp_hrPass", fp_hrPass);
//		formProperties.put("fp_hrBackReason", fp_hrBackReason);
		taskService.complete(taskId, formProperties);
		return "success";
	}
	
	

}
