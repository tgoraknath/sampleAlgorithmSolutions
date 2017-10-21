package com.walmart.wls.util;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import com.fasterxml.jackson.datatype.guava.GuavaModule;


/**
 * Utilize JsonUtil for marshalling/unmarshalling POJO instances.
 * results may vary if the marshalling/unmarshalling applied using default implementation of ObjectMapper class.
 * <P>JsonUtil provides static APIs to marshal and un marshal POJO objects.</P>
 *
 * @author <a href="mailto:gtulla@walmartlabs.com">Gorak Tulla</a>
 * @version $Id: $Id
 */
public final class JsonUtil {
	//private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper objectMapper;
	/**
	 * static block for JsonUtil class
	 */
    static {
    	objectMapper = new ObjectMapper();
        objectMapper.registerModule(new GuavaModule());
        objectMapper.setAnnotationIntrospector(
        		AnnotationIntrospector.pair(new JaxbAnnotationIntrospector(),
        				new JacksonAnnotationIntrospector()));
    	objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
    }
	
	/**
	 * default private constructor.
	 */
	private JsonUtil(){
	}
	
	/**
	 * toObject is wrapper on top Jackson implementation and specific to Data Transfer Object - Serializable.
	 * toObject converts the given json string into specific class and response instance contains according to
	 * the store finder design and implementation principles.
	 *
	 * @param json a {@link java.lang.String} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return a T object.
	 */
	public static <T> T toObject(String json, Class<T> clazz){
		if (json == null) {
			return null;
		}
		T returnType = null;
		//Note: Java 7 has good exception handling mechanism.
		//like catch(JsonParseException | JsonMappingException | IOException e){}
		try {
			returnType = objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			//LOGGER.warn("Unable to convert into a object for the given json string : {}", json);
			System.out.println("exception..."+e);
		} 
		return returnType;
	}
	
	/**
	 * <p>toObject.</p>
	 *
	 * @param json a {@link java.lang.String} object.
	 * @param typeReference a {@link org.codehaus.jackson.type.TypeReference} object.
	 * @param <T> a T object.
	 * @return a T object.
	 */
	public static <T> T toObject(String json, TypeReference<T> typeReference){
		T returnType = null;
		if(json == null || typeReference == null){
			return returnType;
		}
		//Note: Java 7 has good exception handling mechanism.
		try {
			returnType = objectMapper.readValue(json, typeReference);
		} catch(JsonParseException | JsonMappingException e){
			//LOGGER.warn("Unable to convert into a object for the given json string : {}", json);
		} 
		catch (Exception e) {
			//LOGGER.error("Un known exception occured while converting into a object for the given json string : {}", json);
		} 
		return returnType;
	}
	
	/**
	 * toJson is wrapper on top Gson implementation and specific to Store Finder Request and Response.
	 * Json parsing.
	 *
	 * @param obj a {@link java.lang.Object} object.
	 * @return json string based on Store Finder design and principles
	 */
	public static String toJson(Object obj) {
		String json = "";
		if(obj == null){
			return json;
		}
		try {
			json = objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			//LOGGER.error("Unable to convert given object %s into json and exception message: %s", 
			//		obj, e.getMessage());
		} 
		return json;
	}
}

