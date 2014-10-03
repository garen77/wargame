package com.wg.services.api;

import com.wg.criteria.MailSenderCriteria;
import com.wg.result.MailSenderResult;

public interface IMailSenderServices extends IService{

	public static final String SERVICE_NAME = "mailSenderServices";
	
	public MailSenderResult sendMail(MailSenderCriteria criteria);
}
