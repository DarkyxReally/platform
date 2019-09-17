package com.platform.auth.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RestOperatorLogEntityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RestOperatorLogEntityExample() {
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

        public Criteria andIdLogIsNull() {
            addCriterion("id_log is null");
            return (Criteria) this;
        }

        public Criteria andIdLogIsNotNull() {
            addCriterion("id_log is not null");
            return (Criteria) this;
        }

        public Criteria andIdLogEqualTo(Long value) {
            addCriterion("id_log =", value, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogNotEqualTo(Long value) {
            addCriterion("id_log <>", value, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogGreaterThan(Long value) {
            addCriterion("id_log >", value, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogGreaterThanOrEqualTo(Long value) {
            addCriterion("id_log >=", value, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogLessThan(Long value) {
            addCriterion("id_log <", value, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogLessThanOrEqualTo(Long value) {
            addCriterion("id_log <=", value, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogIn(List<Long> values) {
            addCriterion("id_log in", values, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogNotIn(List<Long> values) {
            addCriterion("id_log not in", values, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogBetween(Long value1, Long value2) {
            addCriterion("id_log between", value1, value2, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogNotBetween(Long value1, Long value2) {
            addCriterion("id_log not between", value1, value2, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdRequestIsNull() {
            addCriterion("id_request is null");
            return (Criteria) this;
        }

        public Criteria andIdRequestIsNotNull() {
            addCriterion("id_request is not null");
            return (Criteria) this;
        }

        public Criteria andIdRequestEqualTo(String value) {
            addCriterion("id_request =", value, "idRequest");
            return (Criteria) this;
        }

        public Criteria andIdRequestNotEqualTo(String value) {
            addCriterion("id_request <>", value, "idRequest");
            return (Criteria) this;
        }

        public Criteria andIdRequestGreaterThan(String value) {
            addCriterion("id_request >", value, "idRequest");
            return (Criteria) this;
        }

        public Criteria andIdRequestGreaterThanOrEqualTo(String value) {
            addCriterion("id_request >=", value, "idRequest");
            return (Criteria) this;
        }

        public Criteria andIdRequestLessThan(String value) {
            addCriterion("id_request <", value, "idRequest");
            return (Criteria) this;
        }

        public Criteria andIdRequestLessThanOrEqualTo(String value) {
            addCriterion("id_request <=", value, "idRequest");
            return (Criteria) this;
        }

        public Criteria andIdRequestLike(String value) {
            addCriterion("id_request like", value, "idRequest");
            return (Criteria) this;
        }

        public Criteria andIdRequestNotLike(String value) {
            addCriterion("id_request not like", value, "idRequest");
            return (Criteria) this;
        }

        public Criteria andIdRequestIn(List<String> values) {
            addCriterion("id_request in", values, "idRequest");
            return (Criteria) this;
        }

        public Criteria andIdRequestNotIn(List<String> values) {
            addCriterion("id_request not in", values, "idRequest");
            return (Criteria) this;
        }

        public Criteria andIdRequestBetween(String value1, String value2) {
            addCriterion("id_request between", value1, value2, "idRequest");
            return (Criteria) this;
        }

        public Criteria andIdRequestNotBetween(String value1, String value2) {
            addCriterion("id_request not between", value1, value2, "idRequest");
            return (Criteria) this;
        }

        public Criteria andReqUrlIsNull() {
            addCriterion("req_url is null");
            return (Criteria) this;
        }

        public Criteria andReqUrlIsNotNull() {
            addCriterion("req_url is not null");
            return (Criteria) this;
        }

        public Criteria andReqUrlEqualTo(String value) {
            addCriterion("req_url =", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlNotEqualTo(String value) {
            addCriterion("req_url <>", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlGreaterThan(String value) {
            addCriterion("req_url >", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlGreaterThanOrEqualTo(String value) {
            addCriterion("req_url >=", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlLessThan(String value) {
            addCriterion("req_url <", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlLessThanOrEqualTo(String value) {
            addCriterion("req_url <=", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlLike(String value) {
            addCriterion("req_url like", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlNotLike(String value) {
            addCriterion("req_url not like", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlIn(List<String> values) {
            addCriterion("req_url in", values, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlNotIn(List<String> values) {
            addCriterion("req_url not in", values, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlBetween(String value1, String value2) {
            addCriterion("req_url between", value1, value2, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlNotBetween(String value1, String value2) {
            addCriterion("req_url not between", value1, value2, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqMethodIsNull() {
            addCriterion("req_method is null");
            return (Criteria) this;
        }

        public Criteria andReqMethodIsNotNull() {
            addCriterion("req_method is not null");
            return (Criteria) this;
        }

        public Criteria andReqMethodEqualTo(String value) {
            addCriterion("req_method =", value, "reqMethod");
            return (Criteria) this;
        }

        public Criteria andReqMethodNotEqualTo(String value) {
            addCriterion("req_method <>", value, "reqMethod");
            return (Criteria) this;
        }

        public Criteria andReqMethodGreaterThan(String value) {
            addCriterion("req_method >", value, "reqMethod");
            return (Criteria) this;
        }

        public Criteria andReqMethodGreaterThanOrEqualTo(String value) {
            addCriterion("req_method >=", value, "reqMethod");
            return (Criteria) this;
        }

        public Criteria andReqMethodLessThan(String value) {
            addCriterion("req_method <", value, "reqMethod");
            return (Criteria) this;
        }

        public Criteria andReqMethodLessThanOrEqualTo(String value) {
            addCriterion("req_method <=", value, "reqMethod");
            return (Criteria) this;
        }

        public Criteria andReqMethodLike(String value) {
            addCriterion("req_method like", value, "reqMethod");
            return (Criteria) this;
        }

        public Criteria andReqMethodNotLike(String value) {
            addCriterion("req_method not like", value, "reqMethod");
            return (Criteria) this;
        }

        public Criteria andReqMethodIn(List<String> values) {
            addCriterion("req_method in", values, "reqMethod");
            return (Criteria) this;
        }

        public Criteria andReqMethodNotIn(List<String> values) {
            addCriterion("req_method not in", values, "reqMethod");
            return (Criteria) this;
        }

        public Criteria andReqMethodBetween(String value1, String value2) {
            addCriterion("req_method between", value1, value2, "reqMethod");
            return (Criteria) this;
        }

        public Criteria andReqMethodNotBetween(String value1, String value2) {
            addCriterion("req_method not between", value1, value2, "reqMethod");
            return (Criteria) this;
        }

        public Criteria andReqParamIsNull() {
            addCriterion("req_param is null");
            return (Criteria) this;
        }

        public Criteria andReqParamIsNotNull() {
            addCriterion("req_param is not null");
            return (Criteria) this;
        }

        public Criteria andReqParamEqualTo(String value) {
            addCriterion("req_param =", value, "reqParam");
            return (Criteria) this;
        }

        public Criteria andReqParamNotEqualTo(String value) {
            addCriterion("req_param <>", value, "reqParam");
            return (Criteria) this;
        }

        public Criteria andReqParamGreaterThan(String value) {
            addCriterion("req_param >", value, "reqParam");
            return (Criteria) this;
        }

        public Criteria andReqParamGreaterThanOrEqualTo(String value) {
            addCriterion("req_param >=", value, "reqParam");
            return (Criteria) this;
        }

        public Criteria andReqParamLessThan(String value) {
            addCriterion("req_param <", value, "reqParam");
            return (Criteria) this;
        }

        public Criteria andReqParamLessThanOrEqualTo(String value) {
            addCriterion("req_param <=", value, "reqParam");
            return (Criteria) this;
        }

        public Criteria andReqParamLike(String value) {
            addCriterion("req_param like", value, "reqParam");
            return (Criteria) this;
        }

        public Criteria andReqParamNotLike(String value) {
            addCriterion("req_param not like", value, "reqParam");
            return (Criteria) this;
        }

        public Criteria andReqParamIn(List<String> values) {
            addCriterion("req_param in", values, "reqParam");
            return (Criteria) this;
        }

        public Criteria andReqParamNotIn(List<String> values) {
            addCriterion("req_param not in", values, "reqParam");
            return (Criteria) this;
        }

        public Criteria andReqParamBetween(String value1, String value2) {
            addCriterion("req_param between", value1, value2, "reqParam");
            return (Criteria) this;
        }

        public Criteria andReqParamNotBetween(String value1, String value2) {
            addCriterion("req_param not between", value1, value2, "reqParam");
            return (Criteria) this;
        }

        public Criteria andReqHeaderIsNull() {
            addCriterion("req_header is null");
            return (Criteria) this;
        }

        public Criteria andReqHeaderIsNotNull() {
            addCriterion("req_header is not null");
            return (Criteria) this;
        }

        public Criteria andReqHeaderEqualTo(String value) {
            addCriterion("req_header =", value, "reqHeader");
            return (Criteria) this;
        }

        public Criteria andReqHeaderNotEqualTo(String value) {
            addCriterion("req_header <>", value, "reqHeader");
            return (Criteria) this;
        }

        public Criteria andReqHeaderGreaterThan(String value) {
            addCriterion("req_header >", value, "reqHeader");
            return (Criteria) this;
        }

        public Criteria andReqHeaderGreaterThanOrEqualTo(String value) {
            addCriterion("req_header >=", value, "reqHeader");
            return (Criteria) this;
        }

        public Criteria andReqHeaderLessThan(String value) {
            addCriterion("req_header <", value, "reqHeader");
            return (Criteria) this;
        }

        public Criteria andReqHeaderLessThanOrEqualTo(String value) {
            addCriterion("req_header <=", value, "reqHeader");
            return (Criteria) this;
        }

        public Criteria andReqHeaderLike(String value) {
            addCriterion("req_header like", value, "reqHeader");
            return (Criteria) this;
        }

        public Criteria andReqHeaderNotLike(String value) {
            addCriterion("req_header not like", value, "reqHeader");
            return (Criteria) this;
        }

        public Criteria andReqHeaderIn(List<String> values) {
            addCriterion("req_header in", values, "reqHeader");
            return (Criteria) this;
        }

        public Criteria andReqHeaderNotIn(List<String> values) {
            addCriterion("req_header not in", values, "reqHeader");
            return (Criteria) this;
        }

        public Criteria andReqHeaderBetween(String value1, String value2) {
            addCriterion("req_header between", value1, value2, "reqHeader");
            return (Criteria) this;
        }

        public Criteria andReqHeaderNotBetween(String value1, String value2) {
            addCriterion("req_header not between", value1, value2, "reqHeader");
            return (Criteria) this;
        }

        public Criteria andReqIpIsNull() {
            addCriterion("req_ip is null");
            return (Criteria) this;
        }

        public Criteria andReqIpIsNotNull() {
            addCriterion("req_ip is not null");
            return (Criteria) this;
        }

        public Criteria andReqIpEqualTo(String value) {
            addCriterion("req_ip =", value, "reqIp");
            return (Criteria) this;
        }

        public Criteria andReqIpNotEqualTo(String value) {
            addCriterion("req_ip <>", value, "reqIp");
            return (Criteria) this;
        }

        public Criteria andReqIpGreaterThan(String value) {
            addCriterion("req_ip >", value, "reqIp");
            return (Criteria) this;
        }

        public Criteria andReqIpGreaterThanOrEqualTo(String value) {
            addCriterion("req_ip >=", value, "reqIp");
            return (Criteria) this;
        }

        public Criteria andReqIpLessThan(String value) {
            addCriterion("req_ip <", value, "reqIp");
            return (Criteria) this;
        }

        public Criteria andReqIpLessThanOrEqualTo(String value) {
            addCriterion("req_ip <=", value, "reqIp");
            return (Criteria) this;
        }

        public Criteria andReqIpLike(String value) {
            addCriterion("req_ip like", value, "reqIp");
            return (Criteria) this;
        }

        public Criteria andReqIpNotLike(String value) {
            addCriterion("req_ip not like", value, "reqIp");
            return (Criteria) this;
        }

        public Criteria andReqIpIn(List<String> values) {
            addCriterion("req_ip in", values, "reqIp");
            return (Criteria) this;
        }

        public Criteria andReqIpNotIn(List<String> values) {
            addCriterion("req_ip not in", values, "reqIp");
            return (Criteria) this;
        }

        public Criteria andReqIpBetween(String value1, String value2) {
            addCriterion("req_ip between", value1, value2, "reqIp");
            return (Criteria) this;
        }

        public Criteria andReqIpNotBetween(String value1, String value2) {
            addCriterion("req_ip not between", value1, value2, "reqIp");
            return (Criteria) this;
        }

        public Criteria andUserIpIsNull() {
            addCriterion("user_ip is null");
            return (Criteria) this;
        }

        public Criteria andUserIpIsNotNull() {
            addCriterion("user_ip is not null");
            return (Criteria) this;
        }

        public Criteria andUserIpEqualTo(String value) {
            addCriterion("user_ip =", value, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpNotEqualTo(String value) {
            addCriterion("user_ip <>", value, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpGreaterThan(String value) {
            addCriterion("user_ip >", value, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpGreaterThanOrEqualTo(String value) {
            addCriterion("user_ip >=", value, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpLessThan(String value) {
            addCriterion("user_ip <", value, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpLessThanOrEqualTo(String value) {
            addCriterion("user_ip <=", value, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpLike(String value) {
            addCriterion("user_ip like", value, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpNotLike(String value) {
            addCriterion("user_ip not like", value, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpIn(List<String> values) {
            addCriterion("user_ip in", values, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpNotIn(List<String> values) {
            addCriterion("user_ip not in", values, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpBetween(String value1, String value2) {
            addCriterion("user_ip between", value1, value2, "userIp");
            return (Criteria) this;
        }

        public Criteria andUserIpNotBetween(String value1, String value2) {
            addCriterion("user_ip not between", value1, value2, "userIp");
            return (Criteria) this;
        }

        public Criteria andReqDateIsNull() {
            addCriterion("req_date is null");
            return (Criteria) this;
        }

        public Criteria andReqDateIsNotNull() {
            addCriterion("req_date is not null");
            return (Criteria) this;
        }

        public Criteria andReqDateEqualTo(Date value) {
            addCriterion("req_date =", value, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateNotEqualTo(Date value) {
            addCriterion("req_date <>", value, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateGreaterThan(Date value) {
            addCriterion("req_date >", value, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateGreaterThanOrEqualTo(Date value) {
            addCriterion("req_date >=", value, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateLessThan(Date value) {
            addCriterion("req_date <", value, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateLessThanOrEqualTo(Date value) {
            addCriterion("req_date <=", value, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateIn(List<Date> values) {
            addCriterion("req_date in", values, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateNotIn(List<Date> values) {
            addCriterion("req_date not in", values, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateBetween(Date value1, Date value2) {
            addCriterion("req_date between", value1, value2, "reqDate");
            return (Criteria) this;
        }

        public Criteria andReqDateNotBetween(Date value1, Date value2) {
            addCriterion("req_date not between", value1, value2, "reqDate");
            return (Criteria) this;
        }

        public Criteria andIdUserIsNull() {
            addCriterion("id_user is null");
            return (Criteria) this;
        }

        public Criteria andIdUserIsNotNull() {
            addCriterion("id_user is not null");
            return (Criteria) this;
        }

        public Criteria andIdUserEqualTo(String value) {
            addCriterion("id_user =", value, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserNotEqualTo(String value) {
            addCriterion("id_user <>", value, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserGreaterThan(String value) {
            addCriterion("id_user >", value, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserGreaterThanOrEqualTo(String value) {
            addCriterion("id_user >=", value, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserLessThan(String value) {
            addCriterion("id_user <", value, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserLessThanOrEqualTo(String value) {
            addCriterion("id_user <=", value, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserLike(String value) {
            addCriterion("id_user like", value, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserNotLike(String value) {
            addCriterion("id_user not like", value, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserIn(List<String> values) {
            addCriterion("id_user in", values, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserNotIn(List<String> values) {
            addCriterion("id_user not in", values, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserBetween(String value1, String value2) {
            addCriterion("id_user between", value1, value2, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserNotBetween(String value1, String value2) {
            addCriterion("id_user not between", value1, value2, "idUser");
            return (Criteria) this;
        }

        public Criteria andServiceNameIsNull() {
            addCriterion("service_name is null");
            return (Criteria) this;
        }

        public Criteria andServiceNameIsNotNull() {
            addCriterion("service_name is not null");
            return (Criteria) this;
        }

        public Criteria andServiceNameEqualTo(String value) {
            addCriterion("service_name =", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotEqualTo(String value) {
            addCriterion("service_name <>", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameGreaterThan(String value) {
            addCriterion("service_name >", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameGreaterThanOrEqualTo(String value) {
            addCriterion("service_name >=", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLessThan(String value) {
            addCriterion("service_name <", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLessThanOrEqualTo(String value) {
            addCriterion("service_name <=", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLike(String value) {
            addCriterion("service_name like", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotLike(String value) {
            addCriterion("service_name not like", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameIn(List<String> values) {
            addCriterion("service_name in", values, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotIn(List<String> values) {
            addCriterion("service_name not in", values, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameBetween(String value1, String value2) {
            addCriterion("service_name between", value1, value2, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotBetween(String value1, String value2) {
            addCriterion("service_name not between", value1, value2, "serviceName");
            return (Criteria) this;
        }

        public Criteria andSystemCodeIsNull() {
            addCriterion("system_code is null");
            return (Criteria) this;
        }

        public Criteria andSystemCodeIsNotNull() {
            addCriterion("system_code is not null");
            return (Criteria) this;
        }

        public Criteria andSystemCodeEqualTo(String value) {
            addCriterion("system_code =", value, "systemCode");
            return (Criteria) this;
        }

        public Criteria andSystemCodeNotEqualTo(String value) {
            addCriterion("system_code <>", value, "systemCode");
            return (Criteria) this;
        }

        public Criteria andSystemCodeGreaterThan(String value) {
            addCriterion("system_code >", value, "systemCode");
            return (Criteria) this;
        }

        public Criteria andSystemCodeGreaterThanOrEqualTo(String value) {
            addCriterion("system_code >=", value, "systemCode");
            return (Criteria) this;
        }

        public Criteria andSystemCodeLessThan(String value) {
            addCriterion("system_code <", value, "systemCode");
            return (Criteria) this;
        }

        public Criteria andSystemCodeLessThanOrEqualTo(String value) {
            addCriterion("system_code <=", value, "systemCode");
            return (Criteria) this;
        }

        public Criteria andSystemCodeLike(String value) {
            addCriterion("system_code like", value, "systemCode");
            return (Criteria) this;
        }

        public Criteria andSystemCodeNotLike(String value) {
            addCriterion("system_code not like", value, "systemCode");
            return (Criteria) this;
        }

        public Criteria andSystemCodeIn(List<String> values) {
            addCriterion("system_code in", values, "systemCode");
            return (Criteria) this;
        }

        public Criteria andSystemCodeNotIn(List<String> values) {
            addCriterion("system_code not in", values, "systemCode");
            return (Criteria) this;
        }

        public Criteria andSystemCodeBetween(String value1, String value2) {
            addCriterion("system_code between", value1, value2, "systemCode");
            return (Criteria) this;
        }

        public Criteria andSystemCodeNotBetween(String value1, String value2) {
            addCriterion("system_code not between", value1, value2, "systemCode");
            return (Criteria) this;
        }

        public Criteria andModuleIsNull() {
            addCriterion("module is null");
            return (Criteria) this;
        }

        public Criteria andModuleIsNotNull() {
            addCriterion("module is not null");
            return (Criteria) this;
        }

        public Criteria andModuleEqualTo(String value) {
            addCriterion("module =", value, "module");
            return (Criteria) this;
        }

        public Criteria andModuleNotEqualTo(String value) {
            addCriterion("module <>", value, "module");
            return (Criteria) this;
        }

        public Criteria andModuleGreaterThan(String value) {
            addCriterion("module >", value, "module");
            return (Criteria) this;
        }

        public Criteria andModuleGreaterThanOrEqualTo(String value) {
            addCriterion("module >=", value, "module");
            return (Criteria) this;
        }

        public Criteria andModuleLessThan(String value) {
            addCriterion("module <", value, "module");
            return (Criteria) this;
        }

        public Criteria andModuleLessThanOrEqualTo(String value) {
            addCriterion("module <=", value, "module");
            return (Criteria) this;
        }

        public Criteria andModuleLike(String value) {
            addCriterion("module like", value, "module");
            return (Criteria) this;
        }

        public Criteria andModuleNotLike(String value) {
            addCriterion("module not like", value, "module");
            return (Criteria) this;
        }

        public Criteria andModuleIn(List<String> values) {
            addCriterion("module in", values, "module");
            return (Criteria) this;
        }

        public Criteria andModuleNotIn(List<String> values) {
            addCriterion("module not in", values, "module");
            return (Criteria) this;
        }

        public Criteria andModuleBetween(String value1, String value2) {
            addCriterion("module between", value1, value2, "module");
            return (Criteria) this;
        }

        public Criteria andModuleNotBetween(String value1, String value2) {
            addCriterion("module not between", value1, value2, "module");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andIdClientIsNull() {
            addCriterion("id_client is null");
            return (Criteria) this;
        }

        public Criteria andIdClientIsNotNull() {
            addCriterion("id_client is not null");
            return (Criteria) this;
        }

        public Criteria andIdClientEqualTo(String value) {
            addCriterion("id_client =", value, "idClient");
            return (Criteria) this;
        }

        public Criteria andIdClientNotEqualTo(String value) {
            addCriterion("id_client <>", value, "idClient");
            return (Criteria) this;
        }

        public Criteria andIdClientGreaterThan(String value) {
            addCriterion("id_client >", value, "idClient");
            return (Criteria) this;
        }

        public Criteria andIdClientGreaterThanOrEqualTo(String value) {
            addCriterion("id_client >=", value, "idClient");
            return (Criteria) this;
        }

        public Criteria andIdClientLessThan(String value) {
            addCriterion("id_client <", value, "idClient");
            return (Criteria) this;
        }

        public Criteria andIdClientLessThanOrEqualTo(String value) {
            addCriterion("id_client <=", value, "idClient");
            return (Criteria) this;
        }

        public Criteria andIdClientLike(String value) {
            addCriterion("id_client like", value, "idClient");
            return (Criteria) this;
        }

        public Criteria andIdClientNotLike(String value) {
            addCriterion("id_client not like", value, "idClient");
            return (Criteria) this;
        }

        public Criteria andIdClientIn(List<String> values) {
            addCriterion("id_client in", values, "idClient");
            return (Criteria) this;
        }

        public Criteria andIdClientNotIn(List<String> values) {
            addCriterion("id_client not in", values, "idClient");
            return (Criteria) this;
        }

        public Criteria andIdClientBetween(String value1, String value2) {
            addCriterion("id_client between", value1, value2, "idClient");
            return (Criteria) this;
        }

        public Criteria andIdClientNotBetween(String value1, String value2) {
            addCriterion("id_client not between", value1, value2, "idClient");
            return (Criteria) this;
        }

        public Criteria andDateCreatedIsNull() {
            addCriterion("date_created is null");
            return (Criteria) this;
        }

        public Criteria andDateCreatedIsNotNull() {
            addCriterion("date_created is not null");
            return (Criteria) this;
        }

        public Criteria andDateCreatedEqualTo(Date value) {
            addCriterion("date_created =", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedNotEqualTo(Date value) {
            addCriterion("date_created <>", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedGreaterThan(Date value) {
            addCriterion("date_created >", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedGreaterThanOrEqualTo(Date value) {
            addCriterion("date_created >=", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedLessThan(Date value) {
            addCriterion("date_created <", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedLessThanOrEqualTo(Date value) {
            addCriterion("date_created <=", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedIn(List<Date> values) {
            addCriterion("date_created in", values, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedNotIn(List<Date> values) {
            addCriterion("date_created not in", values, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedBetween(Date value1, Date value2) {
            addCriterion("date_created between", value1, value2, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedNotBetween(Date value1, Date value2) {
            addCriterion("date_created not between", value1, value2, "dateCreated");
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