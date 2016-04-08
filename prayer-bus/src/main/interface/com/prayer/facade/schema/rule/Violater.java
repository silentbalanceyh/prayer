package com.prayer.facade.schema.rule;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractSchemaException;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Violater extends RuleConstants {
    /**
     * 返回一个Schema异常表示验证不成功
     * 
     * @param habitus
     * @param rule
     * @return
     */
    @VertexApi(Api.TOOL)
    AbstractSchemaException violate(ObjectHabitus habitus);
}
