package com.ycs.gulimall.service;

import com.ycs.gulimall.vo.SearchParam;
import com.ycs.gulimall.vo.SearchResult;

public interface MallSearchService {
    SearchResult search(SearchParam param);
}
