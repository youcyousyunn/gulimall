package com.ycs.gulimall.controller;

import com.ycs.gulimall.service.MallSearchService;
import com.ycs.gulimall.vo.SearchParam;
import com.ycs.gulimall.vo.SearchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SearchController {
    @Resource
    private MallSearchService mallSearchService;


    /**
     * 根据请求参数搜索
     * @param param
     * @return
     */
    @GetMapping(value = "/list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request) {
        param.set_queryString(request.getQueryString());
        //1、根据传递来的页面的查询参数，去es中检索商品
//        SearchResult result = mallSearchService.search(param);
        SearchResult result = new SearchResult();
        model.addAttribute("result",result);
        return "list";
    }
}
