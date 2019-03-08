package edu.jmu.demo.controller;

import edu.jmu.util.BasicResponse;
import edu.jmu.util.BusinessWrapper;
import edu.jmu.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表格测试控制器
 *
 * @author guantianmin
 * @date 2019/3/7 15:57
 */
@RestController
public class TableController {
    private static final Logger logger = LoggerFactory.getLogger(TableController.class);

    @GetMapping("/tables")
    public BasicResponse<List<Map>> getTable() {
        return BusinessWrapper.wrap(response -> {
            List<Map> result = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                HashMap map = new HashMap();
                map.put("id", i);
                map.put("title", "test" + i);
                map.put("status", "delete");
                map.put("author", "name" + i);
                map.put("display_time", "1989-03-05 06:11:58");
                map.put("pageviews", 3551);
                result.add(map);
            }
            ResponseUtil.set(response, 0, "查询表格数据成功",result);
        }, logger);
    }
}
