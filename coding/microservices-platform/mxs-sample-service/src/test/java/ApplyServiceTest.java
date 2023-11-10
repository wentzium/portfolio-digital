import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxs.common.util.ResponseData;
import com.mxs.sampleservice.SampleServiceApplication;
import com.mxs.sampleservice.entity.Apply;
import com.mxs.sampleservice.service.ApplyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 *  Created by j.yang on 2019-07-20
 * 
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleServiceApplication.class)
public class ApplyServiceTest {

    @Autowired
    private ApplyService applyService;

    @Test
    public void t1() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Apply> applies = applyService.selectList(new EntityWrapper<>());
        String value = objectMapper.writeValueAsString(applies);
        log.info(value);
        List<Apply> list = objectMapper.readValue(value, List.class);
        log.info("", list);
        ResponseData<List<Apply>> responseData = ResponseData.createSuccessResult(applies);
        String s = objectMapper.writeValueAsString(responseData);
        log.info(s);
        ResponseData<List<Apply>> responseData1 = objectMapper.readValue(s, ResponseData.class);
        log.info("", responseData1);
    }
}
