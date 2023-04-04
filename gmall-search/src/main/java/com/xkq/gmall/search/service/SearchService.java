package com.xkq.gmall.search.service;

import com.xkq.gmall.search.vo.SearchParam;
import com.xkq.gmall.search.vo.SearchResult;

public interface SearchService {
    SearchResult getSearchResult(SearchParam searchParam);
}
