package com.mxs.sampleservice.service.impl;

import com.mxs.sampleservice.mapper.CommSequenceMapper;
import com.mxs.sampleservice.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 获取sequence 序列 公共service
 * 
 *  Created by j.yang on 2019-08-29
 * 
 */

@Service
public class SequenceServiceImpl implements SequenceService {

    @Autowired
    private CommSequenceMapper commSequenceMapper;

    public Long getCurrentLongValue() {
        return this.getCurrentLongValue(SequenceService.SEQ_COMMON);
    }

    public Long getCurrentLongValue(String sequenceName) {
        return new Long(this.getCurrentStringValue(sequenceName));
    }

    public String getCurrentStringValue() {
        return this.getCurrentStringValue(SequenceService.SEQ_COMMON);
    }

    public String getCurrentStringValue(String sequenceName) {
        return this.getCurrentStringValue(sequenceName, SequenceService.DEFAULT_ADDED_TO_LENGTH);
    }

    public String getCurrentStringValue(String sequenceName, int addedToLength) {
        return this.getCurrentStringValue(sequenceName, addedToLength, SequenceService.DEFAULT_ADDED_CHARACTER);
    }

    public String getCurrentStringValue(String sequenceName, int addedToLength, char addedCharacter) {
        Long currentValue = commSequenceMapper.getCurrentValue(sequenceName);
        if (currentValue == null) {
            throw new RuntimeException("获取当前sequence[" + sequenceName + "]不存在.");
        }
        return addedCharAndLength(currentValue, addedToLength, addedCharacter);
    }

    public String getBusinessCurrentStringValue(String businessType, String sequenceName, int allLength) {
        int addedToLength = allLength - businessType.length();
        return this.getCurrentStringValue(sequenceName, addedToLength);
    }

    public Long getNextLongValue() {
        return this.getNextLongValue(SequenceService.SEQ_COMMON);
    }

    public Long getNextLongValue(String sequenceName) {
        return new Long(this.getNextStringValue(sequenceName));
    }

    public String getNextStringValue() {
        return this.getNextStringValue(SequenceService.SEQ_COMMON);
    }

    public String getNextStringValue(String sequenceName) {
        return this.getNextStringValue(sequenceName, SequenceService.DEFAULT_ADDED_TO_LENGTH);
    }

    public String getNextStringValue(String sequenceName, int addedToLength) {
        return this.getNextStringValue(sequenceName, addedToLength, SequenceService.DEFAULT_ADDED_CHARACTER);
    }

    public String getNextStringValue(String sequenceName, int addedToLength, char addedCharacter) {
        Long nextValue = commSequenceMapper.getNextValue(sequenceName);
        if (nextValue == null) {
            throw new RuntimeException("获取下一个sequence[" + sequenceName + "]不存在.");
        }
        return addedCharAndLength(nextValue, addedToLength, addedCharacter);
    }

    @Override
    public String getNextStringValue(String sequenceName, int addedToLength, int randomNumLength) {
        if (randomNumLength > 0) {
            return this.getNextStringValue(sequenceName, addedToLength) + getRandomString(randomNumLength);
        }
        return this.getNextStringValue(sequenceName, addedToLength);
    }

    private String getRandomString(int length) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(rnd.nextInt(10));
        }
        return sb.toString();
    }

    // 补齐方法
    private String addedCharAndLength(Long value, int addedToLength, char addedCharacter) {
        StringBuffer sb = new StringBuffer();

        // 大于数字自身长度
        if (addedToLength > 0 && addedToLength > Long.toString(value).length()) {
            int addLengthCount = addedToLength - Long.toString(value).length();
            for (int i = 0; i < addLengthCount; i++) {
                sb.append(addedCharacter);
            }
        }
        return sb.append(value).toString();
    }

}
