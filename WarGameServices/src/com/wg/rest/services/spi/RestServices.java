package com.wg.rest.services.spi;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.wg.services.api.IService;
import com.wg.services.spi.GenericService;

public class RestServices<T> extends GenericService implements IService {

	private JAXBContext jaxbContext = null;
	
	public T unmarshallObject(T obj,String ser) throws JAXBException {
		
		jaxbContext = JAXBContext.newInstance(obj.getClass());
		Unmarshaller um = jaxbContext.createUnmarshaller();
		StringReader reader = new StringReader(ser);
		
		return (T)um.unmarshal(reader);
	}

	public String marshallObject(T dto) throws JAXBException {

		jaxbContext = JAXBContext.newInstance(dto.getClass());
		Marshaller m = jaxbContext.createMarshaller();
		StringWriter write = new StringWriter();
		m.marshal(dto, write);
		return write.toString();
	}

}
