package com.platform.system.common.exception.business;

import com.platform.system.common.rest.RestStatus;

/**
 * @version: 1.0
 */
public class CouponPackageException extends RuntimeException {
	private static final long serialVersionUID = -7448774490278229552L;
	private int idCouponNo;
    private RestStatus restStatus;

	public CouponPackageException(int idCouponNo, RestStatus restStatus, String message ) {
		super(message);
		this.idCouponNo = idCouponNo;
		this.restStatus = restStatus;
	}

	public CouponPackageException() {
		super();
	}

	public CouponPackageException(String message) {
		super(message);
	}

	public CouponPackageException(String message, Throwable cause) {
		super(message, cause);
	}

	public int getIdCouponNo() {
		return idCouponNo;
	}
	public void setIdCouponNo(int idCouponNo) {
		this.idCouponNo = idCouponNo;
	}
	public RestStatus getRestStatus() {
		return restStatus;
	}
	public void setRestStatus(RestStatus restStatus) {
		this.restStatus = restStatus;
	}
	

}
