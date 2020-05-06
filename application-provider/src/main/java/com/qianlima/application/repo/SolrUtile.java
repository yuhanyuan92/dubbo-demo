package com.qianlima.application.repo;

import com.qianlima.sj.bean.QueryCondition;
import com.qianlima.sj.bean.QueryResult;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import java.util.Map;


@Slf4j
public class SolrUtile {
    private String host1;
    private String host2;
    private String host3;

    public QueryResult getSjSolr(Map<String,Object> param){
        try{
            QueryCondition queryCondition = buildSjSolrParm(param);
            com.qianlima.sj.query.SolrQuery solrQuery = new com.qianlima.sj.query.SolrQuery();
            JSONObject obj = JSONObject.fromObject(queryCondition);
            QueryResult result =null;
            for (int i = 1; i < 3; i++) {
                result =solrQuery.getSolrQuery(obj.toString(), this.host1);
                // 表示solr搜索接口(tomcat部署的search)发生异常，不能搜索
                if(result.isError()||result.getResults()==null) {
                    if(i==1) {
                        this.host1 = this.host2;
                    }
                }else{
                    break;
                }
            }
            return result;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private QueryCondition buildSjSolrParm(Map<String,Object> param){
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPageNum(Integer.parseInt(param.getOrDefault("numPerPage",30).toString()));
        queryCondition.setStartIndex(Integer.parseInt(param.getOrDefault("currentPage",1).toString()));
        queryCondition.setDwmc(param.getOrDefault("dwmc","")+"");
        queryCondition.setAddress(param.getOrDefault("address","")+"");
        queryCondition.setLxrname(param.getOrDefault("lxrname","")+"");
        queryCondition.setTels(param.getOrDefault("tels","")+"");
        queryCondition.setZhiwu(param.getOrDefault("zhiwu","")+"");
        queryCondition.setFax(param.getOrDefault("fax","")+"");
        queryCondition.setAreaId(param.getOrDefault("areaid","")+"");
        return queryCondition;
    }

    public com.qianlima.qy.bean.QueryResult getQySolr(Map<String,Object> param){
        try{
            com.qianlima.qy.bean.QueryCondition queryCondition = buildQySolrParm(param);
            com.qianlima.qy.query.SolrQuery solrQuery = new com.qianlima.qy.query.SolrQuery();
            JSONObject obj = JSONObject.fromObject(queryCondition);
            com.qianlima.qy.bean.QueryResult result =null;
            for(int a=1; a<3; a++){
                result =solrQuery.getSolrQuery(obj.toString(), this.host1);
                if(result.isError()||result.getResults()==null) {
                    if(a==1) {
                        this.host1 = this.host2;
                    }
                }else{
                    break;
                }
            }
            return result;
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private com.qianlima.qy.bean.QueryCondition buildQySolrParm(Map<String,Object> param){
        com.qianlima.qy.bean.QueryCondition queryCondition = new com.qianlima.qy.bean.QueryCondition();
        queryCondition.setPageNum(Integer.parseInt(param.getOrDefault("numPerPage",30).toString()));
        queryCondition.setStartIndex(Integer.parseInt(param.getOrDefault("currentPage",1).toString()));
        queryCondition.setCompany(param.getOrDefault("gsmc","")+"");
        queryCondition.setLxr(param.getOrDefault("lxr","")+"");
        queryCondition.setAreaId(param.getOrDefault("areaid","")+"");
        return queryCondition;
    }

    public String getHost1() {
        return host1;
    }

    public void setHost1(String host1) {
        this.host1 = host1;
    }

    public String getHost2() {
        return host2;
    }

    public void setHost2(String host2) {
        this.host2 = host2;
    }

    public String getHost3() {
        return host3;
    }

    public void setHost3(String host3) {
        this.host3 = host3;
    }
}
