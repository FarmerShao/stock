package com.farmershao.stock.trade.exception;

/**
 * 异常类
 *
 * Exception for the trade SDK
 */
public class TradeException extends RuntimeException {

	private static final long serialVersionUID = -5200798767047417699L;
	private int resultCode = 1;
	private Object messageObject;

	public TradeException(Object messageObject, int resultCode) {
		super(messageObject == null ? null : messageObject.toString());
		this.resultCode = resultCode;
		this.messageObject = messageObject;
	}

	public Object getMessageObject() {
		return messageObject;
	}

	public TradeException(Object messageObject) {
		this(messageObject, 1);
	}

	public TradeException(Object messageObject, Throwable cause) {
		super(messageObject == null ? null : messageObject.toString(), cause);
		this.messageObject = messageObject;
	}

	public TradeException(Throwable cause) {
		super(cause);
	}

	public int getResultCode() {
		return resultCode;
	}

}
