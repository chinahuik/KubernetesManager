package com.chinahuik.KubernetesManager.model;

import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
@ApiModel(value = "BaseImage", description = "")
public class BaseImage extends BaseModel {

	private String imageHost;

	private String imageRepo;

	private String imageTag;

	private String operatingSystem;

	private String osVersion;

	private String developLanguage;

	private String devLangVersion;

	private String command;

	private String startArgs;

	private String workDir;

	private String portExpose;

	private String labels;

	private Long size;

	private Integer layers;

	private String dockerfile;

	/**
	 *
	 * @return
	 */
	public String getImageName() {
		if (imageHost == null || imageHost.trim().isEmpty()) {
			return String.format("%s:%s", imageRepo, imageTag);
		}
		return String.format("%s/%s:%s", imageHost, imageRepo, imageTag);
	}

	/**
	 *
	 * @param image
	 * @return
	 */
	public static BaseImage parseFromJson(JSONObject image) {
		final JSONArray names = image.getJSONArray("names");
		BaseImage baseImage = new BaseImage();
		baseImage.setCreatedBy("system");
		baseImage.setUpdatedBy("system");
		baseImage.setCreateTime(new Date());
		baseImage.setUpdateTime(new Date());
		final Long size = image.getLong("sizeBytes");
		baseImage.setSize(size);
		final String name0 = names.getString(0);
		if (names.size() > 1) {
			final String name1 = names.getString(1);
			int idx = name0.indexOf('@');
			if (idx > 0) {
				baseImage = generateImage(baseImage, name1);
				baseImage.setLabels(name0.substring(idx + 1));
			} else {
				baseImage = generateImage(baseImage, name0);
				idx = name1.indexOf('@');
				baseImage.setLabels(name1.substring(idx + 1));
			}
		} else {
			baseImage = generateImage(baseImage, name0);
		}
		return baseImage;
	}

	private static BaseImage generateImage(final BaseImage baseImage, final String name) {
		final int idx1 = name.indexOf('/');
		if (idx1 < 0) {
			generateImageTag(baseImage, name);
		} else {
			final String left = name.substring(0, idx1);
			final String right = name.substring(idx1 + 1);
			if (left.contains(".")) {
				baseImage.setImageHost(left);
				final int idx2 = right.indexOf(':');
				baseImage.setImageRepo(right.substring(0, idx2));
				baseImage.setImageTag(right.substring(idx2 + 1));
			} else {
				generateImageTag(baseImage, name);
			}
		}
		return baseImage;
	}

	private static void generateImageTag(final BaseImage baseImage, final String name) {
		baseImage.setImageHost("");
		final int idx2 = name.indexOf(':');
		baseImage.setImageRepo(name.substring(0, idx2));
		baseImage.setImageTag(name.substring(idx2 + 1));
	}

}
