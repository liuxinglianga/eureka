package controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@Controller
@RequestMapping("/act")
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

	@RequestMapping("/start")
	public String start1(Map<String, Object> model) {
		Object form = formService.getRenderedStartForm("myProcess:3:13958");
		// System.out.println(form);
		model.put("html", form.toString());
		return "start";
	}

	@RequestMapping("/apply/{name}")
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

	@RequestMapping("/my/{name}")
	public String apply(Map<String, Object> model, @PathVariable String name
			) 
	{
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(name)
				.list();
		
		 model.put("tasks", tasks);
		return "my";
	}
	
	@RequestMapping("/taskinfo/{taskId}")
	public String showTask(Map<String, Object> model, @PathVariable String taskId
			) 
	{
		Object form =formService.getRenderedTaskForm(taskId);
		model.put("html", form.toString());

		return "taskInfo";
	}
	
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
	
	public static void main(String[] args) {
		SpringApplication.run(ActCotroller.class, args);

	}

}
