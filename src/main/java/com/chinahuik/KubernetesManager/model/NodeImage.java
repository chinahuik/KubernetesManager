package com.chinahuik.KubernetesManager.model;

import java.util.Date;

import com.chinahuik.KubernetesManager.bean.BaseModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2020-06-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "NodeImage", description = "")
public class NodeImage extends BaseModel {

	private Integer nodeId;
	private String nodeName;

	private Integer imageId;

	private String imageName;

	/**
	 *
	 */
	public NodeImage() {
	}

	/**
	 *
	 * @param node
	 * @param baseImage
	 */
	public NodeImage(KubeNode node, BaseImage baseImage) {
		imageId = baseImage.getId();
		nodeId = node.getId();
		nodeName = node.getName();
		imageName = baseImage.getImageName();
		setCreatedBy("system");
		setUpdatedBy("system");
		setCreateTime(new Date());
		setUpdateTime(new Date());
	}

}
