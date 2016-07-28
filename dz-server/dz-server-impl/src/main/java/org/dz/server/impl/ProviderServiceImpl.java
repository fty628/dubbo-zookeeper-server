package org.dz.server.impl;

import org.dz.server.api.IProviderService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Hello world!
 *
 */
@Service("providerService")
@Component
public class ProviderServiceImpl implements IProviderService {

	@Override
	public String saySomething(String name) {
		if(name != null && !"".equals(name)){
			return  name + " Hello World!!!";
		}
		return "Hello World!!!";
	}
}
