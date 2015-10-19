package com.prayer.model.web;

import java.io.Serializable;

import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.constant.SystemEnum.ReturnType;
import com.prayer.exception.AbstractWebException;
import com.prayer.exception.web.InternalServerErrorException;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.impl.ClusterSerializable;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 响应对象信息
 * 
 * @author Lang
 *
 */
@Guarded
public final class Responsor implements Serializable, ClusterSerializable {
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = 4549918023264047797L;
	// ~ Instance Fields =====================================
	/** HTTP状态代码 **/
	private transient StatusCode status;
	/** 抽象Web异常信息 **/
	private transient AbstractWebException error;
	/** 返回类型信息 **/
	private transient ReturnType retType;
	/** 最终返回数据信息 **/
	private transient Buffer data;
	/** 响应代码 **/
	private transient ResponseCode code;
	/** 显示信息 **/
	private transient String display;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param jsonContent
	 * @return
	 */
	public static Responsor success(@NotNull final JsonObject object) {
		final Responsor success = new Responsor(object);
		success.setDisplay(StatusCode.OK, null, Constants.EMPTY_STR);
		return success;
	}

	/**
	 * 
	 * @param array
	 * @return
	 */
	public static Responsor success(@NotNull final JsonArray array) {
		final Responsor success = new Responsor(array);
		success.setDisplay(StatusCode.OK, null, Constants.EMPTY_STR);
		return success;
	}

	/**
	 * 
	 * @param error
	 * @return
	 */
	public static Responsor error(@NotNull final AbstractWebException error) {
		return failure(StatusCode.INTERNAL_SERVER_ERROR, error, error.getErrorMessage());
	}

	/**
	 * 
	 * @return
	 */
	public static Responsor serviceError(@NotNull final Class<?> clazz) {
		final AbstractWebException error = new InternalServerErrorException(clazz);
		return failure(StatusCode.INTERNAL_SERVER_ERROR, error, error.getErrorMessage());
	}

	/**
	 * 
	 * @param status
	 * @param error
	 * @return
	 */
	public static Responsor failure(@NotNull final StatusCode status, @NotNull final AbstractWebException error) {
		return failure(status, error, error.getErrorMessage());
	}

	/**
	 * 
	 * @param status
	 * @param error
	 * @param display
	 * @return
	 */
	public static Responsor failure(@NotNull final StatusCode status, @NotNull final AbstractWebException error,
			final String display) {
		final Responsor err = new Responsor(error);
		err.setDisplay(status, error, display);
		return err;
	}

	// ~ Constructors ========================================
	/**
	 * 
	 * @param error
	 */
	private Responsor(final AbstractWebException error) {
		this.retType = ReturnType.ERROR;
		this.status = StatusCode.INTERNAL_SERVER_ERROR;
		this.error = error;
		this.code = ResponseCode.ERROR;
		this.display = error.getErrorMessage();
		this.data = Buffer.buffer();
	}

	/**
	 * 
	 * @param array
	 */
	private Responsor(final JsonArray array) {
		this.retType = ReturnType.ARRAY;
		this.status = StatusCode.OK;
		this.error = null;
		this.code = ResponseCode.SUCCESS;
		this.display = null;
		this.data = Buffer.buffer();
		array.writeToBuffer(this.data);
	}

	/**
	 * 
	 * @param content
	 */
	private Responsor(final JsonObject object) {
		this.retType = ReturnType.OBJECT;
		this.status = StatusCode.OK;
		this.code = ResponseCode.SUCCESS;
		this.data = Buffer.buffer();
		object.writeToBuffer(this.data);
		this.error = null;
		this.display = null;
	}

	// ~ Abstract Methods ====================================

	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public void writeToBuffer(final Buffer buffer) {
		// 响应结果写入到Buffer中
		this.getResult().writeToBuffer(buffer);
	}

	/**
	 * 
	 */
	@Override
	public int readFromBuffer(int pos, final Buffer buffer) {
		// 响应结果读取到Buffer中
		return this.getResult().readFromBuffer(pos, buffer);
	}

	// ~ Methods =============================================
	/**
	 * 
	 * @return
	 */
	public StatusCode getStatus() {
		return this.status;
	}

	/**
	 * 
	 * @return
	 */
	public JsonObject getResult() {
		final JsonObject result = new JsonObject();
		// 1.Status节点
		result.put(JsonKey.RESPONSOR.STATUS.NAME,
				new JsonObject().put(JsonKey.RESPONSOR.STATUS.CODE, this.status.status())
						.put(JsonKey.RESPONSOR.STATUS.LITERAL, this.status));
		// 2.Response节点
		result.put(JsonKey.RESPONSOR.RETURNCODE, this.code.name());
		// 3.数据节点
		if (null == this.error) {
			// 4.Success -> 成功返回值
			if (ReturnType.OBJECT == this.retType) {
				final JsonObject data = new JsonObject();
				data.readFromBuffer(0, this.data);
				result.put(JsonKey.RESPONSOR.DATA, data);
			} else {
				final JsonArray data = new JsonArray();
				data.readFromBuffer(0, this.data);
				result.put(JsonKey.RESPONSOR.DATA, data);
			}
		} else {
			// 4.Error -> 失败返回值
			final JsonObject error = new JsonObject();
			error.put(JsonKey.RESPONSOR.ERROR.CODE, this.getError().getErrorCode());
			error.put(JsonKey.RESPONSOR.ERROR.MESSAGE, this.getError().getErrorMessage());
			error.put(JsonKey.RESPONSOR.ERROR.DISPLAY, this.display);
			result.put(JsonKey.RESPONSOR.ERROR.NAME, error);
		}
		return result;
	}

	/**
	 * 
	 * @param status
	 * @param data
	 * @return
	 */
	public JsonObject getError(@NotNull final StatusCode status, @NotNull final JsonObject data) {
		final JsonObject result = new JsonObject();
		// 1.Status节点
		result.put(JsonKey.RESPONSOR.STATUS.NAME, new JsonObject().put(JsonKey.RESPONSOR.STATUS.CODE, status.status())
				.put(JsonKey.RESPONSOR.STATUS.LITERAL, status));
		// 2.Response节点
		result.put(JsonKey.RESPONSOR.RETURNCODE, ResponseCode.FAILURE.name());
		// 4.Error -> 失败返回值
		final JsonObject error = new JsonObject();
		error.put(JsonKey.RESPONSOR.ERROR.CODE, data.getInteger(JsonKey.RESPONSOR.ERROR.CODE));
		error.put(JsonKey.RESPONSOR.ERROR.MESSAGE, data.getString(JsonKey.RESPONSOR.ERROR.MESSAGE));
		error.put(JsonKey.RESPONSOR.ERROR.DISPLAY, data.getString(JsonKey.RESPONSOR.ERROR.DISPLAY));
		result.put(JsonKey.RESPONSOR.ERROR.NAME, error);
		return result;
	}

	/**
	 * 
	 * @return
	 */
	public AbstractWebException getError() {
		return this.error;
	}

	// ~ Private Methods =====================================
	/**
	 * 设置显示信息
	 * 
	 * @param display
	 */
	private void setDisplay(final StatusCode status, final AbstractWebException error, final String display) {
		// 三个基本信息的设置
		if (null == display) {
			this.display = error.getErrorMessage();
		} else {
			this.display = display;
		}
		this.status = status;
		this.error = error;
		// 根据Status设置信息
		if (status == StatusCode.OK) {
			this.code = ResponseCode.SUCCESS;
		} else if (status == StatusCode.INTERNAL_SERVER_ERROR) {
			this.code = ResponseCode.ERROR;
			this.retType = ReturnType.ERROR;
		} else {
			this.code = ResponseCode.FAILURE;
			this.retType = ReturnType.ERROR;
		}
	}

	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
	/** **/
	@Override
	public String toString() {
		return this.getResult().encode();
	}
}
