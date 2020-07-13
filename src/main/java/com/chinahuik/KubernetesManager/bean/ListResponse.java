/**
 * 
 */
package com.chinahuik.KubernetesManager.bean;

import java.util.List;


/**
 * @author open-source@chinahuik.com
 *
 */
public class ListResponse<T> extends BaseResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1407236523846267083L;
    public ListResponse() {
    	super();
    }

    public ListResponse(List<T> list) {
    	super();
        this.put("result",list);
    }
    
    public void setResult(List<T> list) {
    	this.put("result",list);
	}
    @SuppressWarnings("unchecked")
	public List<T> getResult() {
		return (List<T>) this.get("result");
	}
    
}
