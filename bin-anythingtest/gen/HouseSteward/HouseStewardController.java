package project.freehelp.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import project.freehelp.common.entity.HouseSteward;
import project.freehelp.common.service.HouseStewardService;
import project.master.fw.sh.common.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping("/system/houseSteward/")
public class HouseStewardController extends AbstractController {

	@Autowired
	private HouseStewardService houseStewardService;

	@RequestMapping(value = "houseSteward_{number}", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getPage(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView(request.getPathInfo()).addAllObjects(getParams(request));
	}

	@RequestMapping(value = "post/{next}", method = RequestMethod.POST)
	public ModelAndView post(HttpServletRequest request, HttpServletResponse response, @PathVariable("next") String next) {
		try {
			HouseSteward houseSteward = fillObject(new HouseSteward(), getParams(request));
			houseSteward.setId(houseSteward.generatedValue());
			houseStewardService.save(houseSteward);
			return new ModelAndView("houseSteward_" + next).addObject("houseSteward", houseSteward);
		} catch (Throwable e) {
			return exceptionPage(e);
		}
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Object postJ(HttpServletRequest request, HttpServletResponse response) {
		try {
			HouseSteward houseSteward = fillObject(new HouseSteward(), getParams(request));
			houseSteward.setId(houseSteward.generatedValue());
			houseStewardService.save(houseSteward);
			return success(houseSteward);
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@RequestMapping(value = "put/{next}", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, @PathVariable("next") String next) {
		try {
			HouseSteward houseSteward = fillObject(new HouseSteward(), getParams(request));
			houseStewardService.update(houseSteward);
			return new ModelAndView("houseSteward_" + next).addObject("houseSteward", houseSteward);
		} catch (Throwable e) {
			return exceptionPage(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Object put(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id) {
		try {
			HouseSteward houseSteward = fillObject(new HouseSteward(), getParams(request));
			houseStewardService.update(houseSteward);
			return success(houseSteward);
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Object get(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id) {
		try {
			HouseSteward houseSteward = houseStewardService.getByPk(id);
			return success(houseSteward);
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@RequestMapping(value = "get/{id}/{next}", method = RequestMethod.GET)
	public ModelAndView getj(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id, @PathVariable("next") String next) {
		try {
			HouseSteward houseSteward = houseStewardService.getByPk(id);
			return new ModelAndView("houseSteward_" + next).addObject("houseSteward", houseSteward);
		} catch (Throwable e) {
			return exceptionPage(e);
		}
	}

	@RequestMapping(value = "list/{next}")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response, @PathVariable("next") String next) {
		try {
			Map<String, Object> map = getParams(request);
			int page = map.containsKey("page") ? Integer.valueOf(map.remove("page").toString()) : -1;
			int size = map.containsKey("size") ? Integer.valueOf(map.remove("size").toString()) : -1;
			List<HouseSteward> list = houseStewardService.getList(map,true, page, size);
			return new ModelAndView("houseSteward_" + next).addObject("houseStewardList", list);
		} catch (Throwable e) {
			return exceptionPage(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "listj")
	public Object listj(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> map = getParams(request);
			int page = map.containsKey("page") ? Integer.valueOf(map.remove("page").toString()) : -1;
			int size = map.containsKey("size") ? Integer.valueOf(map.remove("size").toString()) : -1;
			List<HouseSteward> list = houseStewardService.getList(map,true, page, size);
			return success(list);
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public Object delete(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id) {
		try {
			houseStewardService.delete(new HouseSteward(id));
			return success();
		} catch (Throwable e) {
			return fail(e);
		}
	}
}
