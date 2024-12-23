package org.egov.vendor.web.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-01-06T05:34:12.238Z[GMT]")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditDetails {

	@JsonProperty("createdBy")
	private String createdBy = null;

	@JsonProperty("lastModifiedBy")
	private String lastModifiedBy = null;

	@JsonProperty("createdTime")
	private Long createdTime = null;

	@JsonProperty("lastModifiedTime")
	private Long lastModifiedTime = null;

	public AuditDetails createdBy(String createdBy) {
		this.createdBy = createdBy;
		return this;
	}

	/**
	 * username (preferred) or userid of the user that created the object
	 * 
	 * @return createdBy
	 **/
	//@Schema(description = "username (preferred) or userid of the user that created the object")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public AuditDetails lastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
		return this;
	}

	/**
	 * username (preferred) or userid of the user that last modified the object
	 * 
	 * @return lastModifiedBy
	 **/
	//@Schema(description = "username (preferred) or userid of the user that last modified the object")
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public AuditDetails createdTime(Long createdTime) {
		this.createdTime = createdTime;
		return this;
	}

	/**
	 * epoch of the time object is created
	 * 
	 * @return createdTime
	 **/
	//@Schema(description = "epoch of the time object is created")
	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public AuditDetails lastModifiedTime(Long lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
		return this;
	}

	/**
	 * epoch of the time object is last modified
	 * 
	 * @return lastModifiedTime
	 **/
	//@Schema(description = "epoch of the time object is last modified")
	public Long getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Long lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AuditDetails auditDetails = (AuditDetails) o;
		return Objects.equals(this.createdBy, auditDetails.createdBy)
				&& Objects.equals(this.lastModifiedBy, auditDetails.lastModifiedBy)
				&& Objects.equals(this.createdTime, auditDetails.createdTime)
				&& Objects.equals(this.lastModifiedTime, auditDetails.lastModifiedTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdBy, lastModifiedBy, createdTime, lastModifiedTime);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class AuditDetails {\n");

		sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
		sb.append("    lastModifiedBy: ").append(toIndentedString(lastModifiedBy)).append("\n");
		sb.append("    createdTime: ").append(toIndentedString(createdTime)).append("\n");
		sb.append("    lastModifiedTime: ").append(toIndentedString(lastModifiedTime)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
