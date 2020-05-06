package com.qianlima.application.repo;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author yyc
 */
@Component
public class SolrRepository {

	@Autowired
	@Qualifier("solr1")
	private SolrUtile sjsolrUtile;
	@Autowired
	@Qualifier("solr2")
	private SolrUtile qysolrUtile;

	/**
	 *   查询solr  qysolr
	 **/
	public List<Integer> getIdsFromQySolr(Map<String,Object> param) {
		com.qianlima.qy.bean.QueryResult result = qysolrUtile.getQySolr(param);
		List<Integer> lst = Lists.newArrayList();
		if(result !=null){
			lst.add((int)result.getRowCount());
			List<com.qianlima.qy.bean.QueryCondition> list = result.getResults();
			if(list!=null&&list.size()>0){
				for(com.qianlima.qy.bean.QueryCondition product:list){
					lst.add(Integer.parseInt(product.getId()));
				}
			}
			return lst;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 *   查询solr  sjsolr
	 **/
	public List<Integer> getIdsFromSjSolr(Map<String,Object> param) {
		com.qianlima.sj.bean.QueryResult result = sjsolrUtile.getSjSolr(param);
		List<Integer> lst = Lists.newArrayList();
		if(result !=null){
			lst.add((int)result.getRowCount());
			List<com.qianlima.sj.bean.QueryCondition> list = result.getResults();
			if(list!=null&&list.size()>0){
				for(com.qianlima.sj.bean.QueryCondition product:list){
					lst.add(Integer.parseInt(product.getId()));
				}
			}
			return lst;
		}
		return Collections.EMPTY_LIST;
	}

}
