package com.jorge.hp.model;

/**
 * Class Json
 * 
 * @version 1.0
 * @author Jorge
 * 
 *         21/05/2021
 * 
 */

public class Json {

	public String message_type;
	public Long timestamp;
	public Long origin;
	public Long destination;
	public Integer duration;
	public String status_code;
	public String status_description;
	public String message_content;
	public String message_status;

	public Json() {

	}

	public Json(String message_type, Long timestamp, Long origin, Long destination, Integer duration,
			String status_code, String status_description, String message_content, String message_status) {
		this.message_type = message_type;
		this.timestamp = timestamp;
		this.origin = origin;
		this.destination = destination;
		this.duration = duration;
		this.status_code = status_code;
		this.status_description = status_description;
		this.message_content = message_content;
		this.message_status = message_status;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Long getOrigin() {
		return origin;
	}

	public void setOrigin(Long origin) {
		this.origin = origin;
	}

	public Long getDestination() {
		return destination;
	}

	public void setDestination(Long destination) {
		this.destination = destination;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getStatus_code() {
		return status_code;
	}

	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}

	public String getStatus_description() {
		return status_description;
	}

	public void setStatus_description(String status_description) {
		this.status_description = status_description;
	}

	public String getMessage_content() {
		return message_content;
	}

	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}

	public String getMessage_status() {
		return message_status;
	}

	public void setMessage_status(String message_status) {
		this.message_status = message_status;
	}

	@Override
	public String toString() {
		return "JsonEntity [message_type=" + message_type + ", timestamp=" + timestamp + ", origin=" + origin
				+ ", destination=" + destination + ", duration=" + duration + ", status_code=" + status_code
				+ ", status_description=" + status_description + ", message_content=" + message_content
				+ ", message_status=" + message_status + "]";
	}

}
