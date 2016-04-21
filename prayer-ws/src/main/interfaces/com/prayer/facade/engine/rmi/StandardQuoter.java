package com.prayer.facade.engine.rmi;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface StandardQuoter extends RemoteQuoter<String> {
}
