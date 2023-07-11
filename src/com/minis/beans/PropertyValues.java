package com.minis.beans;

import com.minis.core.PropertyValue;

import javax.crypto.interfaces.PBEKey;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-11 11:28
 **/
public class PropertyValues {
	private final List<PropertyValue> propertyValueList;
	public PropertyValues(){
		this.propertyValueList = new ArrayList<>();
	}
	public int size(){
		return this.propertyValueList.size();
	}

	public void addPropertyValue(PropertyValue pv){
		this.propertyValueList.add(pv);
	}
	public void removePropertyValue(PropertyValue pv){
		this.propertyValueList.remove(pv);
	}
	public void removePropertyValue(String valueName){
		this.propertyValueList.remove(getPropertyValue(valueName));
	}
	public PropertyValue[] getPropertyValues(){
		return this.propertyValueList.toArray(new PropertyValue[this.propertyValueList.size()]);
	}
	public PropertyValue getPropertyValue(String valueName){
		for(PropertyValue pv : this.propertyValueList){
			if(pv.getName().equals(valueName)){
				return pv;
			}
		}
		return null;
	}

	public Object get(String propertyName){
		PropertyValue pv = getPropertyValue(propertyName);
		return pv != null ? pv.getValue() : null;
	}

	public boolean contains(String propertyName){
		return getPropertyValue(propertyName) != null;
	}

	public boolean isEmpty(){
		return this.propertyValueList.isEmpty();
	}

}