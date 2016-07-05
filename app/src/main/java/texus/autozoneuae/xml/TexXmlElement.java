/**
 * 
 */
package texus.kavi.xml;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Sandeep Kumar
 * 
 */
public class TexXmlElement {

	public TexXmlElement parent = null;
	public ArrayList<TexXmlElement> children = new ArrayList<TexXmlElement>();
	public HashMap<String, String> attributes = new HashMap<String, String>();
	
	public String name = null;
    public String nodeValue = null;
	

	public TexXmlElement(String name) {
		this.name = name;
	}
	
	public void setAttribute(String name, String value)
	{
		this.attributes.put(name, value);
	}

	public String getAttribute(String name) {

		if (!this.attributes.containsKey(name)) { return null; }
		
		String returnStr = this.attributes.get(name);
		if( (null != returnStr) && (returnStr.length() > 0)) {
			return returnStr;
		}
		return  null;
	}

	public void add(TexXmlElement ChildElement) {
		ChildElement.parent = this;
		children.add(ChildElement);
	}

	public TexXmlElement getSingleElementByAttributeName(String AttributeName,
                                                         String AttributeValue) {
		try {
			if ((null == AttributeName) || (AttributeName.length() < 1) ||
                    (null == AttributeValue)) {
				return null;
			}
			
			for (TexXmlElement curChild : children) {
				if (null == curChild) { continue; }
                String curAttr = curChild.getAttribute(AttributeName);
                if ((null != curAttr) && (0 == curAttr.compareTo(AttributeValue))) {
                    return curChild;
                }
			}
		}catch(Exception e){}
		return null;
	}
	
	
	public ArrayList<TexXmlElement> getElementsByAttributeName(String AttributeName,
                                                               String AttributeValue) {
		ArrayList<TexXmlElement> returnList = new ArrayList<TexXmlElement>();
		try {
			if ((null == AttributeName) || (AttributeName.length() < 1) ||
                    (null == AttributeValue)) {
				return returnList;
			}
			
			for (TexXmlElement curChild : children) {
				if (null == curChild) { continue; }
                String curAttr = curChild.getAttribute(AttributeName);
                if ((null != curAttr) && (0 == curAttr.compareTo(AttributeValue))) {
                    returnList.add(curChild);
                }
			}
		}catch(Exception e){}
		return returnList;
	}
	
	public TexXmlElement getSingleElementByName(String Name) {
		try {
			if ((null == Name) || (Name.length() < 1)) {
				return null;
			}
			
			for (TexXmlElement curChild : children) {
				if ((null != curChild) && (null != curChild.name) &&
                        (0 == curChild.name.compareToIgnoreCase(Name))) {
					return curChild;
				}
			}
		}catch(Exception e){}
		return null;
	}
	
//	public TexXmlElement SelectSingleELementByNameAndAttribute(String name, String attribute) {
//		try {
//			if ((null == name) || (name.length() < 1) || (null == attribute) || (attribute.length() < 1)) {
//				return null;
//			}
//			for (TexXmlElement curChild : children) {
//				if ((null != curChild) && (null != curChild.name)
//						&& (0 == curChild.name.compareToIgnoreCase(name))) {
//					
//					return curChild;
//				}
//			}
//		}catch(Exception e){}
//		return null;
//	}
	
	
	public ArrayList<TexXmlElement> getElementsByName(String Name) {
		ArrayList<TexXmlElement> returnList = new ArrayList<TexXmlElement>();
		try {
			if ((null == Name) || (Name.length() < 1)) {
				return returnList;
			}
			
			for (TexXmlElement curChild : children) {
				if ((null != curChild) && (null != curChild.name) &&
                        (0 == curChild.name.compareToIgnoreCase(Name))) {
					returnList.add(curChild);
				}
			}
		}catch(Exception e){}
		return returnList;
	}

}
