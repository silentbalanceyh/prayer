package com.prayer.facade.schema.rule;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractSchemaException;

/**
 * 规则接口，基本Array规则
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface ArrayRuler extends RuleConstants{
    /**
     * 
     * @param habitus
     * @throws AbstractSchemaException
     */
    @VertexApi(Api.TOOL)
    void apply(ArrayHabitus habitus) throws AbstractSchemaException;
}
