package com.mxs.common.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class UserContext implements UserDetails, Serializable {


   private static final long serialVersionUID = 1L;

   private Long id;
   /**
    * 用户名
    */
   private String username;
   /**
    * 密码
    */
   private String password;
   /**
    * 电话
    */
   private String phone;
   /**
    * 科室
    */
   private String dept;
   /**
    * 用户类型 1-临床医生 2-病理科医生 3-手术室护士 4-保障科护士
    */
   private Integer customerType;
   /**
    * 密码加密盐值
    */
   private String salt;
   /**
    * 唯一标识码
    */
   private String idCode;
   /**
    * 用户状态 1-正常 2-锁定
    */
   private Integer status;
   /**
    * 登录次数
    */
   private Integer loginTimes;
   /**
    * 密码过期时间
    */
   private Date passwordExpireTime;
   /**
    * 上次登录IP
    */
   private String lastLoginIp;
   /**
    * 上次登录时间
    */
   private Date lastLoginTime;
   /**
    * 登录用户中文姓名
    */
   private String nameCn;

   private List<RoleContext> authorities;

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @JsonIgnore
   @Override
   public boolean isEnabled() {
      return true;
   }
}
