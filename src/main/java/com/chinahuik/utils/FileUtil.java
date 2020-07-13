/**
 *
 */
package com.chinahuik.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import lombok.extern.slf4j.Slf4j;

/**
 * @author open-source@chinahuik.com
 *
 */
@Slf4j
public class FileUtil {
	/**
	 * 归档
	 *
	 * @param entry
	 * @throws IOException
	 * @return
	 * @date 2017年5月27日下午1:48:23
	 */
	public static String archive(String entry) {
		final File file = new File(entry);
		try (final TarArchiveOutputStream tos = new TarArchiveOutputStream(
				new FileOutputStream(file.getAbsolutePath() + ".tar"));) {
			final String base = file.getName();
			if (file.isDirectory()) {
				archiveDir(file, tos, base);
			} else {
				archiveHandle(tos, file, base);
			}
		} catch (final IOException e) {
			log.error("archive {} failed: {}", entry, e);
		}
		return file.getAbsolutePath() + ".tar";
	}

	/**
	 * 把tar包压缩成gz
	 *
	 * @param path
	 * @throws IOException
	 * @return
	 * @date 2017年5月27日下午2:08:37
	 */
	public static String compressArchive(String path) {
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
				GzipCompressorOutputStream gcos = new GzipCompressorOutputStream(
						new BufferedOutputStream(new FileOutputStream(path + ".gz")));) {
			final byte[] buffer = new byte[1024];
			int read = -1;
			while ((read = bis.read(buffer)) != -1) {
				gcos.write(buffer, 0, read);
			}
		} catch (final IOException e) {
			log.error("compress archive {} failed: {}", path, e);
		}
		return path + ".gz";
	}

	/**
	 * 文件转换为base64字符串
	 *
	 * @param filePath
	 * @return
	 */
	public static String fileToBase64(String filePath) {
		final byte[] fileBytes = fileToBytes(filePath);
		return Base64.getEncoder().encodeToString(fileBytes);
	}

	/**
	 * 文件转换为二进制数组
	 *
	 * @param filePath
	 * @return
	 */
	public static byte[] fileToBytes(String filePath) {
		byte[] buffer = null;
		final File file = new File(filePath);
		try (FileInputStream fis = new FileInputStream(file);
				ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			final byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			buffer = bos.toByteArray();
		} catch (final Exception ex) {
			log.error("read bytes from file {} failed: {}", filePath, ex);
		}
		return buffer;
	}

	/**
	 *
	 * @param folder
	 * @return
	 */
	public static String folderToBase64(String folder) {
		final String tarPath = archive(folder);
		final String tgzPath = compressArchive(tarPath);
		return fileToBase64(tgzPath);
	}

	/**
	 * 解压
	 *
	 * @param archive
	 * @throws IOException
	 * @date 2017年5月27日下午4:03:29
	 */
	public static void unCompressArchiveGz(String archive) throws IOException {
		final File file = new File(archive);
		final String fileName = file.getName().substring(0,
				file.getName().lastIndexOf("."));

		final String finalName = file.getParent() + File.separator + fileName;
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(finalName));
				GzipCompressorInputStream gcis = new GzipCompressorInputStream(bis);) {
			final byte[] buffer = new byte[1024];
			int read = -1;
			while ((read = gcis.read(buffer)) != -1) {
				bos.write(buffer, 0, read);
			}
		} catch (final IOException e) {
			log.error("uncompress archive gz {} failed: {}", archive, e);
		}
		unCompressTar(finalName);
	}

	/**
	 * 解压tar
	 *
	 * @param finalName
	 * @throws IOException
	 * @date 2017年5月27日下午4:34:41
	 */
	public static void unCompressTar(String finalName) {
		final File file = new File(finalName);
		final String parentPath = file.getParent();
		try (TarArchiveInputStream tais = new TarArchiveInputStream(
				new FileInputStream(file));) {
			TarArchiveEntry tarArchiveEntry = null;
			while ((tarArchiveEntry = tais.getNextTarEntry()) != null) {
				final String name = tarArchiveEntry.getName();
				final File tarFile = new File(parentPath, name);
				if (!tarFile.getParentFile().exists()) {
					tarFile.getParentFile().mkdirs();
				}
				try (BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(tarFile));) {
					int read = -1;
					final byte[] buffer = new byte[1024];
					while ((read = tais.read(buffer)) != -1) {
						bos.write(buffer, 0, read);
					}
				} catch (final IOException e) {
					log.error("write file {} failed: {}", tarFile.getAbsolutePath(), e);
				}
			}
		} catch (final IOException e) {
			log.error("uncompress file {} failed: {}", finalName, e);
		}
		file.delete();// 删除tar文件
	}

	/**
	 * 递归处理，准备好路径
	 *
	 * @param file
	 * @param tos
	 * @param base
	 * @throws IOException
	 * @date 2017年5月27日下午1:48:40
	 */
	private static void archiveDir(File file, TarArchiveOutputStream tos, String basePath)
			throws IOException {
		final File[] listFiles = file.listFiles();
		for (final File fi : listFiles) {
			if (fi.isDirectory()) {
				archiveDir(fi, tos, basePath + File.separator + fi.getName());
			} else {
				archiveHandle(tos, fi, basePath);
			}
		}
	}

	/**
	 * 具体归档处理（文件）
	 *
	 * @param tos
	 * @param fi
	 * @param base
	 * @throws IOException
	 * @date 2017年5月27日下午1:48:56
	 */
	private static void archiveHandle(TarArchiveOutputStream tos, File fi,
			String basePath) throws IOException {
		final TarArchiveEntry tEntry = new TarArchiveEntry(
				basePath + File.separator + fi.getName());
		tEntry.setSize(fi.length());

		tos.putArchiveEntry(tEntry);

		final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fi));

		final byte[] buffer = new byte[1024];
		int read = -1;
		while ((read = bis.read(buffer)) != -1) {
			tos.write(buffer, 0, read);
		}
		bis.close();
		tos.closeArchiveEntry();// 这里必须写，否则会失败
	}
}
