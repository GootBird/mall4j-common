package com.xixi.mall.common.core.handle;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.xixi.mall.common.core.utils.PrincipalUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RefreshScope
public class ImgJsonSerializerHandle extends JsonSerializer<String> {

    @Value("${biz.oss.resources-url}")
    private String imgDomain;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (StrUtil.isBlank(value)) {
            gen.writeString(StrUtil.EMPTY);
            return;
        } else if (StrUtil.isBlank(imgDomain)) {
            gen.writeString(value);
            return;
        }
        String[] imgs = value.split(StrUtil.COMMA);
        StringBuilder sb = new StringBuilder();

        for (String img : imgs) {
            // 图片为http协议开头，直接返回
            if (PrincipalUtil.isHttpProtocol(img)) {
                sb.append(img).append(StrUtil.COMMA);
            } else {
                sb.append(imgDomain).append(img).append(StrUtil.COMMA);
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        gen.writeString(sb.toString());
    }
}
