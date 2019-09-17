package com.platform.user.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserThirdEntityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserThirdEntityExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andThirdTypeIsNull() {
            addCriterion("third_type is null");
            return (Criteria) this;
        }

        public Criteria andThirdTypeIsNotNull() {
            addCriterion("third_type is not null");
            return (Criteria) this;
        }

        public Criteria andThirdTypeEqualTo(Integer value) {
            addCriterion("third_type =", value, "thirdType");
            return (Criteria) this;
        }

        public Criteria andThirdTypeNotEqualTo(Integer value) {
            addCriterion("third_type <>", value, "thirdType");
            return (Criteria) this;
        }

        public Criteria andThirdTypeGreaterThan(Integer value) {
            addCriterion("third_type >", value, "thirdType");
            return (Criteria) this;
        }

        public Criteria andThirdTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("third_type >=", value, "thirdType");
            return (Criteria) this;
        }

        public Criteria andThirdTypeLessThan(Integer value) {
            addCriterion("third_type <", value, "thirdType");
            return (Criteria) this;
        }

        public Criteria andThirdTypeLessThanOrEqualTo(Integer value) {
            addCriterion("third_type <=", value, "thirdType");
            return (Criteria) this;
        }

        public Criteria andThirdTypeIn(List<Integer> values) {
            addCriterion("third_type in", values, "thirdType");
            return (Criteria) this;
        }

        public Criteria andThirdTypeNotIn(List<Integer> values) {
            addCriterion("third_type not in", values, "thirdType");
            return (Criteria) this;
        }

        public Criteria andThirdTypeBetween(Integer value1, Integer value2) {
            addCriterion("third_type between", value1, value2, "thirdType");
            return (Criteria) this;
        }

        public Criteria andThirdTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("third_type not between", value1, value2, "thirdType");
            return (Criteria) this;
        }

        public Criteria andThirdUuidIsNull() {
            addCriterion("third_uuid is null");
            return (Criteria) this;
        }

        public Criteria andThirdUuidIsNotNull() {
            addCriterion("third_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andThirdUuidEqualTo(String value) {
            addCriterion("third_uuid =", value, "thirdUuid");
            return (Criteria) this;
        }

        public Criteria andThirdUuidNotEqualTo(String value) {
            addCriterion("third_uuid <>", value, "thirdUuid");
            return (Criteria) this;
        }

        public Criteria andThirdUuidGreaterThan(String value) {
            addCriterion("third_uuid >", value, "thirdUuid");
            return (Criteria) this;
        }

        public Criteria andThirdUuidGreaterThanOrEqualTo(String value) {
            addCriterion("third_uuid >=", value, "thirdUuid");
            return (Criteria) this;
        }

        public Criteria andThirdUuidLessThan(String value) {
            addCriterion("third_uuid <", value, "thirdUuid");
            return (Criteria) this;
        }

        public Criteria andThirdUuidLessThanOrEqualTo(String value) {
            addCriterion("third_uuid <=", value, "thirdUuid");
            return (Criteria) this;
        }

        public Criteria andThirdUuidLike(String value) {
            addCriterion("third_uuid like", value, "thirdUuid");
            return (Criteria) this;
        }

        public Criteria andThirdUuidNotLike(String value) {
            addCriterion("third_uuid not like", value, "thirdUuid");
            return (Criteria) this;
        }

        public Criteria andThirdUuidIn(List<String> values) {
            addCriterion("third_uuid in", values, "thirdUuid");
            return (Criteria) this;
        }

        public Criteria andThirdUuidNotIn(List<String> values) {
            addCriterion("third_uuid not in", values, "thirdUuid");
            return (Criteria) this;
        }

        public Criteria andThirdUuidBetween(String value1, String value2) {
            addCriterion("third_uuid between", value1, value2, "thirdUuid");
            return (Criteria) this;
        }

        public Criteria andThirdUuidNotBetween(String value1, String value2) {
            addCriterion("third_uuid not between", value1, value2, "thirdUuid");
            return (Criteria) this;
        }

        public Criteria andThirdUrlIsNull() {
            addCriterion("third_url is null");
            return (Criteria) this;
        }

        public Criteria andThirdUrlIsNotNull() {
            addCriterion("third_url is not null");
            return (Criteria) this;
        }

        public Criteria andThirdUrlEqualTo(String value) {
            addCriterion("third_url =", value, "thirdUrl");
            return (Criteria) this;
        }

        public Criteria andThirdUrlNotEqualTo(String value) {
            addCriterion("third_url <>", value, "thirdUrl");
            return (Criteria) this;
        }

        public Criteria andThirdUrlGreaterThan(String value) {
            addCriterion("third_url >", value, "thirdUrl");
            return (Criteria) this;
        }

        public Criteria andThirdUrlGreaterThanOrEqualTo(String value) {
            addCriterion("third_url >=", value, "thirdUrl");
            return (Criteria) this;
        }

        public Criteria andThirdUrlLessThan(String value) {
            addCriterion("third_url <", value, "thirdUrl");
            return (Criteria) this;
        }

        public Criteria andThirdUrlLessThanOrEqualTo(String value) {
            addCriterion("third_url <=", value, "thirdUrl");
            return (Criteria) this;
        }

        public Criteria andThirdUrlLike(String value) {
            addCriterion("third_url like", value, "thirdUrl");
            return (Criteria) this;
        }

        public Criteria andThirdUrlNotLike(String value) {
            addCriterion("third_url not like", value, "thirdUrl");
            return (Criteria) this;
        }

        public Criteria andThirdUrlIn(List<String> values) {
            addCriterion("third_url in", values, "thirdUrl");
            return (Criteria) this;
        }

        public Criteria andThirdUrlNotIn(List<String> values) {
            addCriterion("third_url not in", values, "thirdUrl");
            return (Criteria) this;
        }

        public Criteria andThirdUrlBetween(String value1, String value2) {
            addCriterion("third_url between", value1, value2, "thirdUrl");
            return (Criteria) this;
        }

        public Criteria andThirdUrlNotBetween(String value1, String value2) {
            addCriterion("third_url not between", value1, value2, "thirdUrl");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIsNull() {
            addCriterion("created_user is null");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIsNotNull() {
            addCriterion("created_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedUserEqualTo(String value) {
            addCriterion("created_user =", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserNotEqualTo(String value) {
            addCriterion("created_user <>", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserGreaterThan(String value) {
            addCriterion("created_user >", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserGreaterThanOrEqualTo(String value) {
            addCriterion("created_user >=", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserLessThan(String value) {
            addCriterion("created_user <", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserLessThanOrEqualTo(String value) {
            addCriterion("created_user <=", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserLike(String value) {
            addCriterion("created_user like", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserNotLike(String value) {
            addCriterion("created_user not like", value, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIn(List<String> values) {
            addCriterion("created_user in", values, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserNotIn(List<String> values) {
            addCriterion("created_user not in", values, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserBetween(String value1, String value2) {
            addCriterion("created_user between", value1, value2, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedUserNotBetween(String value1, String value2) {
            addCriterion("created_user not between", value1, value2, "createdUser");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNull() {
            addCriterion("created_date is null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNotNull() {
            addCriterion("created_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateEqualTo(Date value) {
            addCriterion("created_date =", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotEqualTo(Date value) {
            addCriterion("created_date <>", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThan(Date value) {
            addCriterion("created_date >", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("created_date >=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThan(Date value) {
            addCriterion("created_date <", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThanOrEqualTo(Date value) {
            addCriterion("created_date <=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIn(List<Date> values) {
            addCriterion("created_date in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotIn(List<Date> values) {
            addCriterion("created_date not in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateBetween(Date value1, Date value2) {
            addCriterion("created_date between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotBetween(Date value1, Date value2) {
            addCriterion("created_date not between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserIsNull() {
            addCriterion("updated_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserIsNotNull() {
            addCriterion("updated_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserEqualTo(String value) {
            addCriterion("updated_user =", value, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserNotEqualTo(String value) {
            addCriterion("updated_user <>", value, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserGreaterThan(String value) {
            addCriterion("updated_user >", value, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserGreaterThanOrEqualTo(String value) {
            addCriterion("updated_user >=", value, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserLessThan(String value) {
            addCriterion("updated_user <", value, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserLessThanOrEqualTo(String value) {
            addCriterion("updated_user <=", value, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserLike(String value) {
            addCriterion("updated_user like", value, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserNotLike(String value) {
            addCriterion("updated_user not like", value, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserIn(List<String> values) {
            addCriterion("updated_user in", values, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserNotIn(List<String> values) {
            addCriterion("updated_user not in", values, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserBetween(String value1, String value2) {
            addCriterion("updated_user between", value1, value2, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedUserNotBetween(String value1, String value2) {
            addCriterion("updated_user not between", value1, value2, "updatedUser");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIsNull() {
            addCriterion("updated_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIsNotNull() {
            addCriterion("updated_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateEqualTo(Date value) {
            addCriterion("updated_date =", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotEqualTo(Date value) {
            addCriterion("updated_date <>", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateGreaterThan(Date value) {
            addCriterion("updated_date >", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("updated_date >=", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateLessThan(Date value) {
            addCriterion("updated_date <", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateLessThanOrEqualTo(Date value) {
            addCriterion("updated_date <=", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIn(List<Date> values) {
            addCriterion("updated_date in", values, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotIn(List<Date> values) {
            addCriterion("updated_date not in", values, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateBetween(Date value1, Date value2) {
            addCriterion("updated_date between", value1, value2, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotBetween(Date value1, Date value2) {
            addCriterion("updated_date not between", value1, value2, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andSerialNumberIsNull() {
            addCriterion("serial_number is null");
            return (Criteria) this;
        }

        public Criteria andSerialNumberIsNotNull() {
            addCriterion("serial_number is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNumberEqualTo(Long value) {
            addCriterion("serial_number =", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotEqualTo(Long value) {
            addCriterion("serial_number <>", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberGreaterThan(Long value) {
            addCriterion("serial_number >", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberGreaterThanOrEqualTo(Long value) {
            addCriterion("serial_number >=", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberLessThan(Long value) {
            addCriterion("serial_number <", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberLessThanOrEqualTo(Long value) {
            addCriterion("serial_number <=", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberIn(List<Long> values) {
            addCriterion("serial_number in", values, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotIn(List<Long> values) {
            addCriterion("serial_number not in", values, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberBetween(Long value1, Long value2) {
            addCriterion("serial_number between", value1, value2, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotBetween(Long value1, Long value2) {
            addCriterion("serial_number not between", value1, value2, "serialNumber");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}