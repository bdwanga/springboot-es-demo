package com.wbd.service;

import com.wbd.Bean.Application;
import com.wbd.utils.BeanUtil;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class EsService
{

    private static final Logger logger = LoggerFactory.getLogger(EsService.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public void queryEsData(String queryItemName, String queryItemValue, int pageIndex, int pageSize)
    {
        SearchResponse esSearchRes;
        SearchHits searchHits;
        try
        {
            SearchRequest searchRequest = getEsSearchRequest(queryItemName, queryItemValue, pageIndex, pageSize);

            esSearchRes = restHighLevelClient.search(searchRequest);
            searchHits = esSearchRes.getHits();
            logger.info("totalHits:{}", searchHits.totalHits);
            searchHits.forEach((item)->{
                Map<String, Object> mapItem = item.getSourceAsMap();
                Application application = BeanUtil.mapToObject(mapItem, Application.class);
                logger.info("搜索数据:{}", application);
            });
        } catch (Exception e){
           logger.info("异常信息", e);
        }

        //GetRequest
        GetResponse esGetRes;
        GetRequest getRequest = new GetRequest("application","type","1");
        try
        {
            esGetRes = restHighLevelClient.get(getRequest);
            Map<String, Object> fields = esGetRes.getSource();
            for (Map.Entry<String, Object> entry : fields.entrySet())
            {
                logger.info(entry.getKey() + ":" + entry.getValue());
            }
        }
        catch (IOException e)
        {
            logger.info("异常信息", e);
        }
    }

    private SearchRequest getEsSearchRequest(String queryItemName, String queryItemValue, int pageIndex, int pageSize)
    {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from((pageIndex - 1) * pageSize);
        searchSourceBuilder.size(pageSize);

        BoolQueryBuilder booleanQueryBuilder = QueryBuilders.boolQuery();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(queryItemName, queryItemValue);
        //matchQueryBuilder.fuzziness(Fuzziness.AUTO).prefixLength(3).maxExpansions(200);
        booleanQueryBuilder.should(matchQueryBuilder); //模糊匹配

        MatchQueryBuilder isAddress = QueryBuilders.matchQuery("is_address", 1);
        //matchQueryBuilder.fuzziness(Fuzziness.AUTO).prefixLength(3).maxExpansions(200);
        isAddress.operator(Operator.AND);
        booleanQueryBuilder.must(isAddress);  //精确匹配

        searchSourceBuilder.query(booleanQueryBuilder);


        SearchRequest searchRequest = new SearchRequest("application");

        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }


}
