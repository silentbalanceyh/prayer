package com.prayer.bus.impl.deploy;

import static com.prayer.util.debug.Log.peError;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.base.bus.AbstractDPSevImpl;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.dao.impl.schema.RuleDaoImpl;
import com.prayer.facade.bus.deploy.RuleDPService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.vertx.PERule;
import com.prayer.model.vertx.PEUri;
import com.prayer.util.bus.ResultExtractor;
import com.prayer.util.io.JsonKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author Lang
 *
 */
public class RuleDPSevImpl extends AbstractDPSevImpl<PERule, String>implements RuleDPService { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleDPSevImpl.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Class<?> getDaoClass() {
        return RuleDaoImpl.class;
    }

    /** 获取Logger **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    /** T Array **/
    @Override
    public PERule[] getArrayType() {
        return new PERule[] {};
    }

    /** **/
    @Override
    public List<PERule> readJson(@NotNull @NotBlank @NotEmpty final String jsonPath) throws AbstractSystemException {
        final TypeReference<List<PERule>> typeRef = new TypeReference<List<PERule>>() {
        };
        return JsonKit.fromFile(typeRef, jsonPath);
    }

    /** 设置Validator的导入 **/
    @Override
    public ServiceResult<ConcurrentMap<String, List<PERule>>> importRules(
            @NotNull @NotBlank @NotEmpty final String jsonPath, @NotNull final PEUri uri) {
        // 1.构造响应函数
        final ServiceResult<ConcurrentMap<String, List<PERule>>> result = new ServiceResult<>();
        // 2.从Json中读取ValidatorModel的列表
        List<PERule> dataList = new ArrayList<>();
        try {
            dataList = this.readJson(jsonPath);
        } catch (AbstractSystemException ex) {
            peError(getLogger(),ex);
            result.error(ex);
        }
        try {
            // 3.设置RefID的值
            for (final PERule validator : dataList) {
                validator.setRefUriId(uri.getUniqueId());
            }
            if (!dataList.isEmpty()) {
                this.getDao().insert(dataList.toArray(new PERule[] {}));
            }
        } catch (AbstractTransactionException ex) {
            peError(getLogger(),ex);
            result.error(ex);
        }
        // 4.返回最终的Result信息
        if (ResponseCode.SUCCESS == result.getResponseCode() && Constants.RC_SUCCESS == result.getErrorCode()) {
            final ConcurrentMap<String, List<PERule>> listRet = ResultExtractor.extractList(dataList, "refUriId");
            result.success(listRet);
        }
        return result;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
