package com.logstash;

public class JSONOperation<T> {

	private int code;
	private String message;
	private T data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public JSONOperation(int code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static JSONOperation<?> fail(String message) {
		return JSONOperation.builder().message(message).code(500).build();
	}

	public static JSONOperation<?> success(String message, Object data) {
		return JSONOperation.builder().message(message).code(200).data(data).build();
	}

	public static JSONOperation<?> success(Object data) {
		return JSONOperation.builder().message("操作成功").code(200).data(data).build();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder<T> {

		private int code;
		private String message;
		private T data;

		public Builder<T> code(final int code) {
			this.code = code;
			return this;
		}

		public Builder<T> message(final String message) {
			this.message = message;
			return this;
		}

		public Builder<T> data(final T data) {
			this.data = data;
			return this;
		}

		public JSONOperation<T> build() {
			return new JSONOperation<T>(code, message, data);
		}
	}
}
