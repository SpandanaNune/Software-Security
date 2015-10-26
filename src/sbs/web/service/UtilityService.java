package sbs.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbs.web.dao.UtilityDao;
import sbs.web.models.OTP;

@Service("utilityService")
public class UtilityService {
	private UtilityDao utilityDao;
	
	@Autowired
	public void setUtilityDao(UtilityDao utilityDao) {
		this.utilityDao = utilityDao;
	}

	public void insertOTP(OTP otp) {
		utilityDao.insertOTP(otp);
	}

	public OTP checkOTP(OTP otpObj) {
		return utilityDao.checkOTP(otpObj);
	}

	public void deleteOTP(OTP otpObj) {
		utilityDao.deleteOTP(otpObj);
	}

	public void updateOTP(OTP otpObj) {
		utilityDao.updateOTP(otpObj);
		
	}

}
