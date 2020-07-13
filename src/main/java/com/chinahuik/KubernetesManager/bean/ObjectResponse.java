/**
 * 
 */
package com.chinahuik.KubernetesManager.bean;

/**
 * @author open-source@chinahuik.com
 *
 */

public class ObjectResponse<T> extends BaseResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObjectResponse() {
		super();
    }

    public ObjectResponse(T result) {
    	super();
        this.put("result", result);
    }
    public void setResult(T result) {
    	this.put("result",result);
	}
    @SuppressWarnings("unchecked")
	public T getResult() {
		return (T) this.get("result");
	}

}
