package project.freehelp.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import project.freehelp.common.entity.Dictionary;
import project.freehelp.common.service.DictionaryService;
import project.master.fw.sh.common.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping("/system/dictionary/")
public class DictionaryController extends AbstractController {

	@Autowired
	private DictionaryService dictionaryService;

	@RequestMapping(value = "dictionary_{number}", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getPage(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView(request.getPathInfo()).addAllObjects(getParams(request));
	}

	@RequestMapping(value = "post/{next}", method = RequestMethod.POST)
	public ModelAndView post(HttpServletRequest request, HttpServletResponse response, @PathVariable("next") String next) {
		try {
			Dictionary dictionary = fillObject(new Dictionary(), getParams(request));
			dictionary.setId(dictionary.generatedValue());
			dictionaryService.save(dictionary);
			return new ModelAndView("dictionary_" + next).addObject("dictionary", dictionary);
		} catch (Throwable e) {
			return exceptionPage(e);
		}
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Object postJ(HttpServletRequest request, HttpServletResponse response) {
		try {
			Dictionary dictionary = fillObject(new Dictionary(), getParams(request));
			dictionary.setId(dictionary.generatedValue());
			dictionaryService.save(dictionary);
			return success(dictionary);
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@RequestMapping(value = "put/{next}", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, @PathVariable("next") String next) {
		try {
			Dictionary dictionary = fillObject(new Dictionary(), getParams(request));
			dictionaryService.update(dictionary);
			return new ModelAndView("dictionary_" + next).addObject("dictionary", dictionary);
		} catch (Throwable e) {
			return exceptionPage(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Object put(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id) {
		try {
			Dictionary dictionary = fillObject(new Dictionary(), getParams(request));
			dictionaryService.update(dictionary);
			return success(dictionary);
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Object get(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Integer id) {
		try {
			Dictionary dictionary = dictionaryService.getByPk(id);
			return success(dictionary);
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@RequestMapping(value = "get/{id}/{next}", method = RequestMethod.GET)
	public ModelAndView getj(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Integer id, @PathVariable("next") String next) {
		try {
			Dictionary dictionary = dictionaryService.getByPk(id);
			return new ModelAndView("dictionary_" + next).addObject("dictionary", dictionary);
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
			List<Dictionary> list = dictionaryService.getList(map,true, page, size);
			return new ModelAndView("dictionary_" + next).addObject("dictionaryList", list);
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
			List<Dictionary> list = dictionaryService.getList(map,true, page, size);
			return success(list);
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public Object delete(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Integer id) {
		try {
			dictionaryService.delete(new Dictionary(id));
			return success();
		} catch (Throwable e) {
			return fail(e);
		}
	}
}
