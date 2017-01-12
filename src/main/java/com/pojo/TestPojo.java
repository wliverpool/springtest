package com.pojo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.util.CannotContainSpaces;

/**
 * hibernate-validate应用
 * @author 吴福明
 *
 */
public class TestPojo {
	
	/**  
     * @Null   被注释的元素必须为 null       
     * @NotNull    被注释的元素必须不为 null       
     * @AssertTrue     被注释的元素必须为 true       
     * @AssertFalse    被注释的元素必须为 false       
     * @Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值       
     * @Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值       
     * @DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值       
     * @DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值       
     * @Size(max=, min=)   被注释的元素的大小必须在指定的范围内       
     * @Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内       
     * @Past   被注释的元素必须是一个过去的日期       
     * @Future     被注释的元素必须是一个将来的日期       
     * @Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式       
     * Hibernate Validator 附加的 constraint       
     * @NotBlank(message =)   验证字符串非null，且长度必须大于0       
     * @Email  被注释的元素必须是电子邮箱地址       
     * @Length(min=,max=)  被注释的字符串的大小必须在指定的范围内       
     * @NotEmpty   被注释的字符串的必须非空       
     * @Range(min=,max=,message=)  被注释的元素必须在合适的范围内 
     */
	
	@NotEmpty(message="{userName.error.required}")
	@Size(min=3,max=6,message="{userName.error.length}")
	private String userName;
	
	@Min(value=0,message="成绩不能小于{value}")
	@Max(value=100,message="成绩不能大雨{value}")
	private Integer score;
	
	@NotEmpty(message="手机号不能为空")
	@Pattern(regexp="^1[34578]\\d{9}$",message="手机格式不正确")
	private String phone;
	
	//使用自定义验证器
	@CannotContainSpaces
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
