package com.example.demo.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.word.WordExportUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.ESMapper.TestMapper;
import com.example.demo.mapper.chartstitleMapper;
import com.example.demo.mapper.chartsvalueMapper;
import com.example.demo.mapper.primaryDirectoryMapper;
import com.example.demo.mapper.userMapper;
import com.example.demo.pojo.*;
import org.apache.http.HttpHost;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.transport.Transport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.Exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Controller
@RequestMapping("/Test")
public class test {

    @Autowired
    private userMapper userMapper;

    @Autowired
    private primaryDirectoryMapper primaryDirectoryMapper;

    @Autowired
    private chartstitleMapper chartstitleMapper;

    @Autowired
    private chartsvalueMapper chartsvalueMapper;

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @RequestMapping("/ASD")
    public void Test(HttpServletResponse response) throws IOException {
        List<User> A = userMapper.queryAll();

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("目录信息","目录"),
                User.class, A);

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=Test.xls");

        OutputStream ouputStream = response.getOutputStream();

        workbook.write(ouputStream);
        workbook.close();
    }

    @RequestMapping("/ASB")
    public void World(HttpServletResponse response) throws Exception {

        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-disposition", "attachment;filename=Test.docx");
        OutputStream ouputStream = response.getOutputStream();

        TemplateExportParams params = new TemplateExportParams(
                "src/main/java/com/example/demo/Temple/MyTest.xls");

        Map<String, Object> map = new HashMap<String, Object>();

        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 4; i++) {
            Map<String, String> lm = new HashMap<String, String>();
            lm.put("id", i + 1 + "");
            lm.put("name", "Name" + i);
            lm.put("age", "20" + i);
            lm.put("mess", "Name" + i);

            listMap.add(lm);
        }

        map.put("wuxin", listMap);
        map.put("Sum", 1);


        Workbook workbook = ExcelExportUtil.exportExcel(params, map);

        XWPFDocument doc = WordExportUtil.exportWord07(
                "src/main/java/com/example/demo/Temple/Test.docx", map);


        doc.write(ouputStream);
        doc.close();
    }

    @RequestMapping("/GoChartsPage")
    public String Gopage(){
        return "main/Charts";
    }

    @RequestMapping("/AddChartsInfo")
    @ResponseBody
    public String AddChartsInfo(ChartsValue chartsValueList, HttpServletRequest request){
        JSONObject B = new JSONObject();

        long titleID = Long.parseLong((String) request.getParameter("titleId"));

        ChartsTitle title = chartstitleMapper.queryByChartsTitleId(titleID);
        chartsValueList.setChartsTitle(title);

        chartsvalueMapper.save(chartsValueList);

        B.put("A", "D");
        return B.toString();
    }

    @RequestMapping("/ADDES")
    @ResponseBody
    public String TestRedis() throws IOException {
        RestClientBuilder builder = RestClient.builder(new HttpHost("127.0.0.1", 9200));
        RestHighLevelClient client = new RestHighLevelClient(builder);

        SearchRequest searchRequest = new SearchRequest("test");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder("我的未来");
        queryBuilder.field("messInfo").field("titleInfo");

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<h5>");
        highlightBuilder.postTags("</h5>");

        highlightBuilder.field("messInfo").field("titleInfo");
        searchSourceBuilder.highlighter(highlightBuilder);
        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response;
        response = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hit = response.getHits();

        client.close();

        SearchHit[] F = response.getHits().getHits();

        System.out.println(F[0].getSourceAsString());

        ESTest Y = JSON.parseObject(F[0].getSourceAsString(), ESTest.class);

        System.out.println(Y.getMessInfo());

        Map<String, HighlightField> B = F[0].getHighlightFields();

        HighlightField O = B.get("messInfo");

        return O.fragments()[0].toString();
    }

}
